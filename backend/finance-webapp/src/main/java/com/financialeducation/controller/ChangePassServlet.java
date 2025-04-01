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

@WebServlet("/ChangePassServlet")
public class ChangePassServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("nome");
        String oldpass = request.getParameter("oldpass");
        String password = request.getParameter("password");
        String email = request.getParameter("email");


        try (Connection con = com.financialeducation.model.Conexao.getConnection()) {
        	boolean aux = false;
            
        	PreparedStatement sl = con.prepareStatement("SELECT * FROM users WHERE email=?");
            sl.setString(1, email);
            ResultSet rs = sl.executeQuery();
            
            if(rs.getString("username") == username && rs.getString("email") == email) {
	        
            	PreparedStatement ps = con.prepareStatement("UPDATE public.users SET password=? WHERE username = ? and email = ?;");
            	ps.setString(1, BCrypt.hashpw(password, BCrypt.gensalt(12)));
	            ps.setString(2, username);
	            ps.setString(3, email);
	            ps.executeUpdate();
	            
	            response.sendRedirect("index.html?register=success");
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.html?error=1");
        }
    }
}
