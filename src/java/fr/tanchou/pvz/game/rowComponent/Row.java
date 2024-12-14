package fr.tanchou.pvz.game.rowComponent;

import fr.tanchou.pvz.abstractEnity.Entity;
import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;
import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.abstractEnity.abstractPlant.WaitingPlant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;
import fr.tanchou.pvz.game.SunManager;

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

    private double maxHealthObserved = 0;
    private double totalZombieHealth = 0;

    private int killedZombieCount = 0;

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

        for (Zombie zombie : listZombie) {
            if (zombie.isDead() && zombie.getHealthPoint() >= -200) {
                killedZombieCount++;
            }
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
        this.totalZombieHealth = 0;
        for (Zombie zombie : listZombie) {

            zombie.move();
            zombie.tick();

            if (zombie.getX() < 0 && mower == null) {
                this.defeat = true;
            }

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
            this.totalZombieHealth += zombie.getHealthPoint();
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
                }else if (plantCase.getPlant() instanceof WaitingPlant waitingPlant) {
                    waitingPlant.tick();
                    if (haveZombie) {
                        waitingPlant.setNeedToCreate(waitingPlant.collideWith(firstZombie));
                    }
                    if (waitingPlant.canCreate()) {
                        waitingPlant.attack(firstZombie);
                        break;
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

    // Ajouter une entrée pour la dangerosité de chaque ligne
    public double calculateDangerLevel() {
        double dangerLevel = 0.0;

        // Ajouter la dangerosité des zombies
        for (Zombie zombie : listZombie) {
            if (zombie != null) {
                // On utilise la vie restante pour augmenter le danger
                double zombieHealthPercentage = (double) zombie.getHealthPoint() / zombie.getOriginalHealth();
                dangerLevel += zombieHealthPercentage;

                // Ajouter un facteur en fonction de la proximité des zombies aux plantes
                int zombieX = (int) zombie.getX();
                if (zombieX >= 0 && zombieX < plantCasesArray.length) {
                    PlantCase plantCase = plantCasesArray[zombieX];
                    if (!plantCase.isEmpty()) {
                        // Si le zombie est proche d'une plante, il devient plus dangereux
                        dangerLevel += 0.5;  // Ajout d'un facteur de dangerosité selon la proximité
                    }
                }
            }
        }

        // Ajouter un facteur en fonction de la présence et du type de plantes sur la ligne
        for (PlantCase plantCase : this.plantCasesArray) {
            if (plantCase.getPlant() != null) {
                Plant plant = plantCase.getPlant();
                // Si la plante est défensive, elle diminue la dangerosité
                if (plant instanceof WallNut) {
                    dangerLevel -= 0.3;  // Réduit le danger si la plante est défensive
                }
                // Les plantes offensives augmentent le danger, en fonction de leur type
                else if (plant instanceof ObjectGeneratorsPlant) {
                    dangerLevel += 0.2;  // Augmente le danger si la plante est offensive
                }
            }
        }

        // Calculer la dangerosité globale basée sur la somme de la santé des zombies
        this.totalZombieHealth = 0;
        for (Zombie zombie : listZombie) {
            if (zombie != null) {
                this.totalZombieHealth += zombie.getHealthPoint();
            }
        }

        // Si la santé totale des zombies est élevée, augmenter la dangerosité de la ligne
        if (this.totalZombieHealth > 500) {  // Seuil arbitraire pour augmenter la dangerosité
            dangerLevel += 1.0;
        }

        return dangerLevel;
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

    public int getKilledZombieCount() {
        return killedZombieCount;
    }

    public double normalizeHealthOfZombies() {

        if (this.totalZombieHealth > maxHealthObserved) {
            maxHealthObserved = totalZombieHealth;
        }

        return totalZombieHealth / maxHealthObserved;
    }
}
