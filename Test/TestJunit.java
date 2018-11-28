import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simulation.Air;
import simulation.Dirt;
import simulation.Grass;
import simulation.MainSimulation;

import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class TestJunit {

    private MainSimulation Testsimulation;
    private int row = 32;
    private int col = 32;

    @Before
    public void setUp() {

        this.Testsimulation = new MainSimulation(row,col);

    }

    @Test
    public void Test1CheckRows() {

        assertEquals(32, Testsimulation.getRows());
    }
    @Test
    public void Test2CheckCols() {

        assertEquals(32, Testsimulation.getColumns());
    }


    @Test
    public void Test3CheckSpeed() {
        boolean ex = false;
        this.Testsimulation.setSpeed(10);
        if (this.Testsimulation.getSpeed() == 10){
            ex = true;
        }
        assertTrue(ex);

    }

    @Test
    public void Test4setEntity() {
         //this.Testsimulation.setEntity(99,200, new  Dirt(this.Testsimulation, null, 0, 99, 200));
    }
    @Test
    public void NutrientTest1() {

        this.Testsimulation.getEntity(3,3).setNutrients(90000);
    }
    @Test
    public void NutrientTest2() {

        this.Testsimulation.getEntity(3,3).setNutrients(-5000);
    }
    @Test
    public void NutrientTest3() {

        this.Testsimulation.getEntity(3,3).setNutrients(12);
        assertEquals(12,this.Testsimulation.getEntity(3,3).getNutrients());

    }











    }


