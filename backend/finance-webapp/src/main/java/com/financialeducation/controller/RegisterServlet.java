package com.financialeducation.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("nome");
        String password = request.getParameter("password");
        String email = request.getParameter("email");


        try (Connection con = com.financialeducation.model.Conexao.getConnection()) {
            PreparedStatement sl = con.prepareStatement("SELECT * FROM users WHERE username=?");
            sl.setString(1, username);
            ResultSet rs = sl.executeQuery();

            PreparedStatement sll = con.prepareStatement("SELECT * FROM users WHERE email=?");
            sll.setString(1, email);
            ResultSet rss = sll.executeQuery();
            
            if (!rs.next() | !rss.next()) {
	            PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
	            ps.setString(1, username);
	            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt(12)));
	            ps.setString(3, email);
	            ps.executeUpdate();
	            
	            response.sendRedirect("index.html?register=success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("cadastro.html?error=1");
        }
    }
}
