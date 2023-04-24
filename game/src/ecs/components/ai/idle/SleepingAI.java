package ecs.components.ai.idle;

import ecs.components.ai.AIComponent;
import ecs.components.ai.transition.RangeTransition;
import ecs.entities.Entity;

public class SleepingAI implements IIdleAI {
    /**
     * The IdleAI to switch to when hero enters range
     */
    private final IIdleAI walk;
    private final RangeTransition range;

    public SleepingAI(IIdleAI walk, float detectionRadius) {
        this.walk = walk;
        range = new RangeTransition(detectionRadius);
    }

    @Override
    public void idle(Entity entity) {
        boolean awake = range.isInFightMode(entity);

        if (awake) {
            entity.getComponent(AIComponent.class)
                .ifPresent(
                    ac -> ((AIComponent) ac).setIdleAI(walk));
        }
    }
}
