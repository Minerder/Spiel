package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.ai.transition.SelfDefendTransition;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.tile.Tile;
import starter.Game;
import tools.Constants;

public class SleepingAI<T> implements IIdleAI {

    private boolean awake = false; // if monster is awake
    public SleepingAI(T walk, Entity entity, AIComponent ac) {
        RangeTransition range = new RangeTransition(3);
        if(range.isInFightMode(Game.getHero().orElseThrow())){ // when hero is in range of 3x3
            awake = true;
            System.out.println("AUFGEWACHT");
        }
        if(awake == true){ // sets after awake to the parameter walk
            if(walk.getClass() == PatrouilleWalk.class){
                ac.setIdleAI((IIdleAI) walk);
                System.out.println("PATROULLIE");
                System.out.println(ac.getIdleAI());
                //ac.execute();
            }
            if(walk.getClass() == RadiusWalk.class){
                ac.setIdleAI((IIdleAI) walk);
            }
            if(walk.getClass() == StaticRadiusWalk.class){
                ac.setIdleAI((IIdleAI) walk);
            }
        }
    }


    @Override
    public void idle(Entity entity) {

    }
}
