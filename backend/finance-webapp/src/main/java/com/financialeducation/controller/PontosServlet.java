package com.financialeducation.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.financialeducation.model.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        
        try (Connection con = Conexao.getConnection()) {
            // Atualiza os pontos do usuário
            PreparedStatement ps = con.prepareStatement("UPDATE users SET points = points + 10 WHERE username=?");
            ps.setString(1, username);
            ps.executeUpdate();
            
            // Recupera os pontos atualizados
            ps = con.prepareStatement("SELECT points FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int points = rs.getInt("points");
                String nivel;
                
                if (points <= 100) {
                    nivel = "Iniciante";
                } else if (points <= 500) {
                    nivel = "Intermediário";
                } else {
                    nivel = "Experiente";
                }
                
                session.setAttribute("points", points);
                session.setAttribute("nivel", nivel);
            }
            
            response.sendRedirect("logado.jsp?success=1");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("logado.jsp?error=1");
        }
    }
}
