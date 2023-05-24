package contrib.entities.traps;

import core.Entity;
import core.components.DrawComponent;
import core.components.PositionComponent;
import core.utils.Point;
import core.utils.components.draw.Animation;
import dslToGame.AnimationBuilder;

public class Trap extends Entity {

    private final Animation activation;
    private final Animation idle;
    private final DrawComponent dc;

    /**
     * Creates a new Trap
     *
     * @param idleAnimation       The path to the idle animation
     * @param activationAnimation The path to the activation animation
     */
    public Trap(String idleAnimation, String activationAnimation) {
        super();
        activation = AnimationBuilder.buildAnimation(activationAnimation);
        idle = AnimationBuilder.buildAnimation(idleAnimation);
        dc = new DrawComponent(this, idle);
    }

    /**
     * Sets up the position component of the trap
     */
    public void setupPositionComponent() {
        PositionComponent pcc = new PositionComponent(this);
        pcc.setPosition(new Point(pcc.getPosition().x + 0.5f, pcc.getPosition().y + 0.2f));
    }

    /**
     * Sets the animation of the trap to the activation animation
     */
    public void setActivationAnimation() {
        if (dc != null) dc.setCurrentAnimation(activation);
    }

    /**
     * Sets the animation of the trap to the idle animation
     */
    public void startIdleAnimation() {
        if (dc != null) dc.setCurrentAnimation(idle);
    }

    /**
     * Sets the animation of the trap to the received idle animation
     */
    public void setIdleAnimation(Animation idle) {
        if (dc != null) dc.setCurrentAnimation(idle);
    }

}
