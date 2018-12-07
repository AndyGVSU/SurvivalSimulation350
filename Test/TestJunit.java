package Test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simulation.*;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class TestJunit {

    private MainSimulation Testsimulation;
    private SimulationRecord TestRecord;
    private int row = 32;
    private int col = 32;

    @Before
    public void T1_setUp() {

        this.Testsimulation = new MainSimulation(row, col);
        this.Testsimulation.stepForward();
        this.Testsimulation.stepBackward();
    }
    @Test
    public void T2_CheckRows() {
        assertEquals(32, Testsimulation.getRows());
    }

    @Test
    public void T3_CheckCols() {
        assertEquals(32, Testsimulation.getColumns());
    }

    @Test
    public void T4_CheckSpeed() {
        boolean ex = false;
        this.Testsimulation.setSpeed(10);
        if (this.Testsimulation.getSpeed() == 10) {
            ex = true;
        }
        assertTrue(ex);

    }

    @Test
    public void T5_setEntity() {
        this.Testsimulation.setEntity(21, 21, new Dirt(this.Testsimulation, null, 0, 21, 21));
    }

    @Test
    public void T6_Nutrient() {

        this.Testsimulation.getEntity(3, 3).setNutrients(90000);
    }

    @Test
    public void T7_Nutrient() {

        this.Testsimulation.getEntity(3, 3).setNutrients(-5000);
    }

    @Test
    public void T8_Nutrient() {

        this.Testsimulation.getEntity(3, 3).setNutrients(12);
        assertEquals(12, this.Testsimulation.getEntity(3, 3).getNutrients());

    }

    @Test
    public void T9_CanLive() {
        this.Testsimulation.getEntity(3, 3).setNutrients(12);
        if (this.Testsimulation.getEntity(3, 3).getNutrients() > 0) {
            assertTrue(this.Testsimulation.getEntity(3, 3).canLive());
        }
    }

    @Test
    public void T10_GetColor() {
        for (int i = 0; i < this.Testsimulation.getRows(); i++) {
            for (int j = 0; j < this.Testsimulation.getColumns(); j++) {
                if (this.Testsimulation.getEntity(i, j) instanceof Dirt) {
                    assertEquals(2, this.Testsimulation.getEntity(i, j).getColor());
                }
                if (this.Testsimulation.getEntity(i, j) instanceof Air) {
                    assertEquals(1, this.Testsimulation.getEntity(i, j).getColor());
                }
                if (this.Testsimulation.getEntity(i, j) instanceof Grass) {
                    assertEquals(0, this.Testsimulation.getEntity(i, j).getColor());
                }
                if (this.Testsimulation.getEntity(i, j) instanceof Leaf) {
                    assertEquals(3, this.Testsimulation.getEntity(i, j).getColor());
                }
                if (this.Testsimulation.getEntity(i, j) instanceof Fruit) {
                    assertEquals(5, this.Testsimulation.getEntity(i, j).getColor());
                }
            }
        }
    }
    @Test
    public void T11_SeveralRuns(){
        for(int i = 0; i < 99;i++) {
            this.Testsimulation.stepForward();
        }
    }
    @Test
    public void T12_SaveAndLoad() throws IOException {
        this.TestRecord = new SimulationRecord();
        assertTrue(this.TestRecord.simulationExists());
    }

    @Test
    public void T13_TestStepForward(){
        int prevTotalStep = this.Testsimulation.getTotalSteps();
        this.Testsimulation.stepForward();
        this.Testsimulation.stepForward();
        prevTotalStep = prevTotalStep + 1;
        assertEquals(prevTotalStep, this.Testsimulation.getTotalSteps());

    }
    @Test
    public void T14_TestStepBackWard(){
        this.Testsimulation.stepForward();
        this.Testsimulation.stepForward();
        int prevCurrentStep = this.Testsimulation.getCurrentStep();
        this.Testsimulation.stepBackward();
        prevCurrentStep = prevCurrentStep - 1;
        assertEquals(prevCurrentStep, this.Testsimulation.getCurrentStep());
    }
    @Test
    public void T15_GrassCanGrow() throws IOException {
       this.Testsimulation.stepForward();
       this.Testsimulation.setEntity(26, 10, new Grass(this.Testsimulation, null, 0, 26, 10));
       this.Testsimulation.getEntity(26,10).setNutrients(200);
       for(int t = 0; t < 12; t++) {
           this.Testsimulation.stepForward();
       }
        assertEquals('G', this.Testsimulation.getEntity(25, 10).getSymbol());

    }
    @After
    public void T16_Reset() {
        this.Testsimulation.reset(true);

            for (int j = 0; j < this.Testsimulation.getColumns(); j++) {
                assertEquals('.', this.Testsimulation.getEntity(25, j).getSymbol());
            }
    }
}















