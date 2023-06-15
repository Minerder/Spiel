package contrib.entities.traps;

import contrib.components.CollideComponent;
import contrib.components.HealthComponent;

import core.utils.Point;

public class SpikeTrap extends Trap {

    private boolean activated;
    private boolean entered;
    private final Lever lever;

    /** Creates a new SpikeTrap which damages the player when he steps on it */
    public SpikeTrap() {
        super("dungeon/traps/spikeTrap/spikes_1.png", "dungeon/traps/spikeTrap/spikes_4.png");
        setupCollideComponent();
        setupPositionComponent();
        setupPositionComponent();
        lever = new Lever(this);
        activated = false;
        entered = false;
    }

    private void setupCollideComponent() {
        new CollideComponent(
                this,
                CollideComponent.DEFAULT_OFFSET,
                new Point(1, 1),
                (a, b, from) -> {
                    if (b.getComponent(HealthComponent.class).isPresent()) {
                        if (!lever.getPressed()) {
                            if (!activated && !entered) {
                                b.getComponent(HealthComponent.class)
                                        .ifPresent(
                                                hc ->
                                                        ((HealthComponent) hc)
                                                                .setCurrentHealthpoints(
                                                                        ((HealthComponent) hc)
                                                                                        .getCurrentHealthpoints()
                                                                                - 2));
                            } else {
                                b.getComponent(HealthComponent.class)
                                        .ifPresent(
                                                hc ->
                                                        ((HealthComponent) hc)
                                                                .setCurrentHealthpoints(
                                                                        ((HealthComponent) hc)
                                                                                        .getCurrentHealthpoints()
                                                                                - 1));
                            }
                            entered = true;
                            activated = true;
                            setActivationAnimation();
                        }
                    }
                },
                (a, b, from) -> entered = false);
    }
}
