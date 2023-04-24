package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;

public class Skeleton extends Monster {

    public Skeleton(){
        super(3, 0.05f, 0.05f);
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
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/skeleton/run/right");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/skeleton/run/left");
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    @Override
    protected void setupHealthComponent() {
        new HealthComponent(this);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/skeleton/idle/right");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/skeleton/idle/left");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new StaticRadiusWalk(4,2));
        ai.setTransitionAI(new RangeTransition(3));
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
