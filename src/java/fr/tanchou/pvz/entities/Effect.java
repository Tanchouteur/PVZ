package fr.tanchou.pvz.entities;

public abstract class Effect {
    private int speedToApply;
    private int timeOut;
    protected int timeSinceLastShot = 0;

    public Effect(int speedToApply, int timeOut) {
        this.speedToApply = speedToApply;
        this.timeOut = timeOut;
    }

    public int getSpeedToApply() {
        return speedToApply;
    }

    public void setSpeedToApply(int speedToApply) {
        this.speedToApply = speedToApply;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public void tick() {
        timeSinceLastShot++;
    }
}
