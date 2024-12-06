package fr.tanchou.pvz.guiJavaFx.assetsLoder;

import fr.tanchou.pvz.abstractEnity.Entity;
import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssetsLoader {
    private static final Map<String,Map<String, Image>> assetsLoaded = new HashMap<>();

    private static Map<String, Image> loadAssetsEntity(String name, Entity entity){
        HashMap<String, Image> assets = new HashMap<>();

        if (entity instanceof Plant){
            Image image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/plants/"+ entity.getName() +".gif")));
            assets.put("normal", image);
            if (entity instanceof WallNut) {
                image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/plants/" + entity.getName() + "-damaged-1.png")));
                assets.put("damaged-1", image);
                image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/plants/" + entity.getName() + "-damaged-2.png")));
                assets.put("damaged-2", image);
            }
        }else if (entity instanceof Zombie){
            Image image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/zombies/"+entity.getName()+"Zombie/"+ entity.getName() +"Zombie-walk.gif")));
            assets.put("Zombie-walk", image);
            image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/zombies/"+entity.getName()+"Zombie/"+ entity.getName() +"Zombie-heating.gif")));
            assets.put("Zombie-heating", image);
        }

        assetsLoaded.put(name, assets);
        return assets;
    }

    public static Map<String, Image> getAssetEntity(Entity entity, String name){
        if (assetsLoaded.containsKey(name)){
            return assetsLoaded.get(name);
        }else {
            return loadAssetsEntity(name, entity);
        }
    }

    public static Map<String, Image> getAssetItems(String name){
        if (assetsLoaded.containsKey(name)){
            return assetsLoaded.get(name);
        }else {
            return loadAssetsItems();
        }
    }

    private static Map<String, Image> loadAssetsItems() {
        HashMap<String, Image> assets = new HashMap<>();
        Image image = new Image(Objects.requireNonNull(bullet.getClass().getResourceAsStream("/assets/bullets/"+ bullet.getName() +".png")));
    }
}
