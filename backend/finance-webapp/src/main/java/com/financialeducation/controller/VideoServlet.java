package com.financialeducation.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.financialeducation.model.VideoBean;
import com.financialeducation.model.VideoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/videos")
public class VideoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO;

    public void init() {
        try {
            videoDAO = new VideoDAO(); // Corrigido: agora a instância é atribuída corretamente
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<VideoBean> videos = videoDAO.listarVideos();

        // Verificar ID atual
        String idParam = request.getParameter("id");
        String action = request.getParameter("action");
        VideoBean videoAtual = null;

        if (idParam != null && action != null) {
            int videoId = Integer.parseInt(idParam);
            boolean avancar = action.equals("next");
            videoAtual = videoDAO.buscarProximoVideo(videoId, avancar);
        }

        if (videoAtual == null && !videos.isEmpty()) {
            videoAtual = new VideoBean(videos.get(0).getId(), videos.get(0).getTitulo(), videos.get(0).getUrl());
        }
        request.setAttribute("videos", videos);
        request.setAttribute("videoAtual", videoAtual);
        request.getRequestDispatcher("videos.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titulo = request.getParameter("titulo");
        String url = request.getParameter("url");

        if (titulo == null || titulo.trim().isEmpty() || url == null || url.trim().isEmpty()) {
            request.setAttribute("mensagem", "Título e URL são obrigatórios!");
            request.getRequestDispatcher("videos.jsp").forward(request, response);
            return;
        }

        VideoBean novoVideo = new VideoBean();
        novoVideo.setTitulo(titulo);
        novoVideo.setUrl(url);

        boolean inserido = videoDAO.inserirVideo(novoVideo);

        if (inserido) {
            response.sendRedirect("videos"); // Redireciona para atualizar a lista
        } else {
            request.setAttribute("mensagem", "Erro ao inserir vídeo!");
            request.getRequestDispatcher("videos.jsp").forward(request, response);
        }
    }
}