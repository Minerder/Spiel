package contrib.utils.components.ai.idle;

import contrib.components.AIComponent;
import contrib.utils.components.ai.AITools;
import contrib.utils.components.ai.IIdleAI;
import core.Entity;
import core.components.DrawComponent;

public class SleepingAI implements IIdleAI {
    private final IIdleAI walk;
    private final float detectionRadius;
    private final DrawComponent animationAfterWakingup;

    /**
     * Sleeps at its current position until the hero enters the detection radius. If hero enters the
     * radius switch to a different IdleAI.
     *
     * @param walk the IdleAI to switch to when hero enters range
     * @param detectionRadius radius in which the hero should be detected
     * @param animationAfterWakingup animation to switch to after waking up
     */
    public SleepingAI(IIdleAI walk, float detectionRadius, DrawComponent animationAfterWakingup) {
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
            entity.removeComponent(DrawComponent.class);
            entity.addComponent(animationAfterWakingup);
        }
    }
}
