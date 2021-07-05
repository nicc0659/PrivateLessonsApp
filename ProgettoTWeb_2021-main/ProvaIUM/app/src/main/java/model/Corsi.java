package model;

public class Corsi {

    private String materia;

    public Corsi(String materia) {
        this.materia = materia;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Corsi{" +
                "materia='" + materia + '\'' +
                '}';
    }
}
