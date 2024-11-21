package fr.tanchou.pvz.entities.zombie;

import fr.tanchou.pvz.entities.Entitie;
import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.image.ImageView;

import java.net.URL;

public class ZombieVue extends EntitieVue {

    public ZombieVue(Entitie entitie, URL url) {
        super(entitie, new ImageView(url.toString()));
    }
}
