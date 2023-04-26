package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.SleepingAI;
import ecs.components.ai.transition.SelfDefendTransition;
import graphic.Animation;

public class Rat extends Monster {

    public Rat() {
        super(1, 0.1f, 0.1f);
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAIComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
    }

    @Override
    protected void setupPositionComponent() {
        new PositionComponent(this);
    }

    @Override
    protected void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/rat/run/right");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/rat/run/left");
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    @Override
    protected void setupHealthComponent() {
        new HealthComponent(this);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/rat/sleep/right");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/rat/sleep/left");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);

        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/rat/idle/right");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/rat/idle/left");

        ai.setIdleAI(new SleepingAI(new PatrouilleWalk(20, 6, 2000, PatrouilleWalk.MODE.RANDOM), 1.5f,new AnimationComponent(this, idleLeft, idleRight)));
        ai.setTransitionAI(new SelfDefendTransition());
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
