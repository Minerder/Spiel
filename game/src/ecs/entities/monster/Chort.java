package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;


public class Chort extends Monster {

    public Chort(){
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
        new HealthComponent(this);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/chort/idleRight");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/chort/idleLeft");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new PatrouilleWalk(100,6,3, PatrouilleWalk.MODE.RANDOM));
        ai.setTransitionAI(new RangeTransition(3));
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
