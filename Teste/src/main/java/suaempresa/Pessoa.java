package suaempresa;

import java.time.LocalDate;

public class Pessoa {
    private static int contador = 1; // Gera IDs automaticamente
    private int id;
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.id = contador++; // Atribui um ID único a cada nova pessoa
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "ID=" + id + ", Nome=" + nome + ", Data de Nascimento=" + dataNascimento;
    }
}
