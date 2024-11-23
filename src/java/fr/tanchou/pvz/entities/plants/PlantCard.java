package fr.tanchou.pvz.entities.plants;

import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Objects;

public class PlantCard {
    private String name;
    private int cost;
    private int cooldown;

    public PlantCard(String name, int cost, int cooldown) {
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

    public VuePlantCard createVue() {
        return new VuePlantCard(this);
    }

    public URL getURL(){
        return this.getClass().getResource("/assets/plants/Cards/" + this.getName() + "Card.png");
    }
}
