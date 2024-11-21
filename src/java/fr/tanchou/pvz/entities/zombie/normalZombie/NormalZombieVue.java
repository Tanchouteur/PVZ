package fr.tanchou.pvz.entities.zombie.normalZombie;

import fr.tanchou.pvz.entities.zombie.ZombieVue;

import java.net.URL;

import static java.lang.System.exit;

public class NormalZombieVue extends ZombieVue {
    public NormalZombieVue(NormalZombie normalZombie) {
        URL url = normalZombie.getClass().getResource("/assets/zombie/normalZombie/normalZombie.png");
        if (url == null) {
            System.err.println("Impossible de charger l'image du zombie");
            exit(1);
        }
        super(normalZombie, url);
    }
}
