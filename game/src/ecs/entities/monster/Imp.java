package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.skills.FireballSkill;
import ecs.components.skill.skills.Skill;
import ecs.components.skill.SkillTools;
import graphic.Animation;

public class Imp extends Monster {

    public Imp() {
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
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/imp/runRight");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/imp/runLeft");
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
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/imp/idleRight");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/imp/idleLeft");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        new AIComponent(this,
            new MeleeAI(3, new Skill(new FireballSkill(SkillTools::getHeroPosition), 2)),
            new StaticRadiusWalk(3f, 3),
            new RangeTransition(3));
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
