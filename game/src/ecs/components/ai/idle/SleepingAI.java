package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.tile.Tile;
import org.w3c.dom.ranges.Range;
import starter.Game;
import tools.Constants;

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
