public class Creature {
    private String name;
    private int x;
    private int y;
    private int breedTimer;
    private int hungerLevel;
    private Environment env;

    public static int HUNGER_LEVEL_LIMIT;
    public static int BREED_TIMER_PERIOD;

    public Creature(Environment e) {
        env = e;
        breedTimer = 1;
        hungerLevel = 1;
    }
    
    public int move() throws OutofThisWorldCreatureException {
        // TO BE OVERRIDED
        return 0;
    }

    public int breed() throws OutofThisWorldCreatureException {
        // TO BE OVERRIDED
        return 0;
    }

    public boolean starve() {
        return (HUNGER_LEVEL_LIMIT != 0 && getHungerLevel() >= HUNGER_LEVEL_LIMIT);
    }

    public void age() {
        setBreedTimer(getBreedTimer() + 1);
        setHungerLevel(getHungerLevel() + 1);
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setX(int _x) {
        x = _x;
    }

    public void setY(int _y) {
        y = _y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setBreedTimer(int _breedTimer) {
        breedTimer = _breedTimer;
    }

    public int getBreedTimer() {
        return breedTimer;
    }

    public void setHungerLevel(int _hungerLevel) {
        hungerLevel = _hungerLevel;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setEnv(Environment _env) {
        env = _env;
    }
    
    public Environment getEnv() {
        return env;
    }
}
