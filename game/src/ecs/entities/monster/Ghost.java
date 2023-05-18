package ecs.entities.monster;

import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.FollowHero;
import ecs.components.ai.transition.RangeTransition;


public class Ghost extends Monster {

    public Ghost() {
        super(
            2,
            0.15f,
            0.15f,
            0,
            "character/monster/ghost/idleLeft",
            "character/monster/ghost/idleLeft",
            "character/monster/ghost/idleLeft",
            "character/monster/ghost/idleLeft");
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAIComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new FollowHero(30, 5, 3, FollowHero.MODE.RANDOM));
        ai.setTransitionAI(new RangeTransition(3f));
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this, (a, b, from) -> {
            if (b.getComponent(PlayableComponent.class).isPresent()) {
                b.getComponent(HealthComponent.class).ifPresent((hc) -> ((HealthComponent) hc).setCurrentHealthpoints(((HealthComponent) hc).getCurrentHealthpoints() - 1));
            }
        },
            null);
    }
}


