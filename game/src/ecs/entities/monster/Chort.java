package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;


public class Chort extends Monster {

    public Chort() {
        super(4, 0.15f, 0.15f);
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
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/chort/runRight");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/chort/runLeft");
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    @Override
    protected void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(4);
        hc.setCurrentHealthpoints(4);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/chort/idleRight");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/chort/idleLeft");
        new AnimationComponent(this, idleLeft, idleRight);
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
