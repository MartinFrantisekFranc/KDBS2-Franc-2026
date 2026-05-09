package org.example;

public class Druh {
    int id;
    String nazev;

    public Druh(int id, String nazev) {
        this.id = id;
        this.nazev = nazev;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nazev;
    }
}
