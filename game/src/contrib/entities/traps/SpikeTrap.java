package contrib.entities.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import contrib.components.CollideComponent;
import contrib.components.HealthComponent;

import core.components.PlayerComponent;
import core.utils.Point;

import java.util.logging.Logger;

public class SpikeTrap extends Trap {
    private Sound sound;
    private static final Logger LOGGER = Logger.getLogger(SpikeTrap.class.getName());

    private boolean activated;
    private final Lever lever;

    /** Creates a new SpikeTrap which damages the player when he steps on it */
    public SpikeTrap() {
        super("dungeon/traps/spikeTrap/spikes_1.png", "dungeon/traps/spikeTrap/spikes_4.png");
        setupCollideComponent();
        setupPositionComponent();
        setupPositionComponent();
        lever = new Lever(this);
        activated = false;
    }

    private void setupCollideComponent() {
        new CollideComponent(
                this,
                CollideComponent.DEFAULT_OFFSET,
                new Point(1, 1),
                (a, b, from) -> {
                    if (b.getComponent(PlayerComponent.class).isEmpty()) return;
                    if (b.getComponent(HealthComponent.class).isEmpty()) return;
                    if (lever.getPressed()) return;
                    HealthComponent hc =
                            (HealthComponent) b.getComponent(HealthComponent.class).get();
                    if (!activated) {
                        hc.setCurrentHealthpoints(hc.getCurrentHealthpoints() - 2);
                    } else {
                        hc.setCurrentHealthpoints(hc.getCurrentHealthpoints() - 1);
                    }
                    activated = true;
                    setActivationAnimation();
                    try {
                        sound =
                                Gdx.audio.newSound(
                                        Gdx.files.internal("game/assets/sounds/traps/SPIKE.mp3"));
                        sound.play(0.5f);
                        LOGGER.info("Sounds from SpikeTrap played successfully");
                    } catch (GdxRuntimeException e) {
                        LOGGER.warning("Sound file could not be found!");
                    }
                },
                null);
    }
}
