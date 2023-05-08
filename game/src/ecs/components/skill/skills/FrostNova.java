package ecs.components.skill.skills;

import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.UpdateComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import ecs.entities.monster.Monster;
import starter.Game;
import tools.Point;

import java.util.HashMap;

public class FrostNova extends Entity implements IUpdateFunction {
    private static final HashMap<Integer, Entity> entities = new HashMap<>();
    private float holdingTimeInFrames;

    public FrostNova(Point pos, boolean hitbox) {
        this.holdingTimeInFrames = 200;
        new PositionComponent(this, pos);

        ICollide entered = (a, b, from) -> {
            if (b.equals(Game.getHero().get())) return;
            // only continue if Entity has VelocityComponent and AIComponent
            VelocityComponent vc = (VelocityComponent) b.getComponent(VelocityComponent.class).orElse(null);
            AIComponent ac = (AIComponent) b.getComponent(AIComponent.class).orElse(null);
            if (vc == null || ac == null) return;

            // reduce movement speed
            entities.put(b.id, b);
            vc.setXVelocity(((Monster) b).getxSpeed() * 0.5f);
            vc.setYVelocity(((Monster) b).getySpeed() * 0.5f);
        };

        ICollide left = (a, b, from) -> {
            // only continue if Entity has VelocityComponent and AIComponent
            VelocityComponent vc = (VelocityComponent) b.getComponent(VelocityComponent.class).orElse(null);
            AIComponent ac = (AIComponent) b.getComponent(AIComponent.class).orElse(null);
            if (vc == null || ac == null || entities.get(b.id) == null) return;

            // increase movement speed
            entities.remove(b.id);
            vc.setXVelocity(((Monster) b).getxSpeed());
            vc.setYVelocity(((Monster) b).getySpeed());
        };
        if (hitbox) new HitboxComponent(this, new Point(-1f, -1f), new Point(3, 3), entered, left);
        new UpdateComponent(this, this);
    }

    @Override
    public void update(Entity entity) {
        holdingTimeInFrames = Math.max(0, --holdingTimeInFrames);
        if (holdingTimeInFrames == 0 && entities.size() > 0) {
            entities.forEach((key, ent) -> {
                VelocityComponent vc = (VelocityComponent) ent.getComponent(VelocityComponent.class).orElse(null);
                if (vc == null) return;
                vc.setXVelocity(((Monster) ent).getxSpeed());
                vc.setYVelocity(((Monster) ent).getySpeed());
            });
            PositionComponent pc = (PositionComponent) this.getComponent(PositionComponent.class).orElseThrow();
            Game.currentLevel.getTileAt(pc.getPosition().toCoordinate()).setTexturePath("dungeon/default/floor/floor_1.png");
            Game.removeEntity(entity);
        }
    }
}
