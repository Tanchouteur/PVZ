package fr.tanchou.pvz.game.rowComponent;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;

public class PlantCase {
    private final double x;  // Position en X
    private final int y;  // Position en Y
    private Plant plant;

    public PlantCase(double x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean placePlant(Plant plant) {
        if (this.plant == null) {
            this.plant = plant;
            return true;
        }
        return false;
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