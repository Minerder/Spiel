package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.FireballSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import graphic.Animation;

public class Imp extends Monster {

    public Imp(){
        super(2, 0.15f, 0.15f);
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAnimationComponent();
        setupAIComponent();
        setupFireballSkill();
        setupHitBoxComponent();
    }

    @Override
    protected void setupPositionComponent() {
        new PositionComponent(this);
    }

    @Override
    protected void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/imp/runRight");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/imp/runLeft");
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    @Override
    protected void setupHealthComponent() {
        new HealthComponent(this);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/imp/idleRight");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/imp/idleLeft");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new StaticRadiusWalk(4,2));
        ai.setTransitionAI(new RangeTransition(3));
    }

    private void setupFireballSkill() {
        skill =
            new Skill(
                new FireballSkill(SkillTools::getCursorPositionAsPoint), 1);
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
