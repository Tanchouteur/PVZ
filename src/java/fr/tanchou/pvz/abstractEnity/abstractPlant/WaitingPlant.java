package fr.tanchou.pvz.abstractEnity.abstractPlant;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public abstract class WaitingPlant extends Plant {
    private int usableTick;
    private boolean needToCreate = false;
    private final int damage;

    protected WaitingPlant(int healthPoint, double colliderRadius, int x, int y, String name, int cost, int timeBeforeUsable, int damage) {
        super(healthPoint,colliderRadius,x,y, name, cost);
        this.usableTick = timeBeforeUsable;
        this.damage = damage;
    }

    public boolean canCreate(){
        return this.getUsableTick() <= 0 && this.isNeedToCreate();
    }

    public void tick() {
        if (usableTick > 0){
            usableTick--;
        }
    }

    public abstract WaitingPlant clone(double x, int y);

    public int getUsableTick() {
        return usableTick;
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
