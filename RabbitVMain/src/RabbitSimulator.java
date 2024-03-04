import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RabbitSimulator {

    // TODO Try and avoid using static variables
    public static ArrayList<Rabbit> rabbits = new ArrayList<>();
    public static ArrayList<Rabbit> newRabbits = new ArrayList<>();
    public static int maleRabbits = 0;
    public static int femaleRabbits = 0;

    public static void main(String[] args) {

        // Adds the initial rabbits to the list
        // TODO: Have each line of the list simulate a different year.

        readRabbitData();

    }

    public static void rabbitLifecycle() {
        // Simulates the rabbits life for a year
        while (rabbits.get(0).getAge() < 365) {
            // Iterates through all the current rabbits
            for (Rabbit rabbit : rabbits) {
                if (rabbit.canGetPregnant()) { // Checks all the factors that allow a rabbit to breed
                    System.out.println(rabbit);
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
            System.out.println(rabbits.size());
        }
        System.out.println("Female Rabbits: " + femaleRabbits);
        System.out.println("Male Rabbits: " + maleRabbits);
    }
    public static void readRabbitData() {
        ArrayList<Rabbit> rabbitData = new ArrayList<>();

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
                    rabbitData.add(new Rabbit('F'));
                }

                // Adds all the male rabbits to the rabbit list
                for (int i = 0; i < males; i++) {
                    rabbitData.add(new Rabbit('M'));
                }
                rabbits.addAll(rabbitData);
                rabbitLifecycle();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}