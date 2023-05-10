package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.GhostWalk;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;


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
        ai.setIdleAI(new GhostWalk(30, 5, 3, GhostWalk.MODE.RANDOM));
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


