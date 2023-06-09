package contrib.entities.monster;

import contrib.components.AIComponent;
import contrib.components.SkillComponent;
import contrib.utils.components.ai.fight.MeleeAI;
import contrib.utils.components.ai.idle.StaticRadiusWalk;
import contrib.utils.components.ai.transition.RangeTransition;
import contrib.utils.components.skill.Skill;
import contrib.utils.components.skill.SkillTools;
import contrib.utils.components.skill.skills.FireballSkill;

public class Imp extends Monster {

    public Imp() {
        super(
                2,
                0.15f,
                0.15f,
                15,
                "character/monster/imp/runRight",
                "character/monster/imp/runLeft",
                "character/monster/imp/idleRight",
                "character/monster/imp/idleLeft");
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAIComponent();
        setupDrawComponent();
        setupCollideComponent();
        setupXPComponent();
    }

    @Override
    protected void setupAIComponent() {
        SkillComponent sc = new SkillComponent(this, 5);
        Skill skill = new Skill(new FireballSkill(SkillTools::getHeroPosition), 2, 3);
        sc.addSkill(skill);
        new AIComponent(
                this, new MeleeAI(3, skill), new StaticRadiusWalk(3f, 3), new RangeTransition(3));
    }
}
