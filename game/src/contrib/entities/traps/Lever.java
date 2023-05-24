package contrib.entities.traps;

import contrib.components.InteractionComponent;
import core.Entity;
import core.components.DrawComponent;
import core.components.PositionComponent;
import core.utils.components.draw.Animation;
import dslToGame.AnimationBuilder;

public class Lever extends Entity {

    private boolean pressed;
    private final Animation activated;
    private final DrawComponent dc;
    private final Trap trap;

    public Lever(Trap trap) {
        super();
        Animation idle = AnimationBuilder.buildAnimation("dungeon/traps/lever.png");
        activated = AnimationBuilder.buildAnimation("dungeon/traps/lever_activated.png");
        dc = new DrawComponent(this, idle);
        setupInteractionComponent();
        setupPositionComponent();
        this.trap = trap;
        pressed = false;
    }

    /**
     * Returns whether the lever is pressed or not
     *
     * @return True if the lever is pressed, otherwise false
     */
    public boolean getPressed() {
        return pressed;
    }

    private void setActivated() {
        if (dc != null) dc.setCurrentAnimation(activated);
    }

    private void setupPositionComponent() {
        new PositionComponent(this);
    }

    private void setupInteractionComponent() {
        new InteractionComponent(this, 2, false,
            a -> {
                pressed = true;
                trap.startIdleAnimation();
                setActivated();
            });
    }

}
