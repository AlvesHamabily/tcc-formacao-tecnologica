package com.financialeducation.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.financialeducation.model.Conexao;
import com.financialeducation.model.UsuarioBean;
import org.mindrot.jbcrypt.BCrypt;
import io.github.cdimascio.dotenv.Dotenv;


public class UsuarioDAO {
    private static final Dotenv dotenv = Dotenv.load();

	 public boolean usuarioExiste(String username, String email) {
	        try (Connection con = Conexao.getConnection()) {
	            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? OR email=?");
	            ps.setString(1, username);
	            ps.setString(2, email);
	            ResultSet rs = ps.executeQuery();
	            return rs.next();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	 
	    public boolean registrarUsuario(UsuarioBean usuario) {
	        if (usuarioExiste(usuario.getUsername(), usuario.getEmail())) {
	            return false;
	        }
	        try (Connection con = Conexao.getConnection()) {
	            PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
	            ps.setString(1, usuario.getUsername());
	            ps.setString(2, BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt(12)));
	            ps.setString(3, usuario.getEmail());
	            ps.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	
	    public UsuarioBean buscarUsuarioPorEmail(String email) {
	        System.out.println("Buscando usuário com email: " + email);

	        try (Connection con = Conexao.getConnection()) {
	            System.out.println("Conexão obtida com sucesso!");

	            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=?");
	            ps.setString(1, email);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                System.out.println("Usuário encontrado no banco.");
	                return new UsuarioBean(
	                    rs.getString("username"),
	                    rs.getInt("points"),
	                    rs.getString("email"),
	                    rs.getString("password"),
	                    rs.getBoolean("professor")
	                );
	            } else {
	                System.out.println("Usuário não encontrado.");
	            }
	        } catch (Exception e) {
	            System.out.println("Erro ao buscar usuário:");
	            e.printStackTrace();
	        }

	        return null;
	    }

	
    public boolean alterarSenha(String username, String email, String novaSenha) {
        try (Connection con = Conexao.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET password=? WHERE username=? AND email=?");
            ps.setString(1, BCrypt.hashpw(novaSenha, BCrypt.gensalt(12)));
            ps.setString(2, username);
            ps.setString(3, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
    public UsuarioBean atualizarPontos(String username) {
        try (Connection con = Conexao.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET points = points + 10 WHERE username=?");
            ps.setString(1, username);
            ps.executeUpdate();
            
            ps = con.prepareStatement("SELECT points, email, password, professor FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new UsuarioBean(
                    username,
                    rs.getInt("points"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("professor")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean atualizarProfessor(String email, boolean professor) {
        try (Connection con = Conexao.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET professor = ? WHERE email = ?");
            ps.setBoolean(1, professor);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
