package my.teach;

public class Ostrich extends Bird {
    private int runningSpeed;
    private int weight;

    public Ostrich(String name, int runningSpeed, int weight) {
        super(name);
        this.runningSpeed = runningSpeed;
        this.weight = weight;
    }

    public int getInfoHowMuchToEatPerDay() {
        return amountOfFoodPerKilogram();
    }

    private int amountOfFoodPerKilogram() {
        return weight / 10;
    }

    @Override
    public String toString() {
        return "Ostrich is a very big bird, and it run " + runningSpeed + " km, and it weight = " + weight;
    }
}
