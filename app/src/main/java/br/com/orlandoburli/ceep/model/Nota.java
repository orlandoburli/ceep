package br.com.orlandoburli.ceep.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String titulo;
    private final String descricao;

    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

}