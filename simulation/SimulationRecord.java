package simulation;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimulationRecord {

    private String directory ="sim01";
    private final String fileID = "sim";
    private final String fileExtension = ".txt";
    private final String totalStepsID = "total";

    public void writeStep(int step, MainSimulation sim) throws IOException {
        Path filepath = FileSystems.getDefault().getPath(directory);
        FileOutputStream fout;

        if (!Files.exists(filepath))
            Files.createDirectory(filepath);

        fout = new FileOutputStream(
        directory + "\\" + fileID + String.valueOf(step) + fileExtension);

        ObjectOutputStream objOut = new ObjectOutputStream(fout);

        for (int r = 0; r < sim.getRows(); r++)
            for (int c = 0; c < sim.getColumns(); c++)
                objOut.writeObject(sim.getEntity(r,c));

        objOut.writeObject(null); //end of file
        objOut.close();

        writeTotalSteps(sim);
    }

    public void readStep(int step, MainSimulation sim) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(
                directory + "\\" + fileID + String.valueOf(step) + fileExtension);
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

    /** Checks whether a simulation exists in the current directory. **/
    public boolean simulationExists() {
        try {
            readTotalSteps();
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public void writeTotalSteps(MainSimulation sim) throws IOException {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(
                    directory + "\\" + totalStepsID + fileExtension);
        } catch (FileNotFoundException e) {
            //this will never happen
        }
        ObjectOutputStream intWrite = new ObjectOutputStream(fout);
        intWrite.writeInt(sim.getTotalSteps());
        intWrite.close();
    }

    public int readTotalSteps() throws IOException {
        FileInputStream fin = new FileInputStream(
                directory + "\\" + totalStepsID + fileExtension);
        ObjectInputStream objIn = new ObjectInputStream(fin);
        int totalSteps = objIn.readInt();
        objIn.close();

        return totalSteps;
    }

    public void setDirectory(String newDir) {
        directory = newDir;
    }
}
