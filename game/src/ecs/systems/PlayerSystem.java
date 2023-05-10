package ecs.systems;

import com.badlogic.gdx.Gdx;
import configuration.KeyboardConfig;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.SkillComponent;
import ecs.components.xp.XPComponent;
import ecs.entities.Entity;
import ecs.tools.interaction.InteractionTool;
import starter.Game;

/**
 * Used to control the player
 */
public class PlayerSystem extends ECS_System {

    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc, SkillComponent sc, XPComponent xc) {
    }

    @Override
    public void update() {
        Game.getEntities().stream()
            .flatMap(e -> e.getComponent(PlayableComponent.class).stream())
            .map(pc -> buildDataObject((PlayableComponent) pc))
            .forEach(this::checkKeystroke);
    }

    private void checkKeystroke(KSData ksd) {
        if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get()) && Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get())) {
            ksd.vc.setCurrentYVelocity(0.75f * ksd.vc.getYVelocity());
            ksd.vc.setCurrentXVelocity(0.75f * ksd.vc.getXVelocity());
        } else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get()) && Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get())) {
            ksd.vc.setCurrentYVelocity(0.75f * ksd.vc.getYVelocity());
            ksd.vc.setCurrentXVelocity(-0.75f * ksd.vc.getXVelocity());
        } else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get()) && Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get())) {
            ksd.vc.setCurrentYVelocity(-0.75f * ksd.vc.getYVelocity());
            ksd.vc.setCurrentXVelocity(0.75f * ksd.vc.getXVelocity());
        } else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get()) && Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get())) {
            ksd.vc.setCurrentYVelocity(-0.75f * ksd.vc.getYVelocity());
            ksd.vc.setCurrentXVelocity(-0.75f * ksd.vc.getXVelocity());
        } else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get()))
            ksd.vc.setCurrentYVelocity(1 * ksd.vc.getYVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get()))
            ksd.vc.setCurrentYVelocity(-1 * ksd.vc.getYVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get()))
            ksd.vc.setCurrentXVelocity(1 * ksd.vc.getXVelocity());
        else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get()))
            ksd.vc.setCurrentXVelocity(-1 * ksd.vc.getXVelocity());

        if (Gdx.input.isKeyPressed(KeyboardConfig.INTERACT_WORLD.get()))
            InteractionTool.interactWithClosestInteractable(ksd.e);
            // check skills
        else if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get()) && ksd.sc.getSkillFromList(0) != null)
            ksd.sc.getSkillFromList(0).execute(ksd.e);
        else if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get()) && ksd.sc.getSkillFromList(1) != null)
            ksd.sc.getSkillFromList(1).execute(ksd.e);
        else if (Gdx.input.isKeyPressed(KeyboardConfig.THIRD_SKILL.get()) && ksd.sc.getSkillFromList(2) != null)
            ksd.sc.getSkillFromList(2).execute(ksd.e);
        else if (Gdx.input.isKeyPressed(KeyboardConfig.FOURTH_SKILL.get()) && ksd.sc.getSkillFromList(3) != null)
            ksd.sc.getSkillFromList(3).execute(ksd.e);
    }

    private KSData buildDataObject(PlayableComponent pc) {
        Entity e = pc.getEntity();

        VelocityComponent vc =
            (VelocityComponent)
                e.getComponent(VelocityComponent.class)
                    .orElseThrow(PlayerSystem::missingVC);

        SkillComponent sc =
            (SkillComponent)
                e.getComponent(SkillComponent.class)
                    .orElseThrow(PlayerSystem::missingSC);

        XPComponent xc =
            (XPComponent)
                e.getComponent(XPComponent.class)
                    .orElseThrow(PlayerSystem::missingXC);


        return new KSData(e, pc, vc, sc, xc);
    }

    private static MissingComponentException missingVC() {
        return new MissingComponentException("VelocityComponent");
    }

    private static MissingComponentException missingSC() {
        return new MissingComponentException("SkillComponent");
    }

    private static MissingComponentException missingXC() {
        return new MissingComponentException("XPComponent");
    }
}
