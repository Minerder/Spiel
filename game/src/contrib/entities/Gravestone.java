package contrib.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import contrib.components.CollideComponent;
import contrib.components.HealthComponent;
import contrib.components.XPComponent;
import contrib.utils.components.ai.AITools;

import core.Entity;
import core.Game;
import core.components.DrawComponent;
import core.components.PlayerComponent;
import core.components.PositionComponent;

import dslToGame.AnimationBuilder;

import java.util.logging.Logger;

public class Gravestone extends Entity {
    private Sound sound;
    private static final Logger LOGGER = Logger.getLogger(Gravestone.class.getName());
    private static final Gravestone gravestone = new Gravestone();
    private final float detectionRange = 3f;

    private Gravestone() {
        super();
        setupPositionComponent();
        setupDrawComponent();
        setupCollideComponent();
    }

    private void setupPositionComponent() {
        new PositionComponent(this);
    }

    private void setupDrawComponent() {
        new DrawComponent(this, AnimationBuilder.buildAnimation("dungeon/gravestone.png"));
    }

    private void setupCollideComponent() {
        new CollideComponent(
                this,
                (a, b, from) -> {
                    if (b.getComponent(PlayerComponent.class).isPresent()) {
                        if (AITools.entityInRange(Ghost.getInstance(), b, detectionRange)) {
                            b.getComponent(XPComponent.class)
                                    .ifPresent(xpc -> ((XPComponent) xpc).addXP(10));

                            b.getComponent(HealthComponent.class)
                                    .ifPresent(
                                            hc ->
                                                    ((HealthComponent) hc)
                                                            .setCurrentHealthpoints(
                                                                    ((HealthComponent) hc)
                                                                            .getMaximalHealthpoints()));

                            Game.removeEntity(this);
                            try {
                                sound =
                                        Gdx.audio.newSound(
                                                Gdx.files.internal(
                                                        "game/assets/sounds/items/POTION-2.mp3"));
                                sound.play(0.3f);
                                LOGGER.info("Sounds from Gravestone played successfully");
                            } catch (GdxRuntimeException e) {
                                LOGGER.warning("Sound file could not be found!");
                            }
                            Game.removeEntity(Ghost.getInstance());
                        }
                    }
                },
                null);
    }

    /** Sets a new position for the gravestone. */
    public void setNewPosition() {
        setupPositionComponent();
    }

    /**
     * Returns the instance of the gravestone.
     *
     * @return The instance of the gravestone
     */
    public static Gravestone getInstance() {
        return gravestone;
    }
}
