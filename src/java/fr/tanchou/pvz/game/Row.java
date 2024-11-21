package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.zombie.Zombie;

import java.util.Iterator;
import java.util.LinkedList;

public class Row {
    private final LinkedList<Plant> listPlants;
    private final LinkedList<Zombie> listZombie;
    private final LinkedList<Bullet> listBullets;
    private boolean lawnMower;
    private Zombie firstZombie;

    private RowVue rowVue;

    Row() {
        listPlants = new LinkedList<>();
        listZombie = new LinkedList<>();
        listBullets = new LinkedList<>();
        lawnMower = true;

        rowVue = new RowVue(this);
    }

    public void addPlant(Plant plant) {
        listPlants.add(plant);
    }

    public void addZombie(Zombie zombie) {
        listZombie.add(zombie);
    }

    public void addBullet(Bullet bullet) {
        listBullets.add(bullet);
    }

    public void removePlant(Plant plant) {
        listPlants.remove(plant);
    }

    public void removeZombie(Zombie zombie) {
        listZombie.remove(zombie);
    }

    public void removeBullet(Bullet bullet) {
        listBullets.remove(bullet);
    }

    public LinkedList<Plant> getListPlants() {
        return listPlants;
    }

    public LinkedList<Zombie> getListZombies() {
        return listZombie;
    }

    public LinkedList<Bullet> getListBullets() {
        return listBullets;
    }

    public boolean haveLawnMower() {
        return lawnMower;
    }

    public void setLawnMower(boolean lawnMower) {
        this.lawnMower = lawnMower;
    }

    public Zombie findFirstZombie() {
        Zombie firstZombie = listZombie.getFirst();
        for (Zombie zombie : listZombie) {
            if (zombie.getX() < firstZombie.getX()) {
                firstZombie = zombie;
            }
        }
        return firstZombie;
    }

    public void updateFirstZombie() {
        this.firstZombie = this.findFirstZombie();
    }

    public Zombie getFirstZombie() {
        return firstZombie;
    }

    public void updateBullets() {
        Iterator<Bullet> iterator = listBullets.iterator();

        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move();

            // Vérifier les collisions avec les zombies
            for (Zombie zombie : listZombie) {
                if (bullet.collidesWith(zombie)) {
                    zombie.takeDamage(bullet.getDamage());
                    iterator.remove(); // Supprimer le projectile après collision
                    break;
                }
            }
        }
    }

    public RowVue getRowVue() {
        return rowVue;
    }
}
