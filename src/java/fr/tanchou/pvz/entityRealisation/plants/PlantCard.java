package fr.tanchou.pvz.entityRealisation.plants;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;

public class PlantCard {
    private int lastSelected;
    private final int cooldown;
    private final Plant plant;

    public PlantCard(int cooldown, Plant plant) {
        this.cooldown = cooldown;
        this.plant = plant;
    }

    public void tick() {
        if (this.lastSelected > 0) {
            this.lastSelected--;
        }
    }

    public Plant getNewPlant(double x, int y) {
        if (this.lastSelected == 0) {
            this.lastSelected = this.cooldown;
            return this.plant.clone(x, y);
        }
        return null;
    }
}
