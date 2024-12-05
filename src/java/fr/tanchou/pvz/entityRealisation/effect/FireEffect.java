package fr.tanchou.pvz.entityRealisation.effect;

import fr.tanchou.pvz.abstractEnity.Effect;

public class FireEffect extends Effect {
    public FireEffect() {
        super(1, 50, 5);
    }

    @Override
    public Effect clone() {
        return new FireEffect();
    }
}
