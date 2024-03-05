/*******************************************************************************************************

    Mason Hemmrich
    Purpose: The `RabbitSimulator` class represents a simulation of the lifecycle of rabbits for one year.

*******************************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class RabbitSimulator {
    // Stores the active rabbits
    public static ArrayList<Rabbit> rabbits = new ArrayList<>();

    // Stores the initial rabbits
    public static ArrayList<Rabbit> tempRabbitList = new ArrayList<>();

    // Stores the rabbits that were recently born
    public static ArrayList<Rabbit> newRabbits = new ArrayList<>();

    // Keeps track of the male and female rabbits
    public static int maleRabbits = 0;
    public static int femaleRabbits = 0;
    public static final int year = 365;

    public static void main(String[] args) {
        // Adds the initial rabbits to the list
        readRabbitData();
    }

    /**
     * Simulates the lifecycle of the rabbits.
     * Runs 10 trials and prints out the averages and standard deviations for the population, does, and bucks.
     */
    public static void rabbitLifecycle() {
        int[] trialResults = new int[3];
        int[][] allTrialResults = new int[10][3];

        double totalRabbitCount = 0;
        double totalBuckCount = 0;
        double totalDoeCount = 0;

        double[] totalRabbitDeviation = new double[10];
        double[] totalBuckDeviation = new double[10];
        double[] totalDoeDeviation = new double[10];

        // Runs 10 trials
        for (int i = 0; i < 10; i++) {
            // Simulates the rabbits life for a year
            for (int j = 0; j < year; j++) {
                // Iterates through all the current rabbits
                for (Rabbit rabbit : rabbits) {
                    if (rabbit.canGetPregnant()) { // Checks all the factors that allow a rabbit to breed
                        rabbit.breed();
                    } else if (rabbit.canGiveBirth()) { // Checks all the factors that allow a rabbit to give birth
                        rabbit.birth();
                    } else {
                        rabbit.cycleDay(); // Simulates one day of the rabbits life
                    }
                }
                // Adds the newly born rabbits to the list
                rabbits.addAll(newRabbits);

                // Removes the new rabbits from the temporary list
                newRabbits.clear();
            }

            // Prints the statistics for the current trial
            System.out.println("Trial " + i + ": " + (maleRabbits + femaleRabbits) + " was the final population of rabbits; " + femaleRabbits + " does, " + maleRabbits + " bucks.");

            // Removes all the rabbits after the 10 trials are done for that seed.
            rabbits.clear();

            // Stores the total amount of rabbits, does, and bucks for the 10 trials
            totalRabbitCount += (maleRabbits + femaleRabbits);
            totalBuckCount += maleRabbits;
            totalDoeCount += femaleRabbits;

            // Stores the total amount of rabbits, does, and bucks for the 10 trials in an array to calculate the standard deviation
            totalRabbitDeviation[i] = maleRabbits + femaleRabbits;
            totalDoeDeviation[i] = femaleRabbits;
            totalBuckDeviation[i] = maleRabbits;

/*

            // Stores the results of the trial
            trialResults[0] = maleRabbits + femaleRabbits;
            trialResults[1] = femaleRabbits;
            trialResults[2] = maleRabbits;

            allTrialResults[i][0] = maleRabbits + femaleRabbits;
            allTrialResults[i][1] = femaleRabbits;
            allTrialResults[i][2] = maleRabbits;

            System.out.println(Arrays.toString(trialResults));
*/

            // Resets the amount of does and bucks for the next seed
            maleRabbits = 0;
            femaleRabbits = 0;

            // Resets the characteristics of the rabbits for the next trial
            for (Rabbit rabbit : tempRabbitList) {
                rabbit.resetRabbit();
            }

            // Adds the initial rabbits to the main rabbit list for the next trial
            rabbits.addAll(tempRabbitList);
        }
        // System.out.println(Arrays.deepToString(allTrialResults));

        // Prints the statistics for the 10 trials
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println("Average number of rabbits: " + df.format(totalRabbitCount / 10) + " with standard deviation of " + df.format(calculateSD(totalRabbitDeviation)) + ".");
        System.out.println("Average number of female rabbits: " + df.format(totalDoeCount / 10) + " with standard deviation of " + df.format(calculateSD(totalDoeDeviation)) + ".");
        System.out.println("Average number of male rabbits: " + df.format(totalBuckCount / 10) + " with standard deviation of " + df.format(calculateSD(totalBuckDeviation)) + ".");

        System.out.println();

        // Clears the lists for the next seed
        rabbits.clear();
        tempRabbitList.clear();
    }

    /**
     * Calculates the standard deviation for the 10 trials.
     *
     * @param deviation Array containing the totals for the 10 trials.
     * @return The standard deviation for the 10 trials.
     */
    public static double calculateSD(double[] deviation) {
        double sum = 0.0;
        double standardDeviation = 0.0;

        int length = deviation.length;

        for (double num : deviation) {
            sum += num;
        }

        double average = sum / length;

        for (double num : deviation) {
            standardDeviation += Math.pow(num - average, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    /**
     * Reads the rabbit data from the input file.
     */
    public static void readRabbitData() {

        // Grabs the file from relative path
        URL url = RabbitSimulator.class.getResource("input.txt");
        File file = new File(url.getPath());

        // Attempts to read the file
        try (Scanner scanner = new Scanner(file)) {
            // Iterates through the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Splits values based on tabbing
                String[] parts = line.split("\t");

                // Grabs females and males based on the first and second index
                int females = Integer.parseInt(parts[0]);
                int males = Integer.parseInt(parts[1]);
                System.out.println("Female rabbits: " + females);
                System.out.println("Male Rabbits: " + males);

                // Adds all the females to the rabbit list
                for (int i = 0; i < females; i++) {
                    tempRabbitList.add(new Rabbit('F'));
                }

                // Adds all the male rabbits to the rabbit list
                for (int i = 0; i < males; i++) {
                    tempRabbitList.add(new Rabbit('M'));
                }

                // Adds the seed rabbits to the main rabbit list
                rabbits.addAll(tempRabbitList);

                // Begins the rabbit lifecycle
                rabbitLifecycle();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}