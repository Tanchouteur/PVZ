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

    private boolean haveZombie = false;

    private final RowVue rowVue;

    Row() {
        listPlants = new LinkedList<>();
        listZombie = new LinkedList<>();
        listBullets = new LinkedList<>();
        lawnMower = true;

        rowVue = new RowVue(this);
    }

    public void updateZombies() {
        Iterator<Zombie> iterator = listZombie.iterator();

        while (iterator.hasNext()) {
            Zombie zombie = iterator.next();

            if (zombie.isDead()) {
                iterator.remove();
                continue;
            }

            if (havePlant()) {
                for (Plant plant : listPlants) {
                    if (plant.isDead()) {
                        removePlant(plant);
                        continue;
                    }
                    if (zombie.collidesWith(plant)) {
                        plant.takeDamage(zombie.getDamage());
                    } else {
                        zombie.move();
                    }
                }
            } else {
                zombie.move();
            }

            if (zombie.getX() < -1.0) {
                zombie.onDeath();
                iterator.remove();
            }
        }
    }

    public void updatePlants() {
        for (Plant plant : listPlants) {
            plant.tick();

            if (haveZombie) {
                Bullet bullet = plant.shoot();
                if (bullet != null) {
                    addBullet(bullet);
                }
            }
        }
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
                    bullet.onDeath();
                    iterator.remove();
                    break;
                }
            }

            // Supprimer le projectile s'il est hors de l'écran
            if (bullet.getX() > 7.0) {
                bullet.onDeath();
                iterator.remove();
            }
        }
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

    public void addPlant(Plant plant) {
        listPlants.add(plant);
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

    public void removePlant(Plant plant) {
        listPlants.remove(plant);
    }

    public void removeZombie(Zombie zombie) {
        listZombie.remove(zombie);

        if (listZombie.isEmpty()) {
            haveZombie = false;
        }
    }

    public void removeBullet(Bullet bullet) {
        listBullets.remove(bullet);
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

    public RowVue getRowVue() {
        return rowVue;
    }

    public boolean haveZombie() {
        return haveZombie;
    }

    public void setHaveZombie(boolean haveZombie) {
        this.haveZombie = haveZombie;
    }

    public boolean havePlant() {
        return !listPlants.isEmpty();
    }
}
