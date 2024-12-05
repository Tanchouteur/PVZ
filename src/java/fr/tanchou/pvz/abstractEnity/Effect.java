package fr.tanchou.pvz.abstractEnity;

public abstract class Effect {
    private double speedToApply;
    private int effectDuration;
    private final int dammagePer10Tick;

    public Effect(double speedToApply, int effectDuration, int dammagePer10Tick) {
        this.speedToApply = speedToApply;
        this.effectDuration = effectDuration;
        this.dammagePer10Tick = dammagePer10Tick;
    }

    public double getSpeedToApply() {
        return speedToApply;
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public void tick() {
        effectDuration--;
    }

    public int getDammagePer10Tick() {
        return dammagePer10Tick;
    }

    public abstract Effect clone();
}
