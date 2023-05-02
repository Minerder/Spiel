package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.skills.BouncingArrowSkill;
import ecs.components.skill.skills.Skill;
import ecs.components.skill.SkillTools;
import graphic.Animation;

public class Skeleton extends Monster {

    public Skeleton() {
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
        HealthComponent hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(3);
        hc.setCurrentHealthpoints(3);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/skeleton/idle/right");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/skeleton/idle/left");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        new AIComponent(this,
            new MeleeAI(3, new Skill(new BouncingArrowSkill(SkillTools::getHeroPosition, 1), 2)),
            new StaticRadiusWalk(3f, 3),
            new RangeTransition(3));
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
