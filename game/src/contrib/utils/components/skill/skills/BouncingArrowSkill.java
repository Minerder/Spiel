package contrib.utils.components.skill.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import contrib.utils.components.health.Damage;
import contrib.utils.components.skill.DamageProjectileSkill;
import contrib.utils.components.skill.ITargetSelection;

import core.Entity;
import core.utils.Point;

import java.util.logging.Logger;

public class BouncingArrowSkill extends DamageProjectileSkill {
    private static final Logger LOGGER = Logger.getLogger(BouncingArrowSkill.class.getName());

    public BouncingArrowSkill(ITargetSelection selectionFunction, Damage damage, int bounceAmount) {
        super(
                "skills/arrow/up",
                0.5f,
                damage,
                new Point(0.5f, 0.5f),
                selectionFunction,
                6f,
                bounceAmount);
    }

    @Override
    public void execute(Entity entity) {
        try {
            Sound sound =
                    Gdx.audio.newSound(Gdx.files.internal("game/assets/sounds/skills/arrow.mp3"));
            sound.play(0.5f);
            LOGGER.info("Sounds from BouncingArrowSkill played successfully");
        } catch (GdxRuntimeException e) {
            LOGGER.warning("Sound file could not be found!");
        }
        super.execute(entity);
    }
}
