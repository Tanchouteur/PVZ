package fr.tanchou.pvz.entityRealisation.effect;

import fr.tanchou.pvz.abstractEnity.Effect;

public class FreezeEffect extends Effect {
    public FreezeEffect() {
        super(0.4, 40, 0);
    }

    @Override
    public Effect clone() {
        return new FreezeEffect();
    }
}
