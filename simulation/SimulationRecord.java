package simulation;

import java.io.*;

public class SimulationRecord {

    private String directory ="simulation";

    public void writeStep(int step, Entity[][] grid) throws IOException {
        FileOutputStream fout = new FileOutputStream(directory + "\\" + String.valueOf(step)+".txt");
        ObjectOutputStream objOut = new ObjectOutputStream(fout);

        for (Entity[] eArr : grid)
            for (Entity ent : eArr)
                objOut.writeObject(ent);

        objOut.writeObject(null); //end of file
        objOut.close();
    }

    public void readStep(int step, MainSimulation sim) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(directory + "\\" + String.valueOf(step)+".txt");
        ObjectInputStream objIn = new ObjectInputStream(fin);

        Entity toReplace = (Entity) objIn.readObject();
        while(toReplace != null) {
            int r = toReplace.getRow();
            int c = toReplace.getColumn();
            toReplace.setSimulation(sim); //replace simulation field (not copied in serialization)
            sim.replaceEntity(r,c,toReplace); //write over entity in simulation
            toReplace = (Entity) objIn.readObject();
        }
        objIn.close();
    }

    public void setDirectory(String newDir) {
        directory = newDir;
    }
}
