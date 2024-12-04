package fr.tanchou.pvz.game.rowComponent;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;

public class PlantCase {
    private final int x;  // Position en X
    private final int y;  // Position en Y
    private Plant plant;

    public PlantCase(int x, int y) {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
        return "|"+this.getPlant()+"| ";
    }

    public boolean isEmpty() {
        return plant == null;
    }
}
