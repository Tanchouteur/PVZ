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

import java.util.LinkedList;

public class Row {
    private final int rowIndex;
    private Mower mower;
    private boolean haveZombie = false;
    private Zombie firstZombie;

    private final PlantCase[] plantCasesArray;
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
        plantCasesArray = new PlantCase[9];
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

        //first zombie update first zombie is not de first in linkedlist but the first in the row on x axis
        for (Zombie zombie : listZombie) {
            if (firstZombie == null || zombie.getX() < firstZombie.getX() || firstZombie.isDead()) {
                firstZombie = zombie;
            }
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

        for (PlantCase plantCase : plantCasesArray) {
            if (!plantCase.isEmpty() && plantCase.getPlant().isDead()) {
                plantCase.removePlant();
            }
        }

        this.haveZombie = !(listZombie.isEmpty());
    }

    private void updateMawer() {
        if (haveZombie && mower != null && mower.collideWith(firstZombie)) {
            if (mower.isActive()) {
                firstZombie.takeDamage(1000);
            }else {
                mower.setActive(true);
            }
        }

        if (mower != null && mower.isActive()) {
            mower.move();
        }

        if (mower != null && mower.getX() >= 10.5) {
            mower = null;
        }
    }

    private void updateZombies() {
        for (Zombie zombie : listZombie) {

            zombie.move();
            zombie.tick();

            if (zombie.getX() < 0 && mower == null) {
                this.defeat = true;
            }

            //wich case choose :

            int index = (int) zombie.getX();

            if ((int) zombie.getX() >8){
                index = 8;
            }else if ((int) zombie.getX() < 0){
                index = 0;
            }
            PlantCase plantCase = plantCasesArray[index];
            if (!plantCase.isEmpty()) {
                Plant plant = plantCase.getPlant();

                if (zombie.collideWith(plant)) {
                    zombie.attack(plant);
                    zombie.setHeating(true);
                }

            }else {
                zombie.setHeating(false);
            }
        }
    }

    private void updatePlants() {

        for (PlantCase plantCase : plantCasesArray) {
            if (!plantCase.isEmpty()) {

                if (plantCase.getPlant() instanceof ObjectGeneratorsPlant plant) {

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
                            //System.err.println("Sun create and add");
                        }
                        //System.err.println(plant.getName()+" create");
                    }
                }
            }
        }
    }

    private void updateBullets() {

        for (Bullet bullet : listBullets) {
            bullet.move();

            if (this.haveZombie && bullet.collidesWith(firstZombie)) {
                firstZombie.takeDamage(bullet.getDamage());

                if (bullet.haveEffect()) {
                    firstZombie.setEffect(bullet.getEffect().clone());
                }

                bullet.setDead(true);
                break;

            }else if (bullet.getX() > 11) {
                bullet.setDead(true);
                break;
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
