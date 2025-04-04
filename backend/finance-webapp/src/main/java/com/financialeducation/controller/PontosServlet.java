package com.financialeducation.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.financialeducation.model.UsuarioDAO;
import com.financialeducation.model.UsuarioBean;

@WebServlet("/PontosServlet")
public class PontosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        String username = (String) session.getAttribute("username");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioBean usuario = usuarioDAO.atualizarPontos(username);
        
        if (usuario != null) {
            session.setAttribute("points", usuario.getPontos());
            session.setAttribute("nivel", usuario.getNivel());
            response.sendRedirect("logado.jsp?success=1");
        } else {
            response.sendRedirect("logado.jsp?error=1");
        }
    }
}
