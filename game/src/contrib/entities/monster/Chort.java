package contrib.entities.monster;

import contrib.components.AIComponent;
import contrib.components.CollideComponent;
import contrib.components.HealthComponent;
import contrib.utils.components.ai.fight.CollideAI;
import contrib.utils.components.ai.idle.PatrouilleWalk;
import contrib.utils.components.ai.transition.RangeTransition;

import core.components.PlayerComponent;

public class Chort extends Monster {

    public Chort() {
        super(
                4,
                0.15f,
                0.15f,
                30,
                "character/monster/chort/runRight",
                "character/monster/chort/runLeft",
                "character/monster/chort/idleRight",
                "character/monster/chort/idleLeft");
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAIComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
        setupXPComponent();
    }

    @Override
    protected void setupAIComponent() {
        new AIComponent(
                this,
                new CollideAI(1),
                new PatrouilleWalk(100, 6, 3, PatrouilleWalk.MODE.RANDOM),
                new RangeTransition(3));
    }

    @Override
    protected void setupHitBoxComponent() {
        new CollideComponent(
                this,
                (a, b, from) -> {
                    if (b.getComponent(PlayerComponent.class).isPresent()) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        (hc) ->
                                                ((HealthComponent) hc)
                                                        .setCurrentHealthpoints(
                                                                ((HealthComponent) hc)
                                                                                .getCurrentHealthpoints()
                                                                        - 2));
                    }
                },
                null);
    }
}
