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
    private final Map<String, Map<String, Image>> assetsLoaded = new HashMap<>();

    public AssetsLoader(Player player) {
        loadAssetsItems();
        loadAssetsBullet();

        // Charger les plantes du joueur
        for (PlantCard plantCard : player.getPlantCardsArray()) {
            loadAssetsEntity(plantCard.getPlant());
        }

        // Charger les zombies disponibles
        ZombieCard[] zombiesCardArray = {
                new ZombieCard(new NormalZombie(11.0, 0), 40, 1),
                new ZombieCard(new ConeHeadZombie(11.0, 0), 25, 2),
                new ZombieCard(new BucketHeadZombie(11.0, 0), 10, 3)
        };

        for (ZombieCard zombieCard : zombiesCardArray) {
            loadAssetsEntity(zombieCard.getZombie());
        }
    }

    public Map<String, Image> getAssetEntity(Entity entity) {
        return assetsLoaded.computeIfAbsent(entity.getName(), k -> loadAssetsEntity(entity));
    }

    private Map<String, Image> loadAssetsEntity(Entity entity) {
        Map<String, Image> assets = new HashMap<>();

        if (entity instanceof Plant) {
            loadPlantAssets((Plant) entity, assets);
        } else if (entity instanceof Zombie) {
            loadZombieAssets((Zombie) entity, assets);
        }

        assetsLoaded.put(entity.getName(), assets);
        return assets;
    }

    private void loadPlantAssets(Plant plant, Map<String, Image> assets) {
        // Charger l'image principale de la plante
        assets.put("normal", loadImage("/assets/plants/" + plant.getName() + ".gif"));

        // Charger les images spécifiques aux noix
        if (plant instanceof WallNut) {
            assets.put("damaged-1", loadImage("/assets/plants/" + plant.getName() + "-damaged-1.png"));
            assets.put("damaged-2", loadImage("/assets/plants/" + plant.getName() + "-damaged-2.png"));
        }
    }

    private void loadZombieAssets(Zombie zombie, Map<String, Image> assets) {
        String basePath = "/assets/zombies/" + zombie.getName() + "Zombie/";
        assets.put("Zombie-walk", loadImage(basePath + zombie.getName() + "Zombie-walk.gif"));
        assets.put("Zombie-heating", loadImage(basePath + zombie.getName() + "Zombie-heating.gif"));
        assets.put("Zombie-walk-damaged", loadImage(basePath + zombie.getName() + "Zombie-walk-damaged.gif"));
    }

    public void loadAssetsItems() {
        assetsLoaded.put("sun", createAsset("/assets/items/Sun/SunAnimated.gif"));
        assetsLoaded.put("mower", createAsset("/assets/items/Mower.png"));
    }

    public void loadAssetsBullet() {
        assetsLoaded.put("PeaBullet", createAsset("/assets/bullets/PeaBullet.png"));
        assetsLoaded.put("FreezePeaBullet", createAsset("/assets/bullets/FreezePeaBullet.png"));
    }

    private Map<String, Image> createAsset(String path) {
        Map<String, Image> assets = new HashMap<>();
        assets.put("normal", loadImage(path));
        return assets;
    }

    private Image loadImage(String path) {
        try {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (NullPointerException e) {
            throw new RuntimeException("Asset not found: " + path, e);
        }
    }

    public Map<String, Map<String, Image>> getAssetsLoaded() {
        return assetsLoaded;
    }

    public Map<String, Image> getAssetsItems(String itemName) {
        return assetsLoaded.get(itemName);
    }

    public Map<String, Image> getAssetsBullet(String bulletName) {
        return assetsLoaded.get(bulletName);
    }
}
