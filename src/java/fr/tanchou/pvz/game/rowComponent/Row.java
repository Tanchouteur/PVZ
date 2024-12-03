package fr.tanchou.pvz.game.rowComponent;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;
import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
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

    private final SunManager sunManager;
    private boolean defeat = false;

    public Row(int rowIndex, SunManager sunManager) {
        this.rowIndex = rowIndex;
        this.mower = new Mower();
        this.sunManager = sunManager;

        // Initialiser les cases avec des positions fixes
        for (int i = 0; i < 9; i++) {
            plantCasesArray[i] = new PlantCase(i, rowIndex);
        }
    }

    public void tick() {
        updateZombies();
        updatePlants();
        updateBullets();
        updateMawer();
    }

    public void updateMawer() {
        if (haveZombie && mower != null) {
            if (mower.collideWith(firstZombie)) {
                mower = null;
                listZombie.clear();
                System.err.println("Mower");
            }
        }
    }

    public void updateZombies() {
        this.haveZombie = !listZombie.isEmpty();
        
        Iterator<Zombie> iterator = listZombie.iterator();
        while (iterator.hasNext()) {
            Zombie zombie = iterator.next();
            if (zombie.getHealthPoint() <= 0) {
                iterator.remove();
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
            }else {
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

    public void updatePlants() {
        for (PlantCase c : plantCasesArray) {
            if (c.getPlant() != null) {
                if (c.getPlant().getHealthPoint() <= 0) {
                    c.removePlant(); // Retire la plante morte de la case
                    System.out.println("Plant mort : x = " + c.getX() + " y = " + c.getY());
                    continue;
                }

                if (c.getPlant() instanceof ObjectGeneratorsPlant plant) {
                    plant.setZombieInFront(haveZombie);

                    ObjectOfPlant object = plant.tick();

                    if (object != null) {

                        if (object instanceof Bullet bullet) {
                            this.addBullet(bullet);
                        } else if (object instanceof Sun sun) {
                            sunManager.addSun(sun);
                        }
                        System.err.println("Plant create object : x = " + c.getX() + " y = " + c.getY() + " object = " + plant.getName()+"object");
                    }
                }
            }
        }
    }

    public void updateBullets() {
        Iterator<Bullet> iterator = listBullets.iterator();

        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move();

            if (bullet.collidesWith(firstZombie)) {
                firstZombie.takeDamage(bullet.getDamage());
                iterator.remove();
                System.out.println("Zombie take damage");
                break;
            }

            if (bullet.getX() > 11) {
                iterator.remove();
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
        listZombie.add(zombie);
        if (!haveZombie) {
            haveZombie = true;
        }
    }

    public void addBullet(Bullet bullet) {
        listBullets.add(bullet);
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

    public void placePlantInCase(int i, Plant plant) {
        plantCasesArray[i].placePlant(plant);
        System.out.println("Plant place : x = " + plantCasesArray[i].getX() + " y = " + plantCasesArray[i].getY());
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
}
