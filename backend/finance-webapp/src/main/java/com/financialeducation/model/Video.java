package com.financialeducation.model;

public class Video {
    private int id;
    private String titulo;
    private String url;

    public Video() {
    }
    
    public Video(int id, String titulo, String url, int index) {
        this.id = id;
        this.titulo = titulo;
        this.url = url;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getUrl() { return url; }

    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setUrl(String url) { this.url = url; }
}
