package contrib.entities.monster;

import contrib.components.AIComponent;
import contrib.utils.components.ai.idle.PatrouilleWalk;
import contrib.utils.components.ai.idle.SleepingAI;
import contrib.utils.components.ai.transition.SelfDefendTransition;
import core.components.DrawComponent;
import core.utils.components.draw.Animation;
import dslToGame.AnimationBuilder;

public class Rat extends Monster {

    public Rat() {
        super(
                1,
                0.1f,
                0.1f,
                10,
                "character/monster/rat/run/right",
                "character/monster/rat/run/left",
                "character/monster/rat/sleep/right",
                "character/monster/rat/sleep/left");
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAIComponent();
        setupAnimationComponent();
        setupHitBoxComponent();
        setupXPComponent();
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);

        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/rat/idle/right");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/rat/idle/left");

        ai.setIdleAI(
                new SleepingAI(
                        new PatrouilleWalk(20, 6, 2000, PatrouilleWalk.MODE.RANDOM),
                        1.5f,
                        new DrawComponent(this, idleLeft, idleRight)));
        ai.setTransitionAI(new SelfDefendTransition());
    }
}
