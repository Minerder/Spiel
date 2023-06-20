package contrib.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import contrib.components.HealthComponent;
import contrib.components.SkillComponent;
import contrib.utils.components.skill.Skill;
import contrib.utils.components.skill.SkillTools;
import contrib.utils.components.skill.skills.FrostNovaSkill;
import contrib.utils.components.skill.skills.GravityStormSkill;
import contrib.utils.components.xp.ILevelUp;

import core.Game;

import java.util.logging.Logger;

public class HeroLevelUp implements ILevelUp {
    private static final Logger LOGGER = Logger.getLogger(HeroLevelUp.class.getName());

    @Override
    public void onLevelUp(long nexLevel) {
        System.out.println("Hero has reached Lvl " + nexLevel + "! You gained 1 extra hitpoint.");

        HealthComponent hc =
                (HealthComponent)
                        Game.getHero()
                                .orElseThrow()
                                .getComponent(HealthComponent.class)
                                .orElseThrow();
        SkillComponent sc =
                (SkillComponent)
                        Game.getHero()
                                .orElseThrow()
                                .getComponent(SkillComponent.class)
                                .orElseThrow();
        hc.setMaximalHealthpoints(hc.getMaximalHealthpoints() + 1);
        hc.setCurrentHealthpoints(hc.getMaximalHealthpoints());

        sc.setMaxMana(sc.getMaxMana() + 1);

        switch ((int) nexLevel) {
            case 5 -> {
                System.out.println(
                        "You unlocked the Frost Nova Skill! Press '1' to create a Frost Nova that slows nearby enemies by 50%");
                sc.addSkill(new Skill(new FrostNovaSkill(SkillTools::getHeroPosition), 10, 2));
            }
            case 10 -> {
                System.out.println(
                        "You unlocked the Gravity Storm Skill! Press '2' to shoot a Storm which pulls enemies towards it.");
                sc.addSkill(
                        new Skill(
                                new GravityStormSkill(SkillTools::getCursorPositionAsPoint),
                                30,
                                5));
            }
        }
        try {
            Sound sound =
                    Gdx.audio.newSound(
                            Gdx.files.internal("game/assets/sounds/levelup/LEVELUP.mp3"));
            sound.play(0.5f);
            LOGGER.info("Levelup Sounds played successfully");
        } catch (GdxRuntimeException e) {
            LOGGER.warning("Sound file could not be found!");
        }
    }
}
