package com.financialeducation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	//credenciais de conexão com o banco cloud
    private static final String URL = "jdbc:postgresql://34.95.165.153:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "meu_banco_de_dados_na_nuvem";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // Força o carregamento do driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL não encontrado!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco!", e);
        }
    }
}
