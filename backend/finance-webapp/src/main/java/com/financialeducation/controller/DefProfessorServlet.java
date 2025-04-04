package com.financialeducation.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.microprofile.openapi.annotations.media.Schema.False;
import org.mindrot.jbcrypt.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DefProfessorServlet")
public class DefProfessorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email = request.getParameter("email");
    	String slProfessor = request.getParameter("defProfessor");
    	Boolean defProf;
    	
    	if(slProfessor == null) {
    		defProf = false;
    	} else {
    		defProf = true;
    	}


        try (Connection con = com.financialeducation.model.Conexao.getConnection()) {
            
        	PreparedStatement sl = con.prepareStatement("SELECT email, professor FROM users WHERE email=?");
            sl.setString(1, email);
            ResultSet rs = sl.executeQuery();
            // Move para a próxima linha
            if (rs.next()) {  // Verifica se existe uma linha correspondente
                String retrievedEmail = rs.getString("email");
                
                if (retrievedEmail.equals(email)) {
                    PreparedStatement ps = con.prepareStatement("UPDATE public.users SET professor = ? WHERE email = ?");
                    ps.setBoolean(1, defProf);
                    ps.setString(2, email);
                    ps.executeUpdate();

                    response.sendRedirect("teste.html?register=success");
                }
            } else {
                // Caso nenhum registro seja encontrado
                response.sendRedirect("teste.html?error=noUserFound");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("teste.html?error=1");
        }
    }
}
