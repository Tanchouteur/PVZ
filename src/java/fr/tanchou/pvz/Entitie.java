package fr.tanchou.pvz;

public abstract class Entitie {
    private Double position;
    private int healthPoint;

    Entitie(Double position) {
        this.position = position;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }
}
