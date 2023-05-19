package contrib.utils.components.skill.skills;


import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import contrib.utils.components.skill.DamageMeleeSkill;
import contrib.utils.components.skill.ITargetSelection;
import core.utils.Point;


public class MeeleSwordSkill extends DamageMeleeSkill {
    public MeeleSwordSkill(ITargetSelection selectionFunction) {
        super("character/knight/attack",
            new Damage(1, DamageType.PHYSICAL, null),
            new Point(1, 1),
            selectionFunction);

    }
}
