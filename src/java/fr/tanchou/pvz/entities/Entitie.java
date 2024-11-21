package fr.tanchou.pvz.entities;

public abstract class Entitie {
    private Double position;
    int healthPoint;

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }
}
