package com.financialeducation.model;

public class UsuarioBean {
    private String username;
    private int pontos;
    private String nivel;
    private String email;
    private String passwordHash;
    private boolean professor;

    public UsuarioBean(String username, int pontos, String email, String passwordHash) {
        this.username = username;
        this.pontos = pontos;
        this.nivel = calcularNivel(pontos);
        this.email = email;
        this.passwordHash = passwordHash;
    }
    
    public UsuarioBean(String username, int pontos, String email, String passwordHash, boolean professor) {
        this.username = username;
        this.pontos = pontos;
        this.nivel = calcularNivel(pontos);
        this.email = email;
        this.passwordHash = passwordHash;
        this.professor = professor;
    }
    
    public UsuarioBean(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.passwordHash = password;
        this.pontos = 0; // Inicia com 0 pontos
        this.nivel = calcularNivel(this.pontos); // Define nível inicial
        this.professor = false; // Por padrão, não é professor
    }

    private String calcularNivel(int pontos) {
        if (pontos <= 100) return "Iniciante";
        if (pontos <= 500) return "Intermediário";
        return "Experiente";
    }

    public String getUsername() {
        return username;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNivel() {
        return nivel;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public String getEmail() {
        return email;
    }
    
    public boolean isProfessor() {
        return professor;
    }
}