import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class RabbitSimulator {

    // TODO Try and avoid using static variables

    // Stores the active rabbits
    public static ArrayList<Rabbit> rabbits = new ArrayList<>();

    // Stores the initial rabbits
    public static ArrayList<Rabbit> tempRabbitList = new ArrayList<>();

    // Stores the rabbits that were recently born
    public static ArrayList<Rabbit> newRabbits = new ArrayList<>();
    public static int maleRabbits = 0;
    public static int femaleRabbits = 0;

    public static void main(String[] args) {

        // Adds the initial rabbits to the list
        // TODO: Have each line of the list simulate a different year.

        readRabbitData();

    }

    public static void rabbitLifecycle() {
        double totalRabbitCount = 0;
        double totalBuckCount = 0;
        double totalDoeCount = 0;
        double[] totalRabbitDeviation = new double[10];
        double[] totalBuckDeviation = new double[10];
        double[] totalDoeDeviation = new double[10];
        for (int i = 0; i < 10; i++) {

            // Simulates the rabbits life for a year
            while (rabbits.get(0).getAge() < 365) { // TODO There as an issue with the first rabbits age not being reset.
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
                // Adds the new rabbits to the list
                rabbits.addAll(newRabbits);

                // Removes the new rabbits from the temporary list
                newRabbits.clear();
//            System.out.println(rabbits.size());
            }

            System.out.println("Trial " + i + ": " + (maleRabbits + femaleRabbits) + " was the final population of rabbits; " + femaleRabbits + " does, " + maleRabbits + " bucks.");

            rabbits.clear();
            totalRabbitCount += (maleRabbits + femaleRabbits);
            totalBuckCount += maleRabbits;
            totalDoeCount += femaleRabbits;
            totalRabbitDeviation[i] = maleRabbits + femaleRabbits;
            totalDoeDeviation[i] = femaleRabbits;
            totalBuckDeviation[i] = maleRabbits;
            maleRabbits = 0;
            femaleRabbits = 0;
            for (Rabbit rabbit : tempRabbitList) {
                rabbit.resetRabbit();
            }
            rabbits.addAll(tempRabbitList);
        }

        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println("Average number of rabbits: " + df.format(totalRabbitCount / 10) + " with standard deviation of " + df.format(calculateSD(totalRabbitDeviation)) + ".");
        System.out.println("Average number of female rabbits: " + df.format(totalDoeCount / 10) + " with standard deviation of " + df.format(calculateSD(totalDoeDeviation)) + ".");
        System.out.println("Average number of male rabbits: " + df.format(totalBuckCount / 10) + " with standard deviation of " + df.format(calculateSD(totalBuckDeviation)) + ".");

        System.out.println();
        rabbits.clear();
        tempRabbitList.clear();
    }

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
                rabbits.addAll(tempRabbitList);
                rabbitLifecycle();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}