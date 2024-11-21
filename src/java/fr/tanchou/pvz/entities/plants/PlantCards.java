package fr.tanchou.pvz.entities.plants;

public class PlantCards {
    private String name;
    private int cost;
    private int cooldown;

    public PlantCards(String name, int cost, int cooldown) {
        this.name = name;
        this.cost = cost;
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }
}
