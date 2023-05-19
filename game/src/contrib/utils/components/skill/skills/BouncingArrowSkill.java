package contrib.utils.components.skill.skills;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import contrib.utils.components.skill.DamageProjectileSkill;
import contrib.utils.components.skill.ITargetSelection;
import core.utils.Point;

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
