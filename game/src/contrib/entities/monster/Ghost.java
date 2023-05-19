package contrib.entities.monster;


import contrib.components.AIComponent;
import contrib.components.CollideComponent;
import contrib.components.HealthComponent;
import contrib.utils.components.ai.idle.FollowHero;
import contrib.utils.components.ai.transition.RangeTransition;
import core.components.PlayerComponent;

public class Ghost extends Monster {

    public Ghost() {
        super(
            2,
            0.15f,
            0.15f,
            0,
            "character/monster/ghost/idleLeft",
            "character/monster/ghost/idleLeft",
            "character/monster/ghost/idleLeft",
            "character/monster/ghost/idleLeft");
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAIComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new FollowHero(30, 5, 3, FollowHero.MODE.RANDOM));
        ai.setTransitionAI(new RangeTransition(3f));
    }

    @Override
    protected void setupHitBoxComponent() {
        new CollideComponent(this, (a, b, from) -> {
            if (b.getComponent(PlayerComponent.class).isPresent()) {
                b.getComponent(HealthComponent.class).ifPresent((hc) -> ((HealthComponent) hc).setCurrentHealthpoints(((HealthComponent) hc).getCurrentHealthpoints() - 1));
            }
        },
            null);
    }
}


