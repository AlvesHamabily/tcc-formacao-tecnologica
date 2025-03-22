package com.financialeducation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	//Lembrar de alterar as informações ${}
	
//    private static final String URL = "jdbc:postgresql://[IP_DO_SERVIDOR]:[PORTA]/[NOME_DO_BANCO]";
//    private static final String USER = "${USER}";
//    private static final String PASSWORD = "${PASS}";
	
//    private static final String URL = "jdbc:postgresql://34.95.165.153:5432/finance";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "h@^XG@JG9MEQ\">*N";
	
    private static final String URL = "jdbc:postgresql://localhost:3113/finance";
    private static final String USER = "postgres";
    private static final String PASSWORD = "banco";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // Força o carregamento do driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL não encontrado!", e);
        }
    }
}
