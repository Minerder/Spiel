package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.GhostWalk;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;


public class Ghost extends Monster {

    public Ghost() {
        super(2, 0.15f, 0.15f);
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAnimationComponent();
        setupAIComponent();
        setupHitBoxComponent();
    }

    @Override
    protected void setupPositionComponent() {
        new PositionComponent(this);
    }

    @Override
    protected void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/ghost/idleLeft");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/ghost/idleLeft");
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    @Override
    protected void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(2);
        hc.setCurrentHealthpoints(2);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/ghost/idleLeft");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/ghost/idleLeft");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        //TODO ghostwalk doesnt work
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new GhostWalk(30, 5, 3, GhostWalk.MODE.RANDOM));
        ai.setTransitionAI(new RangeTransition(3f));
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this, (a, b, from) -> {
            if (b.getComponent(PlayableComponent.class).isPresent()) {
                b.getComponent(HealthComponent.class).ifPresent((hc) -> ((HealthComponent) hc).setCurrentHealthpoints(((HealthComponent) hc).getCurrentHealthpoints() - 1));
                System.out.println("dmg");
            }
        },
            null);
    }
}


