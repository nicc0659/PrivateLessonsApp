package model;

public class Insegna {

    private int id;
    private String nome;
    private String materia;

    public Insegna(int id, String nome, String materia) {
        this.id = id;
        this.nome = nome;
        this.materia = materia;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMateria() {
        return materia;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}
