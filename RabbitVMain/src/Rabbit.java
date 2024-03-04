import java.util.Random;

public class Rabbit {
    /*

        - Only females can have a litter of rabbits.
        - The gestation period is the period of how long a rabbit is pregnant.
        - This period last from 28 to 32 days. This changes per pregnancy.
        - A rabbit can breed again a week after giving birth.
        - Rabbits will breed for the first time after 100 days from birth.
        - Assume there will be a gestation period for the first birth.
        - Producing a doe or a buck is 50/50
        - A litter can contain 3-8 rabbits. This changes per pregnancy.

     */
    private char sex; // 'M' or 'F'
    private int age; // in days
    private int gestationalPeriod; // in days
    private int daysAfterBirth = 7;
    private boolean isBreeding;

    public Rabbit(char sex) {
        this.sex = sex;
    }
    public void breed() {
        isBreeding = true;
        Random rand = new Random();
        // rng between 28 and 32
        gestationalPeriod = rand.nextInt(32 - 28 + 1) + 28; //
    }

    public void birth() {
        isBreeding = false;
        daysAfterBirth = 0;
        int litterAmount = new Random().nextInt(8 - 3 + 1) + 3;
        for (int i = 0; i < litterAmount; i++) {
            int femaleOrMale = new Random().nextInt(2);
            if (femaleOrMale == 0) {
                RabbitSimulator.femaleRabbits += 1;
                RabbitSimulator.newRabbits.add(new Rabbit('F'));
                System.out.println("FEMALE RABBIT BORN");
            } else {
                RabbitSimulator.maleRabbits += 1;
                RabbitSimulator.newRabbits.add(new Rabbit('M'));
                System.out.println("MALE RABBIT BORN");
            }
        }
        System.out.println("LITTER BORN");
    }

    public void cycleDay() {
        age++;
        daysAfterBirth++;
        gestationalPeriod--;
    }

    public boolean canGetPregnant() {
        return gestationalPeriod <= 0 && age >= 100 && daysAfterBirth >= 7;
    }

    public boolean canGiveBirth() {
        return gestationalPeriod <= 0 && daysAfterBirth >= 7;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGestationalPeriod() {
        return gestationalPeriod;
    }

    public void setGestationalPeriod(int gestationalPeriod) {
        this.gestationalPeriod = gestationalPeriod;
    }

    public int getDaysAfterBirth() {
        return daysAfterBirth;
    }

    public void setDaysAfterBirth(int daysAfterBirth) {
        this.daysAfterBirth = daysAfterBirth;
    }

    public boolean getIsBreeding() {
        return isBreeding;
    }

    public void setIsBreeding(boolean isBreeding) {
        this.isBreeding = isBreeding;

    }
}

