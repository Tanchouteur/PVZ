package fr.tanchou.pvz.entities.plants;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Objects;

import static java.lang.System.exit;

public class PlantCard {
    private String name;
    private int cost;
    private int cooldown;

    public PlantCard(String name, int cost, int cooldown) {
        this.name = name;
        this.cost = cost;
        this.cooldown = cooldown;
    }

    public URL getURL(){
        URL url = this.getClass().getResource("/assets/plants/Cards/" + this.getName() + "Card.png");
        if (url == null) {
            System.out.println("/assets/plants/Cards/" + this.getName() + "Card.png");
            exit(1);
        }
        return url;
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
        VuePlantCard vuePlantCard = new VuePlantCard(this);
        vuePlantCard.setOnMouseClicked((MouseEvent event) -> {
            System.out.println("Clicked on " + this.getName());
        });
        vuePlantCard.setFitHeight(100);
        vuePlantCard.setFitWidth(100);
        return vuePlantCard;
    }


}
