package ecs.components.skill.skills;

import ecs.components.skill.DamageProjectileSkill;
import ecs.components.skill.ITargetSelection;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class BouncingArrowSkill extends DamageProjectileSkill {


    public BouncingArrowSkill(ITargetSelection selectionFunction, int bounceAmount) {
        super(
            "skills/arrow/up",
            0.5f,
            new Damage(1, DamageType.PHYSICAL, null),
            new Point(0.5f, 0.5f),
            selectionFunction,
            6f,
            bounceAmount);
    }
}
