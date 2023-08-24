package com.ti2cc;

public class Animais {
    private int codigo;
    private String tipo;//vaca,gato,cachorro
    private String nome;//minie,lulu,snoopy
    private String raca;//border collie, shihtzu

    public Animais() {
        this.codigo = -1;
        this.tipo = "";
        this.nome = "";
        this.raca = "";
    }

    public Animais(int codigo, String tipo, String nome, String raca) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.nome = nome;
        this.raca = raca;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    @Override
    public String toString() {
        return "Animais [codigo=" + codigo + ", tipo=" + tipo + ", nome=" + nome + ", raça=" + raca + "]";
    }
}
