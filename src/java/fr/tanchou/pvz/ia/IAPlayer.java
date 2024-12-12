package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.game.Partie;

import java.util.Random;

public class IAPlayer {
    private final Partie partie;

    public IAPlayer(Partie partie) {
        this.partie = partie;
    }

    public void makeDecision() {
        Random random = new Random();

        int x = random.nextInt(8); // colonne
        int y = random.nextInt(5); // ligne


        if (partie.getPlayer().getSold() >= 50) {
            partie.getPlayer().buyPlant(partie.getPlayer().getPlantCardsArray()[random.nextInt(partie.getPlayer().getPlantCardsArray().length)], x, y);
            //System.out.println("IA a planté une plante sur la ligne " + y + " et la colonne " + x);
        } /*else {
            //System.out.println("IA attend...");
        }*/
    }
}