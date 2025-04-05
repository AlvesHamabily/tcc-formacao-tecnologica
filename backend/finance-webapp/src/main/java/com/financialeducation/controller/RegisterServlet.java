package com.financialeducation.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.financialeducation.model.UsuarioDAO;
import com.financialeducation.model.UsuarioBean;

@WebServlet("/Cadastro")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("cadastro.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("nome");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioBean usuario = new UsuarioBean(username, email, password);
        
        if (usuarioDAO.registrarUsuario(usuario)) {
            response.sendRedirect("index.html?register=success");
        } else {
            response.sendRedirect("cadastro.html?error=1");
        }
    }
}
