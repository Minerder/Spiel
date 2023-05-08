package ecs.entities.monster;

import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.skills.FireballSkill;
import ecs.components.skill.skills.Skill;
import ecs.components.skill.SkillTools;

public class Imp extends Monster {

    public Imp() {
        super(
            2,
            0.15f,
            0.15f,
            4,
            "character/monster/imp/runRight",
            "character/monster/imp/runLeft",
            "character/monster/imp/idleRight",
            "character/monster/imp/idleLeft");
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAIComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
        setupXPComponent();
    }

    @Override
    protected void setupAIComponent() {
        new AIComponent(this,
            new MeleeAI(3, new Skill(new FireballSkill(SkillTools::getHeroPosition), 2)),
            new StaticRadiusWalk(3f, 3),
            new RangeTransition(3));
    }
}
