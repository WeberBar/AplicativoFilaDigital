package com.example.aplicativodefila;
public class Senha {
    private Long id;
    private int numero;
    private boolean chamada;
    private boolean atendida;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isChamada() {
        return chamada;
    }

    public void setChamada(boolean chamada) {
        this.chamada = chamada;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }
// Getters e Setters
}
