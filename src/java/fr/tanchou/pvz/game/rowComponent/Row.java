package fr.tanchou.pvz.game.rowComponent;

import fr.tanchou.pvz.abstractEnity.Entity;
import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;
import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.game.SunManager;

import java.util.Iterator;
import java.util.LinkedList;

public class Row {
    private final int rowIndex;
    private Mower mower;
    private boolean haveZombie = false;
    private Zombie firstZombie;

    private final PlantCase[] plantCasesArray = new PlantCase[9];
    private final LinkedList<Bullet> listBullets = new LinkedList<>();
    private final LinkedList<Zombie> listZombie = new LinkedList<>();

    private final LinkedList<Plant> listPlantToAdd = new LinkedList<>();
    private final LinkedList<Bullet> listBulletsToAdd = new LinkedList<>();
    private final LinkedList<Zombie> listZombieToAdd = new LinkedList<>();

    private final SunManager sunManager;
    private boolean defeat = false;

    public Row(int rowIndex, SunManager sunManager) {
        this.rowIndex = rowIndex;
        this.mower = new Mower(rowIndex);
        this.sunManager = sunManager;

        // Initialiser les cases avec des positions fixes
        for (int i = 0; i < 9; i++) {
            plantCasesArray[i] = new PlantCase(i, rowIndex);
        }
    }

    public void tick() {

        updateChange();
        updateZombies();
        updatePlants();
        updateBullets();
        updateMawer();
    }

    private void updateChange(){
        if (!listZombieToAdd.isEmpty()) {
            listZombie.addAll(listZombieToAdd);
            listZombieToAdd.clear();
        }

        if (!listPlantToAdd.isEmpty()) {

            for (Plant plant : listPlantToAdd) {
                plantCasesArray[(int) plant.getX()].placePlant(plant);
            }
            listPlantToAdd.clear();
        }

        if (!listBulletsToAdd.isEmpty()) {
            listBullets.addAll(listBulletsToAdd);
            listBulletsToAdd.clear();
        }

        listZombie.removeIf(Entity::isDead);

        listBullets.removeIf(Bullet::isDead);

        listPlantToAdd.removeIf(Entity::isDead);
    }

    private void updateMawer() {
        if (haveZombie && mower != null) {
            if (mower.collideWith(firstZombie)) {
                mower = null;
                for (Zombie zombie : listZombie) {
                    zombie.takeDamage(1000);
                }
                System.err.println("Mower");
            }
        }
    }

    private void updateZombies() {

        this.haveZombie = !listZombie.isEmpty();

        for (Zombie zombie : listZombie) {
            if (zombie.getHealthPoint() <= 0) {
                zombie.setHeating(false);
                System.out.println("Zombie mort");
                continue;
            }

            zombie.move();
            zombie.tick();

            if (zombie.getX() < 0 && mower == null) {
                this.defeat = true;
            }
            if (firstZombie != null) {
                if (zombie.getX() < firstZombie.getX()) {
                    firstZombie = zombie;
                }
            } else {
                firstZombie = zombie;
            }

            for (PlantCase c : plantCasesArray) {
                if (c.getPlant() != null) {
                    Plant plant = c.getPlant();
                    int attackResult = 1000;
                    if (zombie.collideWith(plant)) {
                        attackResult = zombie.attack(plant);
                        zombie.setHeating(true);

                    }
                    if (attackResult <= 0) {
                        zombie.setHeating(false);
                        plantCasesArray[(int) Math.round(plant.getX())].removePlant();
                        System.err.println("Zombie Kill " + plant.getName());
                    }
                }
            }
        }
    }

    private void updatePlants() {

        for (PlantCase c : plantCasesArray) {
            if (c.getPlant() != null) {
                if (c.getPlant().getHealthPoint() <= 0) {
                    c.getPlant().setDead(true); // Retire la plante morte de la case
                    System.out.println("Plant mort : x = " + c.getX() + " y = " + c.getY());
                    continue;
                }

                if (c.getPlant() instanceof ObjectGeneratorsPlant plant) {

                    if (!(plant instanceof SunFlower)) {
                        plant.setNeedToCreate(this.haveZombie && firstZombie.getX() > plant.getX() && firstZombie.getX() < 9.0); // défini si la plante dois tiré
                    }else {
                        plant.setNeedToCreate(true);
                    }
                    ObjectOfPlant object = plant.tick();

                    if (object != null) {

                        if (object instanceof Bullet bullet) {
                            this.addBullet(bullet);
                        } else if (object instanceof Sun sun) {
                            sunManager.addSun(sun);
                            System.err.println("Sun create and add");
                        }
                        System.err.println(plant.getName()+" create");
                    }
                }
            }
        }
    }

    private void updateBullets() {

        for (Bullet bullet : listBullets) {
            bullet.move();

            if (bullet.collidesWith(firstZombie)) {
                firstZombie.takeDamage(bullet.getDamage());

                if (bullet.haveEffect()) {
                    firstZombie.setEffect(bullet.getEffect().clone());
                }

                bullet.setDead(true);
                break;

            }else if (bullet.getX() > 11) {

                bullet.setDead(true);
            }
        }
    }

    public LinkedList<Zombie> getListZombies() {
        return listZombie;
    }

    public LinkedList<Bullet> getListBullets() {
        return listBullets;
    }

    public void addZombie(Zombie zombie) {
        listZombieToAdd.add(zombie);
        if (!haveZombie) {
            haveZombie = true;
        }
    }

    public void addBullet(Bullet bullet) {
        listBulletsToAdd.add(bullet);
    }

    public Zombie getFirstZombie() {
        return firstZombie;
    }

    public boolean haveZombie() {
        return !listZombie.isEmpty();
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public boolean isDefeat() {
        return defeat;
    }

    public void placePlantInCase(Plant plant) {
        listPlantToAdd.add(plant);

        System.out.println("Plant place : x = " + plant.getX() + " y = " + plant.getY());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Row[").append(rowIndex).append("] haveZombie=").append(haveZombie).append("{");
        if (mower != null){
            stringBuilder.append("Mower ");
        }

        for (PlantCase plantCase : plantCasesArray){
            stringBuilder.append(plantCase).append(" ");
        }

        stringBuilder.append("List Zombies").append(" ");
        for (Zombie zombie : listZombie){
            stringBuilder.append(zombie).append(" ");
        }

        stringBuilder.append("ListBullet").append(" ");
        for (Bullet bullet : listBullets){
            stringBuilder.append(bullet).append(" ");
        }

        //stringBuilder.append(firstZombie);

        return stringBuilder.toString();
    }

    public PlantCase getPlantCase(int x) {
        return plantCasesArray[x];
    }

    public Mower getMower(){
        return mower;
    }
}
