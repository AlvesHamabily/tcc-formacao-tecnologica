package com.financialeducation.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.financialeducation.model.UsuarioDAO;

@WebServlet("/Redefinir-Senha")
public class ChangePassServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("resetsenha.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("nome");
        String email = request.getParameter("email");
        String novaSenha = request.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.alterarSenha(username, email, novaSenha)) {
            response.sendRedirect("index.html?register=success");
        } else {
            response.sendRedirect("index.html?error=1");
        }
    }
}
