package contrib.entities.monster;


import contrib.components.AIComponent;
import contrib.components.SkillComponent;
import contrib.utils.components.ai.fight.MeleeAI;
import contrib.utils.components.ai.idle.StaticRadiusWalk;
import contrib.utils.components.ai.transition.RangeTransition;
import contrib.utils.components.skill.SkillTools;
import contrib.utils.components.skill.skills.BouncingArrowSkill;
import contrib.utils.components.skill.Skill;

public class Skeleton extends Monster {

    public Skeleton() {
        super(
            3,
            0.05f,
            0.05f,
            20,
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
