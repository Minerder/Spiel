package ecs.entities.monster;

import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.SkillComponent;
import ecs.components.skill.SkillTools;
import ecs.components.skill.skills.BouncingArrowSkill;
import ecs.components.skill.skills.Skill;

public class Skeleton extends Monster {

    public Skeleton() {
        super(
            3,
            0.05f,
            0.05f,
            4,
            "character/monster/skeleton/run/right",
            "character/monster/skeleton/run/left",
            "character/monster/skeleton/idle/right",
            "character/monster/skeleton/idle/left");
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
        SkillComponent sc = new SkillComponent(this);
        Skill skill = new Skill(new BouncingArrowSkill(SkillTools::getHeroPosition, 1), 2);
        sc.addSkill(skill);
        new AIComponent(this,
            new MeleeAI(3, skill),
            new StaticRadiusWalk(3f, 3),
            new RangeTransition(3));
    }
}
