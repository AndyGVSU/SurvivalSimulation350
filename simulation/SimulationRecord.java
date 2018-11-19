package simulation;

import java.io.*;

public class SimulationRecord {

    private final String defaultDirectory = "sim01";
    private final String fileID = "sim";
    private final String fileExtension = ".txt";
    private final String totalStepsID = "total";
    private File directory;

    public SimulationRecord() throws IOException {
        setDirectory(defaultDirectory);
    }

    public void writeStep(int step, MainSimulation sim) throws IOException {
        File newStep = new File(directory,fileID + String.valueOf(step) + fileExtension);

        FileOutputStream fout = new FileOutputStream(newStep);
        ObjectOutputStream objOut = new ObjectOutputStream(fout);

        for (int r = 0; r < sim.getRows(); r++)
            for (int c = 0; c < sim.getColumns(); c++)
                objOut.writeObject(sim.getEntity(r,c));

        objOut.writeObject(null); //end of file
        objOut.close();

        writeTotalSteps(sim);
    }

    public void readStep(int step, MainSimulation sim) throws IOException, ClassNotFoundException {
        File newStep = new File(directory,fileID + String.valueOf(step) + fileExtension);

        FileInputStream fin = new FileInputStream(newStep);
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
        File newTotal = new File(directory,totalStepsID + fileExtension);

        FileOutputStream fout = new FileOutputStream(newTotal);
        ObjectOutputStream intWrite = new ObjectOutputStream(fout);
        intWrite.writeInt(sim.getTotalSteps());
        intWrite.close();
    }

    public int readTotalSteps() throws IOException {
        File newTotal = new File(directory,totalStepsID + fileExtension);

        FileInputStream fin = new FileInputStream(newTotal);
        ObjectInputStream objIn = new ObjectInputStream(fin);
        int totalSteps = objIn.readInt();
        objIn.close();

        return totalSteps;
    }

    public void setDirectory(String newDir) throws IOException {
        directory = new File(newDir);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new IOException();
    }
}
