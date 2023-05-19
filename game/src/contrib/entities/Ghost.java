package contrib.entities;

import contrib.components.AIComponent;
import contrib.utils.components.ai.fight.CollideAI;
import contrib.utils.components.ai.idle.FollowHero;
import contrib.utils.components.ai.transition.RangeTransition;
import core.Entity;
import core.components.PositionComponent;
import core.components.VelocityComponent;
import dslToGame.AnimationBuilder;
import core.components.DrawComponent;


public class Ghost extends Entity {

    private static final Ghost ghost = new Ghost();

    private Ghost() {
        super();
        setupPositionComponent();
        setupVelocityComponent();
        setupAIComponent();
        setupDrawComponent();
    }

    private void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new FollowHero(4));
        ai.setTransitionAI(new RangeTransition(0f));
        ai.setFightAI(new CollideAI(0f));
    }

    private void setupPositionComponent() {
        new PositionComponent(this);
    }

    private void setupVelocityComponent() {
        new VelocityComponent(
            this,
            0.06f,
            0.06f,
            AnimationBuilder.buildAnimation("character/monster/ghost"),
            AnimationBuilder.buildAnimation("character/monster/ghost"));
    }

    private void setupDrawComponent() {
        new DrawComponent(this, AnimationBuilder.buildAnimation("character/monster/ghost"));
    }

    /**
     * Sets a new position for the ghost.
     */
    public void setNewPosition() {
        setupPositionComponent();
    }

    /**
     * Returns the instance of the ghost.
     *
     * @return The instance of the ghost
     */
    public static Ghost getInstance() {
        return ghost;
    }

}
