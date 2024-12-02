package fr.tanchou.pvz.abstractEnity;

public abstract class Effect {
    private int speedToApply;
    private int effectDuration;
    protected int timeSinceLastShot = 0;
    private final int dammage;

    public Effect(int speedToApply, int effectDuration, int dammage) {
        this.speedToApply = speedToApply;
        this.effectDuration = effectDuration;
        this.dammage = dammage;
    }

    public int getSpeedToApply() {
        return speedToApply;
    }

    public void setSpeedToApply(int speedToApply) {
        this.speedToApply = speedToApply;
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    public void tick() {
        timeSinceLastShot++;
    }

    public int getDammage() {
        return dammage;
    }
}
