package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.skill.SkillComponent;
import ecs.components.skill.SkillTools;
import ecs.components.skill.skills.HomingSparkSkill;
import ecs.components.skill.skills.MeeleSwordSkill;
import ecs.components.skill.skills.Skill;
import graphic.Animation;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to set up the hero
 * with all its components and attributes .
 */
public class Hero extends Entity {

    private final int fireballCoolDown = 1;
    private final float xSpeed = 0.2f;
    private final float ySpeed = 0.2f;

    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";

    /**
     * Entity with Components
     */
    public Hero() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        new PlayableComponent(this);
        setupSkillComponent();
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupSkillComponent() {
        SkillComponent sc = new SkillComponent(this);
        sc.addSkill(new Skill(new MeeleSwordSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown));
        sc.addSkill(new Skill(new HomingSparkSkill(() -> SkillTools.getNearestEntityPosition(this)), fireballCoolDown));
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println("heroCollisionEnter"),
            (you, other, direction) -> System.out.println("heroCollisionLeave"));
    }
}
