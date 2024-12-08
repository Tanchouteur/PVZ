package fr.tanchou.pvz.abstractEnity.abstractPlant;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public abstract class WaitingPlant extends Plant {
    private int usable;
    private boolean needToCreate = false;
    private final int damage;

    protected WaitingPlant(int healthPoint, double colliderRadius, int x, int y, String name, int cost, int timeBeforeUsable, int damage) {
        super(healthPoint,colliderRadius,x,y, name, cost);
        this.usable = timeBeforeUsable;
        this.damage = damage;
    }

    public boolean canCreate(){
        return this.getUsable() <= 0 && this.isNeedToCreate();
    }

    public void tick() {
        if (usable > 0){
            usable--;
        }
    }

    public abstract WaitingPlant clone(double x, int y);

    public int getUsable() {
        return usable;
    }

    public boolean isNeedToCreate() {
        return needToCreate;
    }

    public void setNeedToCreate(boolean needToCreate) {
        this.needToCreate = needToCreate;
    }

    public void attack(Zombie zombie) {
        zombie.takeDamage(this.getDamage());
        this.takeDamage(this.getHealthPoint()+1);
    }

    public int getDamage() {
        return damage;
    }
}
