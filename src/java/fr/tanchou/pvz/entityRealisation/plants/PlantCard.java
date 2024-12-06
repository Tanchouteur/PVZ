package fr.tanchou.pvz.entityRealisation.plants;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;

public class PlantCard {
    private int lastSelected = 50;
    private final int cooldown;
    private final Plant plant;
    private int sold;

    public PlantCard(int cooldown, Plant plant, int sold) {
        this.cooldown = cooldown;
        this.plant = plant;
        this.sold = sold;
    }

    public void tick(int playerSold) {
        if (this.lastSelected > 0) {
            this.lastSelected--;
        }
        this.sold = playerSold;
    }

    public Plant getNewPlant(double x, int y) {

        this.lastSelected = this.cooldown;

        return this.plant.clone(x, y);

    }

    public boolean canBuy(){
        return this.lastSelected <= 0 && this.sold >= this.plant.getCost();
    }

    public Plant getPlant() {
        return plant;
    }

    //getCooldown
    public int getCooldown() {
        return cooldown;
    }

    //getLastSelected
    public int getLastSelected() {
        return lastSelected;
    }
}
