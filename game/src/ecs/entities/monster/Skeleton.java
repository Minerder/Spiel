package ecs.entities.monster;

import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.skills.BouncingArrowSkill;
import ecs.components.skill.skills.Skill;
import ecs.components.skill.SkillTools;

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
        new AIComponent(this,
            new MeleeAI(3, new Skill(new BouncingArrowSkill(SkillTools::getHeroPosition, 1), 2)),
            new StaticRadiusWalk(3f, 3),
            new RangeTransition(3));
    }
}
