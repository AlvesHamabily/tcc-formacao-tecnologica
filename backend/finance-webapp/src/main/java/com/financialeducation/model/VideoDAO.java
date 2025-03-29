package com.financialeducation.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO {
    private Connection conn;

    public VideoDAO() throws SQLException {
        conn = Conexao.getConnection();
    }

    public List<VideoBean> listarVideos() {
        List<VideoBean> videos = new ArrayList<>();
        String sql = "SELECT * FROM videos";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                VideoBean video = new VideoBean();
                video.setId(rs.getInt("id"));
                video.setTitulo(rs.getString("titulo"));
                video.setUrl(rs.getString("url"));
                videos.add(video);
            }
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videos;
    }

    public VideoBean buscarVideoPorId(int id) {
        String sql = "SELECT * FROM videos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new VideoBean(rs.getInt("id"), rs.getString("titulo"), rs.getString("url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean inserirVideo(VideoBean video) {
        String sql = "INSERT INTO videos (titulo, url) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, video.getTitulo());
            stmt.setString(2, video.getUrl());
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public VideoBean buscarProximoVideo(int id, boolean avancar) {
        String sql = avancar ? 
            "SELECT * FROM videos WHERE id > ? ORDER BY id ASC LIMIT 1" :
            "SELECT * FROM videos WHERE id < ? ORDER BY id DESC LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new VideoBean(rs.getInt("id"), rs.getString("titulo"), rs.getString("url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
