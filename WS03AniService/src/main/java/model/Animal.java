package model;

public class Animal {
    private int id;
    private String nome;
    private String especie;
    private int idade;

    public Animal() {
        id = -1;
        nome = "";
        especie = "";
        idade = 0;
    }

    public Animal(int id, String nome, String especie, int idade) {
        setId(id);
        setNome(nome);
        setEspecie(especie);
        setIdade(idade);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Animal: " + nome + "   Espécie: " + especie + "   Idade: " + idade;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Animal) obj).getId());
    }
}
