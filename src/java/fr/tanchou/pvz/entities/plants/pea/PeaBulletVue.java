package fr.tanchou.pvz.entities.plants.pea;

import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.image.ImageView;

import java.net.URL;

import static java.lang.System.exit;

public class PeaBulletVue extends EntitieVue {

    public PeaBulletVue(PeaBullet peaBullet) {
        URL url = peaBullet.getClass().getResource("/assets/plants/peaShooter/PeaBullet.png");
        if (url == null) {
            System.err.println("Impossible de charger l'image du peaBullet");
            exit(1);
        }

        super(peaBullet, new ImageView(url.toString()));
    }
}
