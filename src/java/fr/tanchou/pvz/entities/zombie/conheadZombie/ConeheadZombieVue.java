package fr.tanchou.pvz.entities.zombie.conheadZombie;

import fr.tanchou.pvz.entities.zombie.ZombieVue;
import javafx.scene.layout.Pane;

import java.net.URL;

import static java.lang.System.exit;

public class ConeheadZombieVue extends ZombieVue {
    public ConeheadZombieVue(ConeheadZombie coneheadZombie, Pane parent) {
        URL url = coneheadZombie.getClass().getResource("/assets/zombies/coneheadZombie/ConeheadZombie.webp");
        if (url == null) {
            System.err.println("Impossible de charger l'image du zombie connedhead");
            exit(1);
        }
        super(coneheadZombie, url, parent);
    }
}
