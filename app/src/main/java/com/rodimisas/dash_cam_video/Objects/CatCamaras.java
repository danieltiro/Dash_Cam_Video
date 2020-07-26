package com.rodimisas.dash_cam_video.Objects;

public class CatCamaras {
    public String titulo;
    public String valor;

    public CatCamaras() {
    }

    public CatCamaras(String titulo, String valor) {
        this.titulo = titulo;
        this.valor = valor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    @Override
    public String toString() {
        return titulo;
    }
}
