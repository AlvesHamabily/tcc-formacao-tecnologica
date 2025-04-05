package com.financialeducation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {

	private static final String URL = "jdbc:postgresql://34.95.165.153:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "minhasenha";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // Força o carregamento do driver
            System.out.println(PASSWORD);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL não encontrado!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco!", e);
        }
    }
}
