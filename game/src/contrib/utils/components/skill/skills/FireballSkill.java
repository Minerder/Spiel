package contrib.utils.components.skill.skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import contrib.utils.components.skill.DamageProjectileSkill;
import contrib.utils.components.skill.ITargetSelection;

import core.Entity;
import core.utils.Point;

import java.util.logging.Logger;

public class FireballSkill extends DamageProjectileSkill {
    private static final Logger LOGGER = Logger.getLogger(FireballSkill.class.getName());

    public FireballSkill(ITargetSelection targetSelection) {
        super(
                "skills/fireball/fireBall_Down/",
                0.5f,
                new Damage(1, DamageType.FIRE, null),
                new Point(0.5f, 0.5f),
                targetSelection,
                5f);
    }

    @Override
    public void execute(Entity entity) {
        try {
            Sound sound =
                    Gdx.audio.newSound(
                            Gdx.files.internal("game/assets/sounds/skills/FIREBALL.mp3"));
            sound.play(0.5f);
            LOGGER.info("Sounds from FireballSkill played successfully");
        } catch (GdxRuntimeException e) {
            LOGGER.warning("Sound file could not be found!");
        }
        super.execute(entity);
    }
}
