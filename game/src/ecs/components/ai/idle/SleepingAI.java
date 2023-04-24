package ecs.components.ai.idle;

import ecs.components.ai.AIComponent;
import ecs.components.ai.transition.RangeTransition;
import ecs.entities.Entity;

public class SleepingAI<T> implements IIdleAI {
    /**
     * The IdleAI to switch to when hero enters range
     */
    private final T walk;
    private final float detectionRadius = 5;
    private RangeTransition range;

    public SleepingAI(T walk) {
        this.walk = walk;
        range = new RangeTransition(detectionRadius);
    }

    @Override
    public void idle(Entity entity) {
        boolean awake = range.isInFightMode(entity);

        if(awake){
            entity.getComponent(AIComponent.class)
                    .ifPresent(
                        ac -> {
                            ((AIComponent) ac).setIdleAI((IIdleAI) walk);
                        });
        }
    }
}
