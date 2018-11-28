package simulation;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.IOException;

/******************************************************************************
 * Simulation Record class.
 * Handles reading and writing the MainSimulation class.
 * Reads and writes the entity grid, a two-dimensional grid
 * of serializable Entity objects from the MainSimulation class.
 * Additionally, writes the total number of steps for a
 * simulation.
 * @author Andy Hudson
 *****************************************************************************/
public class SimulationRecord {
    /** The default directory to save simulation files to. */
    private final String defaultDirectory = "sim01";
    /** The file name of a simulation file, followed by the step number. */
    private final String fileID = "sim";
    /** Simulation file extension (text). */
    private final String fileExtension = ".txt";
    /** The file name of a simulation's total steps file. */
    private final String totalStepsID = "total";
    /** The current working directory. */
    private File directory;

    /** @throws IOException If the default directory cannot be set. */
    public SimulationRecord() throws IOException {
        setDirectory(defaultDirectory);
    }

    /** Writes a given step from the provided simulation to a file.
     * This method is called automatically when a new step is entered,
     * so saving is done automatically -- the user does not control it.
     * @param step The simulation step to write to file.
     * @param sim The MainSimulation to take data from.
     * @throws IOException When there is an error in writing objects.
     * */
    public void writeStep(final int step,
                          final MainSimulation sim) throws IOException {
        File newStep = new File(directory,
                fileID + String.valueOf(step) + fileExtension);

        FileOutputStream fout = new FileOutputStream(newStep);
        ObjectOutputStream objOut = new ObjectOutputStream(fout);

        for (int r = 0; r < sim.getRows(); r++) {
            for (int c = 0; c < sim.getColumns(); c++) {
                objOut.writeObject(sim.getEntity(r, c));
            }
        }

        objOut.writeObject(null); //end of file
        objOut.close();

        writeTotalSteps(sim.getTotalSteps());
    }

    /** Reads a given step from the simulation.
     * Overwrites the entities in the simulation object.
     * @param step The step to read from file.
     * @param sim The MainSimulation object to load data into.
     * @throws IOException When there is an error reading objects.
     * @throws ClassNotFoundException When serialized data is outdated.
     * */
    public void readStep(final int step, final MainSimulation sim)
            throws IOException, ClassNotFoundException {
        File newStep = new File(directory,
                fileID + String.valueOf(step) + fileExtension);

        FileInputStream fin = new FileInputStream(newStep);
        ObjectInputStream objIn = new ObjectInputStream(fin);

        Entity toReplace = (Entity) objIn.readObject();
        while (toReplace != null) {
            int r = toReplace.getRow();
            int c = toReplace.getColumn();
            //replace simulation field (not copied in serialization)
            toReplace.setSimulation(sim);
            //write over entity in simulation
            sim.replaceEntity(r, c, toReplace);
            toReplace = (Entity) objIn.readObject();
        }
        objIn.close();
    }

    /** Checks whether a simulation exists in the current directory.
     * @return False if there is an issue reading the total step file,
     * true otherwise.
     **/
    public boolean simulationExists() {
        try {
            readTotalSteps();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /** Writes the total number of steps in the current simulation to
     * a file.
     * @param totalSteps The given number of total steps.
     * @throws IOException When there's an issue writing the file.
     * */
    public void writeTotalSteps(final int totalSteps) throws IOException {
        File newTotal = new File(directory, totalStepsID + fileExtension);

        FileOutputStream fout = new FileOutputStream(newTotal);
        ObjectOutputStream intWrite = new ObjectOutputStream(fout);
        intWrite.writeInt(totalSteps);
        intWrite.close();
    }

    /** Reads from a file the total number of steps that was in
     * a given simulation.
     * @return The total number of steps read from the file.
     * @throws IOException When there's an issue reading the file.
     * */
    public int readTotalSteps() throws IOException {
        File newTotal = new File(directory, totalStepsID + fileExtension);

        FileInputStream fin = new FileInputStream(newTotal);
        ObjectInputStream objIn = new ObjectInputStream(fin);
        int totalSteps = objIn.readInt();
        objIn.close();

        return totalSteps;
    }

    /** Sets the current working directory to a new directory.
     * @param newDir The new directory (as a string).
     * @throws IOException When the directory cannot be made.
     * */
    public void setDirectory(final String newDir) throws IOException {
        directory = new File(newDir);
        if (!directory.exists()) {
            if (!makeDirectory(directory)) {
                throw new IOException();
            }
        }
    }

    /** Creates a new directory.
     * @param newDir The new directory "file."
     * @return Whether the directory was successfully created.
     * */
    public boolean makeDirectory(final File newDir) {
        return newDir.mkdir();
    }

    /** Deletes all simulation files, writes total steps to zero.
     * @return Whether all files were successfully deleted or not.
     * */
    public boolean reset() {
        int totalSteps = 0;
        try {
            totalSteps = readTotalSteps();
            writeTotalSteps(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File toDelete;
        boolean success = true;
        for (int f = 0; f <= totalSteps; f++) {
            toDelete = new File(directory, fileID + f + fileExtension);
            if (toDelete.exists()) {
                success = toDelete.delete();
            }
        }
        toDelete = new File(directory, totalStepsID + fileExtension);
        if (toDelete.exists()) {
            success = toDelete.delete();
        }

        makeDirectory(directory);
        return success;
    }
}
