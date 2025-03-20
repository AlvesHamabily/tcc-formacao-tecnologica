package com.financialeducation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	//Lembrar de alterar as informações ${}
	
//    private static final String URL = "jdbc:postgresql://localhost:5432/${DATABASE}";
//    private static final String USER = "${USER}";
//    private static final String PASSWORD = "${PASS}";
	


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // Força o carregamento do driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL não encontrado!", e);
        }
    }
}
