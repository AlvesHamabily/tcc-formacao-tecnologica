package com.financialeducation.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.*;

import com.financialeducation.model.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
//inserir verificação aqui
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try (Connection con = Conexao.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                
                String passwordHash = rs.getString("password");
                String username = rs.getString("username");
            
            
	            if (BCrypt.checkpw(password, passwordHash)) {
	                HttpSession session = request.getSession();
	                session.setAttribute("email", email);
	                session.setAttribute("username", username);
	                response.sendRedirect("logado.jsp");
	            } else {
	                response.sendRedirect("index.jsp?error=1");
	            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
