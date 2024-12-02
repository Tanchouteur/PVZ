package fr.tanchou.pvz.entityRealisation.ObjectOfPlant;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;

public class Sun extends ObjectOfPlant {
    private final int value;

    public Sun(double x, int y, int colliderRadius, int value) {
        super(x, y, colliderRadius);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public ObjectOfPlant clone() {
        return new Sun(this.getX(), this.getY(), this.getCollider().getRadius(), this.getValue());
    }
}
