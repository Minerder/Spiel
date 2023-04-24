package ecs.systems;

import ecs.components.AnimationComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import graphic.Animation;
import graphic.Painter;
import graphic.PainterConfig;

import java.util.HashMap;
import java.util.Map;

import starter.Game;
import tools.Point;

/**
 * used to draw entities
 */
public class DrawSystem extends ECS_System {

    private final Painter painter;
    private final Map<String, PainterConfig> configs;

    private record DSData(Entity e, AnimationComponent ac, PositionComponent pc) {
    }

    /**
     * @param painter PM-Dungeon painter to draw
     */
    public DrawSystem(Painter painter) {
        super();
        this.painter = painter;
        configs = new HashMap<>();
    }

    /**
     * draw entities at their position
     */
    public void update() {
        Game.getEntities().stream()
            .flatMap(e -> e.getComponent(AnimationComponent.class).stream())
            .map(ac -> buildDataObject((AnimationComponent) ac))
            .forEach(this::draw);
    }

    private void draw(DSData dsd) {
        final Animation animation = dsd.ac.getCurrentAnimation();
        String currentAnimationTexture = animation.getNextAnimationTexturePath();
        if (!configs.containsKey(currentAnimationTexture)) {
            configs.put(currentAnimationTexture, new PainterConfig(currentAnimationTexture));
        }
        painter.draw(
            new Point(dsd.pc.getPosition().x - 0.5f, dsd.pc.getPosition().y - 0.2f),
            currentAnimationTexture,
            configs.get(currentAnimationTexture));
    }

    private DSData buildDataObject(AnimationComponent ac) {
        Entity e = ac.getEntity();

        PositionComponent pc =
            (PositionComponent)
                e.getComponent(PositionComponent.class).orElseThrow(DrawSystem::missingPC);

        return new DSData(e, ac, pc);
    }

    @Override
    public void toggleRun() {
        // DrawSystem cant pause
        run = true;
    }

    private static MissingComponentException missingPC() {
        return new MissingComponentException("PositionComponent");
    }
}
