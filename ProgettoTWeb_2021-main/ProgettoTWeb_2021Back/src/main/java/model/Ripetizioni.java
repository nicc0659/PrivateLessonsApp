package model;

public class Ripetizioni {
    private int doc;
    private String mat;
    private float orarioIn;
    private String giorno;
    private int prenotazione;

    public Ripetizioni(int doc, String mat, float orarioIn, String giorno, int prenotazione) {
        this.doc = doc;
        this.mat = mat;
        this.orarioIn = orarioIn;
        this.giorno = giorno;
        this.prenotazione = prenotazione;
    }

    public int getDoc() {
        return doc;
    }

    public void setDoc(int doc) {
        this.doc = doc;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public float getOrarioIn() {
        return orarioIn;
    }

    public void setOrarioIn(float orarioIn) {
        this.orarioIn = orarioIn;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public int getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(int prenotazione) {
        this.prenotazione = prenotazione;
    }
}
