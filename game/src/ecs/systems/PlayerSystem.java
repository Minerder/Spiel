package ecs.systems;

import com.badlogic.gdx.Gdx;
import configuration.KeyboardConfig;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.SkillComponent;
import ecs.components.skill.skills.Skill;
import ecs.entities.Entity;
import ecs.tools.interaction.InteractionTool;
import starter.Game;

/**
 * Used to control the player
 */
public class PlayerSystem extends ECS_System {

    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc, SkillComponent sc) {
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

        Object[] skills = ksd.sc.getSkillSet().toArray();
        if (Gdx.input.isKeyPressed(KeyboardConfig.INTERACT_WORLD.get()))
            InteractionTool.interactWithClosestInteractable(ksd.e);
            // check skills
        else if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get()) && skills[0] != null)
            ((Skill) skills[0]).execute(ksd.e);
        else if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get()) && skills[1] != null)
            ((Skill) skills[1]).execute(ksd.e);
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


        return new KSData(e, pc, vc, sc);
    }

    private static MissingComponentException missingVC() {
        return new MissingComponentException("VelocityComponent");
    }
    private static MissingComponentException missingSC() {
        return new MissingComponentException("SkillComponent");
    }
}
