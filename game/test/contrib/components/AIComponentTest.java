package contrib.components;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import contrib.utils.components.ai.IFightAI;
import contrib.utils.components.ai.IIdleAI;
import contrib.utils.components.ai.ITransition;

import core.Entity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AIComponentTest {

    private AIComponent aiComponent;
    private final IFightAI mockFightAI = mock(IFightAI.class);

    private final IIdleAI mockIdleAI = mock(IIdleAI.class);

    private final ITransition mockTransition = mock(ITransition.class);

    private final Entity entity = new Entity();

    @Before
    public void setup() {
        aiComponent = new AIComponent(entity, mockFightAI, mockIdleAI, mockTransition);
    }

    @Test
    public void executeFight() {
        when(mockTransition.isInFightMode(entity)).thenReturn(true);
        aiComponent.execute();
        verify(mockFightAI, times(1)).fight(entity);
        verify(mockIdleAI, never()).idle(entity);
    }

    @Test
    public void executeIdle() {
        when(mockTransition.isInFightMode(entity)).thenReturn(false);
        aiComponent.execute();
        verify(mockFightAI, never()).fight(entity);
        verify(mockIdleAI, times(1)).idle(entity);
    }

    @Test
    public void setFightAI() {
        IFightAI newAI = Mockito.mock(IFightAI.class);
        aiComponent.setFightAI(newAI);
        assertEquals(newAI, aiComponent.getFightAI());
    }

    @Test
    public void setIdleAI() {
        IIdleAI newAI = Mockito.mock(IIdleAI.class);
        aiComponent.setIdleAI(newAI);
        assertEquals(newAI, aiComponent.getIdleAI());
    }

    @Test
    public void setTransitionAI() {
        ITransition newAI = Mockito.mock(ITransition.class);
        aiComponent.setTransitionAI(newAI);
        assertEquals(newAI, aiComponent.getTransitionAI());
    }
}
