package edu.upc.marcog.restapi;

public class Track {
    private String id;
    private String titulo;
    private String cantante;

    // Constructor
    public Track(String id, String title, String singer) {
        this.id = id;
        this.titulo = title;
        this.cantante = singer;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return titulo;
    }

    public void setTitle(String title) {
        this.titulo = title;
    }

    public String getSinger() {
        return cantante;
    }

    public void setSinger(String singer) {
        this.cantante = singer;
    }
}
