package model;

public class Prenotazioni {
    private String user_name;
    private int id_prof;
    private String nome;
    private String mat;
    private String giorno;
    private float orarioIn;
    private String stato;

    public Prenotazioni(String user_name, int id_prof, String nome, String mat, String giorno, float orarioIn, String stato) {
        this.user_name = user_name;
        this.id_prof = id_prof;
        this.nome = nome;
        this.mat = mat;
        this.giorno = giorno;
        this.orarioIn = orarioIn;
        this.stato = stato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public float getOrarioIn() {
        return orarioIn;
    }

    public void setOrarioIn(float orarioIn) {
        this.orarioIn = orarioIn;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getId_prof() {
        return id_prof;
    }

    public void setId_prof(int id_prof) {
        this.id_prof = id_prof;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
