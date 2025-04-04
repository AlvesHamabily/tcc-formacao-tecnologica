package com.financialeducation.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import com.financialeducation.model.UsuarioDAO;
import com.financialeducation.model.UsuarioBean;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioBean usuario = usuarioDAO.buscarUsuarioPorEmail(email);
        
        if (usuario != null && BCrypt.checkpw(password, usuario.getPasswordHash())) {
            HttpSession session = request.getSession();
            session.setAttribute("email", usuario.getEmail());
            session.setAttribute("username", usuario.getUsername());
            response.sendRedirect("logado.jsp");
        } else {
            response.sendRedirect("index.jsp?error=1");
        }
    }
}
