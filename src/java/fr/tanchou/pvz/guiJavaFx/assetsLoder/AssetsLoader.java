package fr.tanchou.pvz.guiJavaFx.assetsLoder;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.abstractEnity.Entity;
import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;
import fr.tanchou.pvz.entityRealisation.zombie.BucketHeadZombie;
import fr.tanchou.pvz.entityRealisation.zombie.ConeHeadZombie;
import fr.tanchou.pvz.entityRealisation.zombie.NormalZombie;
import fr.tanchou.pvz.entityRealisation.zombie.ZombieCard;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssetsLoader {

    private final Map<String,Map<String, Image>> assetsLoaded = new HashMap<>();

    public AssetsLoader(Player player){
        loadAssetsItems();
        loadAssetsBullet();

        for (PlantCard plantCard : player.getPlantCardsArray()){
            loadAssetsEntity(plantCard.getPlant());
        }

        ZombieCard[] zombiesCardArray = new ZombieCard[]{
                new ZombieCard(new NormalZombie(11.0,0), 40),
                new ZombieCard(new ConeHeadZombie(11.0,0), 25),
                new ZombieCard(new BucketHeadZombie(11.0,0), 10)
        };

        for (ZombieCard zombieCard : zombiesCardArray){
            loadAssetsEntity(zombieCard.getZombie());
        }
    }

    public Map<String, Image> getAssetEntity(Entity entity){
        if (assetsLoaded.containsKey(entity.getName())){
            return assetsLoaded.get(entity.getName());
        }else {
            return loadAssetsEntity(entity);
        }
    }

    public Map<String, Image> getAssetItems(String name){
        return assetsLoaded.get(name);
    }

    public Map<String, Image> getAssetBullet(String name){
        return assetsLoaded.get(name);
    }

    private Map<String, Image> loadAssetsEntity(Entity entity){
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

        assetsLoaded.put(entity.getName(), assets);
        return assets;
    }

    public void loadAssetsItems(){
        HashMap<String, Image> assets = new HashMap<>();
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/items/Sun/SunAnimated.gif")));
        assets.put("normal", image);
        assetsLoaded.put("sun", assets);

        assets = new HashMap<>();
        image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/items/Mower.png")));
        assets.put("normal", image);
        assetsLoaded.put("mower", assets);
    }

    public void loadAssetsBullet(){
        HashMap<String, Image> assets = new HashMap<>();
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/bullets/PeaBullet.png")));
        assets.put("normal", image);
        assetsLoaded.put("PeaBullet", assets);

        assets = new HashMap<>();
        image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/bullets/FreezePeaBullet.png")));
        assets.put("normal", image);
        assetsLoaded.put("FreezePeaBullet", assets);
    }

    public Map<String, Map<String, Image>> getAssetsLoaded() {
        return assetsLoaded;
    }
}
