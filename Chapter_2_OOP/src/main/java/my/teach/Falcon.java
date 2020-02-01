package my.teach;

import java.util.Objects;

public class Falcon extends Bird {
    private int speedFly;

    public Falcon(String name, int speedFly) {
        super(name);
        this.speedFly = speedFly;
    }

    public String hunt() {
        return fly() + " and hunting on mice";
    }

    public int getSpeedFly() {
        return speedFly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Falcon falcon = (Falcon) o;
        return speedFly == falcon.speedFly;
    }

    @Override
    public int hashCode() {
        return Objects.hash(speedFly);
    }

    @Override
    public String toString() {
        return "I`m " + getName() + " and my speed fly is " + speedFly;
    }
}
