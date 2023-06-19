package contrib.entities.monster;

import contrib.components.AIComponent;
import contrib.components.CollideComponent;
import contrib.components.HealthComponent;
import contrib.components.InteractionComponent;
import contrib.entities.EntityFactory;
import contrib.utils.components.ai.fight.CollideAI;
import contrib.utils.components.ai.idle.StaticRadiusWalk;
import contrib.utils.components.ai.transition.RangeTransition;
import contrib.utils.components.health.IOnDeathFunction;
import contrib.utils.components.interaction.IInteraction;

import core.Entity;
import core.components.DrawComponent;
import core.components.PlayerComponent;
import core.components.PositionComponent;

import core.utils.Point;
import dslToGame.AnimationBuilder;

public class MonsterChest extends Monster implements IOnDeathFunction, IInteraction {

    public MonsterChest() {
        super(
                5,
                0.15f,
                0.15f,
                15,
                "objects/treasurechest/monsterChest",
                "objects/treasurechest/monsterChest",
                "objects/treasurechest/chest_full_open_anim_f0.png",
                "objects/treasurechest/chest_full_open_anim_f0.png");
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupDrawComponent();
        setupHitBoxComponent();
        setupXPComponent();
        setupInteractionComponent();
    }

    @Override
    protected void setupAIComponent() {
        new AIComponent(
                this, new CollideAI(2f), new StaticRadiusWalk(2f, 1), new RangeTransition(3f));
    }

    @Override
    protected void setupHealthComponent() {
        super.setupHealthComponent();
        HealthComponent hc =
                (HealthComponent) this.getComponent(HealthComponent.class).orElseThrow();
        hc.setOnDeath(this);
    }

    @Override
    protected void setupHitBoxComponent() {
        new CollideComponent(this,
            CollideComponent.DEFAULT_OFFSET,
            new Point(0.75f, 0.75f),
            ((a,b,from) -> {
                if (b.getComponent(PlayerComponent.class).isEmpty()) return;
                if (b.getComponent(HealthComponent.class).isEmpty()) return;
                HealthComponent hc =
                        (HealthComponent) b.getComponent(HealthComponent.class).get();
                hc.setCurrentHealthpoints(hc.getCurrentHealthpoints() - 1);
            }), null);
    }

    private void setupInteractionComponent() {
        final float defaultInteractionRadius = 1f;
        new InteractionComponent(this, defaultInteractionRadius, false, this);
    }

    @Override
    public void onDeath(Entity entity) {
        Entity chest = EntityFactory.getChest();
        PositionComponent chestPc =
                (PositionComponent) chest.getComponent(PositionComponent.class).orElseThrow();
        PositionComponent pc =
                (PositionComponent) this.getComponent(PositionComponent.class).orElseThrow();
        chestPc.setPosition(pc.getPosition());
    }

    @Override
    public void onInteraction(Entity entity) {
        setupAIComponent();
        this.removeComponent(DrawComponent.class);
        new DrawComponent(
                this, AnimationBuilder.buildAnimation("objects/treasurechest/monsterChest"));
    }
}
