package fr.tanchou.pvz.game.board;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.plants.passive.sunflower.Sun;
import fr.tanchou.pvz.entities.plants.passive.sunflower.SunFlower;
import fr.tanchou.pvz.entities.plants.shooter.ShooterPlant;
import fr.tanchou.pvz.entities.zombie.Zombie;
import fr.tanchou.pvz.player.Player;

import java.util.Iterator;
import java.util.LinkedList;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private final LinkedList<Plant> listPlants;
    private final LinkedList<Zombie> listZombie;
    private final LinkedList<Bullet> listBullets;

    private boolean lawnMower;
    private Zombie firstZombie;

    private boolean haveZombie = false;

    private final RowVue rowVue;
    private final LinkedList<Case> cases;

    private final SunManager sunManager;

    public Row(int rowIndex, SunManager sunManager, Player player) {
        listPlants = new LinkedList<>();
        listZombie = new LinkedList<>();
        listBullets = new LinkedList<>();

        this.sunManager = sunManager;

        lawnMower = true;

        // Initialiser les cases avec des positions fixes
        cases = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            cases.add(new Case(i, rowIndex)); // Espacement des cases
        }

        rowVue = new RowVue(this, player);
    }

    public void updateZombies() {
        Iterator<Zombie> iterator = listZombie.iterator();

        while (iterator.hasNext()) {
            Zombie zombie = iterator.next();
            zombie.tick();

            if (zombie.isDead()) {
                iterator.remove();
                continue;
            }

            boolean hasCollided = false;

            // Vérifie les collisions avec les plantes dans les cases
            for (Case c : cases) {
                if (c.isOccupied() && zombie.collidesWith(c.getPlant())) {
                    zombie.attack(c.getPlant());
                    hasCollided = true;
                    break;
                }
            }

            if (!hasCollided) {
                zombie.move();
            }

            if (zombie.getX() < -1.0) {
                zombie.onDeath();
                iterator.remove();
            }
        }
    }

    public void updatePlants() {
        for (Case c : cases) {
            if (c.isOccupied()) {
                Plant plant = c.getPlant();
                plant.tick();

                if (plant.isDead()) {
                    plant.onDeath();
                    c.removePlant(); // Retire la plante morte de la case
                }

                if (plant instanceof ShooterPlant shooterPlant) {
                    if (haveZombie()) {
                        Bullet bullet = shooterPlant.shoot();
                        if (bullet != null) {
                            addBullet(bullet);
                        }
                    }
                }else if (plant instanceof SunFlower sunFlower) {
                    Sun sun = (Sun) sunFlower.action();

                    if (sun != null) {
                        sunManager.addSun(sun.getX(), sun.getY(), sun.getValue());
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

            for (Zombie zombie : listZombie) {
                if (bullet.collidesWith(zombie)) {
                    zombie.takeDamage(bullet.getDamage());
                    bullet.onDeath();
                    iterator.remove();
                    break;
                }
            }

            if (bullet.getX() > 11) {
                bullet.onDeath();
                iterator.remove();
            }
        }
    }

    public void placePlantInCase(int caseIndex, Plant plant) {
        if (caseIndex >= 0 && caseIndex < cases.size()) {
            Case selectedCase = cases.get(caseIndex);
            if (!selectedCase.isOccupied()) {
                selectedCase.placePlant(plant);
                listPlants.add(plant); // Facultatif si on veut garder la liste des plantes
            }
        }
    }

    public void removePlantFromCase(int caseIndex) {
        if (caseIndex >= 0 && caseIndex < cases.size()) {
            Case selectedCase = cases.get(caseIndex);
            if (selectedCase.isOccupied()) {
                listPlants.remove(selectedCase.getPlant()); // Retirer de la liste si nécessaire
                selectedCase.removePlant();
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
        return !listZombie.isEmpty();
    }

    public void setHaveZombie(boolean haveZombie) {
        this.haveZombie = haveZombie;
    }

    public boolean havePlant() {
        return !listPlants.isEmpty();
    }

    public LinkedList<Case> getCases() {
        return cases;
    }
}
