package ecs.entities.monster;

import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.RangeTransition;

public class Chort extends Monster {

    public Chort() {
        super(
            4,
            0.15f,
            0.15f,
            5,
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
        new AIComponent(this,
            new CollideAI(0.1f),
            new PatrouilleWalk(100, 6, 3, PatrouilleWalk.MODE.RANDOM),
            new RangeTransition(3));
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this, (a, b, from) -> {
            if (b.getComponent(PlayableComponent.class).isPresent()) {
                b.getComponent(HealthComponent.class).ifPresent((hc) -> ((HealthComponent) hc).setCurrentHealthpoints(((HealthComponent) hc).getCurrentHealthpoints() - 2));
            }
        },
            null);
    }
}
