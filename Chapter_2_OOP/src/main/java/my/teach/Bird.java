package my.teach;

public class Bird {
    private String name;

    public Bird(String name) {
        this.name = name;
    }

    public String fly() {
        return "Bird`s name is " + name +" and it`s flying";
    }

    public String getName() {
        return name;
    }
}
