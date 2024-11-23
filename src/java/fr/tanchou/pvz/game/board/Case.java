package fr.tanchou.pvz.game.board;

import fr.tanchou.pvz.entities.plants.Plant;

public class Case {
    private final double x;  // Position en X
    private final int y;  // Position en Y
    private Plant plant;     // Plante occupant la case

    public Case(double x, int y) {
        this.x = x;
        this.y = y;
        this.plant = null;
    }

    public boolean isOccupied() {
        return plant != null;
    }

    public void placePlant(Plant plant) {
        this.plant = plant;
        if (plant != null) {
            plant.setX(this.x);  // Positionner la plante à la position de la case
            plant.setY(this.y);
        }
    }

    public void removePlant() {
        this.plant = null;
    }

    public Plant getPlant() {
        return plant;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
