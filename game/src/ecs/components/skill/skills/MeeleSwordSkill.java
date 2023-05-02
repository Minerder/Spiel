package ecs.components.skill.skills;

import ecs.components.skill.ITargetSelection;
import ecs.components.skill.DamageMeleeSkill;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class MeeleSwordSkill extends DamageMeleeSkill {
    public MeeleSwordSkill(ITargetSelection selectionFunction) {
        super("character/knight/attack",
            new Damage(1, DamageType.PHYSICAL, null),
            new Point(1, 1),
            selectionFunction);

    }
}
