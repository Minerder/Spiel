package core.systems;

import com.badlogic.gdx.Gdx;

import contrib.components.SkillComponent;
import contrib.components.XPComponent;
import contrib.configuration.KeyboardConfig;
import contrib.utils.components.interaction.InteractionTool;

import core.Entity;
import core.Game;
import core.System;
import core.components.PlayerComponent;
import core.components.VelocityComponent;
import core.hud.Inventory.InventoryGUI;
import core.utils.components.MissingComponentException;

/** Used to control the player */
public class PlayerSystem extends System {

    private record KSData(
            Entity e,
            PlayerComponent pc,
            VelocityComponent vc,
            SkillComponent sc,
            XPComponent xc) {}

    @Override
    public void update() {
        Game.getEntities().stream()
                .flatMap(e -> e.getComponent(PlayerComponent.class).stream())
                .map(pc -> buildDataObject((PlayerComponent) pc))
                .forEach(this::checkKeystroke);
    }

    private void checkKeystroke(KSData ksd) {
        if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get())
                && Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get())) {
            ksd.vc.setCurrentYVelocity(0.75f * ksd.vc.getYVelocity());
            ksd.vc.setCurrentXVelocity(0.75f * ksd.vc.getXVelocity());
        } else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get())
                && Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get())) {
            ksd.vc.setCurrentYVelocity(0.75f * ksd.vc.getYVelocity());
            ksd.vc.setCurrentXVelocity(-0.75f * ksd.vc.getXVelocity());
        } else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get())
                && Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get())) {
            ksd.vc.setCurrentYVelocity(-0.75f * ksd.vc.getYVelocity());
            ksd.vc.setCurrentXVelocity(0.75f * ksd.vc.getXVelocity());
        } else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get())
                && Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get())) {
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

        if (Gdx.input.isKeyJustPressed(KeyboardConfig.INTERACT_WORLD.get()))
            InteractionTool.interactWithClosestInteractable(ksd.e);
        // check skills
        else if (Gdx.input.isKeyPressed(KeyboardConfig.EQUIPMENT_SLOT.get())
                && ksd.sc.getSkillFromList(0) != null) ksd.sc.getSkillFromList(0).execute(ksd.e);
        else if (Gdx.input.isKeyPressed(KeyboardConfig.SKILL_SLOT_1.get())
                && ksd.sc.getSkillFromList(1) != null) ksd.sc.getSkillFromList(1).execute(ksd.e);
        else if (Gdx.input.isKeyPressed(KeyboardConfig.SKILL_SLOT_2.get())
                && ksd.sc.getSkillFromList(2) != null) ksd.sc.getSkillFromList(2).execute(ksd.e);
        // open inventory
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
            InventoryGUI.getInstance().toggleInventory();
        }
    }

    private KSData buildDataObject(PlayerComponent pc) {
        Entity e = pc.getEntity();

        VelocityComponent vc =
                (VelocityComponent)
                        e.getComponent(VelocityComponent.class)
                                .orElseThrow(PlayerSystem::missingVC);

        SkillComponent sc =
                (SkillComponent)
                        e.getComponent(SkillComponent.class).orElseThrow(PlayerSystem::missingSC);

        XPComponent xc =
                (XPComponent)
                        e.getComponent(XPComponent.class).orElseThrow(PlayerSystem::missingXC);

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
