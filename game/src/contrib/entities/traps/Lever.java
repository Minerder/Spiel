package contrib.entities.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import contrib.components.InteractionComponent;

import core.Entity;
import core.components.DrawComponent;
import core.components.PositionComponent;
import core.utils.Point;
import core.utils.components.draw.Animation;

import dslToGame.AnimationBuilder;

import java.util.logging.Logger;

public class Lever extends Entity {
    private static final Logger LOGGER = Logger.getLogger(Lever.class.getName());
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
        try {
            Sound sound =
                    Gdx.audio.newSound(Gdx.files.internal("game/assets/sounds/traps/LEVER.mp3"));
            sound.play(0.3f);
            LOGGER.info("Sounds from Lever played successfully");
        } catch (GdxRuntimeException e) {
            LOGGER.warning("Sound file could not be found!");
        }
    }

    private void setupPositionComponent() {
        PositionComponent pcc = new PositionComponent(this);
        pcc.setPosition(new Point(pcc.getPosition().x + 0.5f, pcc.getPosition().y + 0.2f));
    }

    private void setupInteractionComponent() {
        new InteractionComponent(
                this,
                1.2f,
                false,
                a -> {
                    pressed = true;
                    trap.startIdleAnimation();
                    setActivated();
                });
    }
}
