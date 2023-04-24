package ecs.components.ai.idle;

import ecs.components.ai.AIComponent;
import ecs.components.ai.transition.RangeTransition;
import ecs.entities.Entity;

public class SleepingAI<T> implements IIdleAI {
    /**
     * The IdleAI to switch to when hero enters range
     */
    private final T walk;
    private final float detectionRadius = 2;

    public SleepingAI(T walk) {
        this.walk = walk;
    }

    @Override
    public void idle(Entity entity) {
        boolean awake = new RangeTransition(detectionRadius).isInFightMode(entity);

        if(awake){
            entity.getComponent(AIComponent.class)
                    .ifPresent(
                        ac -> {
                            ((AIComponent) ac).setIdleAI((IIdleAI) walk);
                        });
        }
    }
}
