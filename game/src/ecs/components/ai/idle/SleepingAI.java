package ecs.components.ai.idle;

import ecs.components.AnimationComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.entities.Entity;

public class SleepingAI implements IIdleAI {
    private final IIdleAI walk;
    private final float detectionRadius;
    private final AnimationComponent animationAfterWakingup;

    /**
     * Sleeps at its current position until the hero enters the detection radius. If hero
     * enters the radius switch to a different IdleAI.
     *
     * @param walk the IdleAI to switch to when hero enters range
     * @param detectionRadius radius in which the hero should be detected
     * @param animationAfterWakingup animation to switch to after waking up
     */
    public SleepingAI(IIdleAI walk, float detectionRadius, AnimationComponent animationAfterWakingup) {
        this.walk = walk;
        this.detectionRadius = detectionRadius;
        this.animationAfterWakingup = animationAfterWakingup;
    }

    @Override
    public void idle(Entity entity) {
        boolean awake = AITools.playerInRange(entity, detectionRadius);

        if (awake) {
            entity.getComponent(AIComponent.class)
                .ifPresent(ac -> ((AIComponent) ac).setIdleAI(walk));
            entity.removeComponent(AnimationComponent.class);
            entity.addComponent(animationAfterWakingup);
        }
    }
}
