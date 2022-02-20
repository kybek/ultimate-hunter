import java.util.ArrayList;
import java.util.Scanner;

public class Environment {
    // 1-indexed and in (row, col) format.
    private Character[][] grid;
    private ArrayList<Creature> hunters;
    public ArrayList<Creature> preys;
    private int width;
    private int height;
    private int mode;
    private Scanner interactiveModeScanner;
    private boolean invalidCreatureInput;

    private static final char EMPTY = '.';
    private static final char OBSTACLE = 'O';
    private static final char PREY = 'P';
    private static final char HUNTER = 'H';

    public static final int[][] DIRS = {{0, 0}, {0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    public Environment(int w, int h, int obstacles, int mode) throws OutofThisWorldCreatureException {
        setWidth(w);
        setHeight(h);
        setMode(mode);
        initGrid(w, h);
        addObstacles(obstacles);

        invalidCreatureInput = false;

        hunters = new ArrayList<Creature>();
        preys = new ArrayList<Creature>();

        if (mode == 0) {
            interactiveModeScanner = new Scanner(System.in);
        }
    }

    public boolean isValidCoordinate(int x, int y) {
        return (x >= 1 && x <= width && y >= 1 && y <= height);
    }

    private void addObstacle(int x, int y) throws OutofThisWorldCreatureException{
        if (isValidCoordinate(x, y) == false) {
            throw new OutofThisWorldCreatureException();
        }
        
        if (getCell(x, y) != EMPTY) {
            throw new OutofThisWorldCreatureException();
        }

        setCell(x, y, OBSTACLE);
    }

    private ArrayList<int[]> getEmptyCells() throws OutofThisWorldCreatureException {
        ArrayList<int[]> emptyCells = new ArrayList<int[]>();

        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= width; j++) {
                if (getCell(j, i) == EMPTY) {
                    int[] temp = new int[2];
                    temp[0] = i;
                    temp[1] = j;

                    emptyCells.add(temp);
                }
            }
        }

        return emptyCells;
    }

    private void addObstacles(int count) throws OutofThisWorldCreatureException {
        ArrayList<int[]> emptyCells = getEmptyCells();
        
        if (emptyCells.size() < count) {
            throw new OutofThisWorldCreatureException();
        }

        for (int i = 1; i <= count; i++) {
            int randomIndex = (int) (Math.floor(Math.random() * emptyCells.size()));
            
            int[] emptyCell = emptyCells.get(randomIndex);
            emptyCells.remove(randomIndex);
            
            addObstacle(emptyCell[1], emptyCell[0]);
        }
    }

    private void initGrid(int w, int h) throws OutofThisWorldCreatureException {
        grid = new Character[h + 1][w + 1];

        for (int i = 1; i <= h; i++) {            
            for (int j = 1; j <= w; j++) {
                setCell(j, i, EMPTY);
            }
        }
    }

    private void setCell(int x, int y, char c) throws OutofThisWorldCreatureException {
        if (isValidCoordinate(x, y) == false) {
            throw new OutofThisWorldCreatureException();
        }

        grid[y][x] = c;
    }

    private char getCell(int x, int y) throws OutofThisWorldCreatureException{
        if (isValidCoordinate(x, y) == false) {
            throw new OutofThisWorldCreatureException();
        }

        return grid[y][x];
    }

    public Creature get(int x, int y) throws OutofThisWorldCreatureException{
        if (isValidCoordinate(x, y) == false) {
            throw new OutofThisWorldCreatureException();
        }

        if (getCell(x, y) != PREY && getCell(x, y) != HUNTER) {
            throw new OutofThisWorldCreatureException();
        }

        if (getCell(x, y) == PREY) {
            for (Creature prey : preys) {
                if (prey.getX() == x && prey.getY() == y) {
                    return prey;
                }
            }
        }
        else {
            for (Creature hunter : hunters) {
                if (hunter.getX() == x && hunter.getY() == y) {
                    return hunter;
                }
            }
        }

        return null;
    }

    public boolean isMovable(int x, int y) throws OutofThisWorldCreatureException {
        if (isValidCoordinate(x, y) == false) {
            throw new OutofThisWorldCreatureException();
        }

        return (getCell(x, y) == EMPTY);
    }

    public boolean isEatable(int x, int y) throws OutofThisWorldCreatureException{
        if (isValidCoordinate(x, y) == false) {
            throw new OutofThisWorldCreatureException();
        }

        return (getCell(x, y) == PREY);
    }

    public void putCreature(Creature creature) throws OutofThisWorldCreatureException {
        ArrayList<int[]> emptyCells = getEmptyCells();

        if (emptyCells.size() < 1) {
            throw new OutofThisWorldCreatureException();
        }

        int randomIndex = (int) (Math.floor(Math.random() * emptyCells.size()));
            
        int[] emptyCell = emptyCells.get(randomIndex);

        if (creature instanceof Hunter) {
            setCell(emptyCell[1], emptyCell[0], HUNTER);
            hunters.add(creature);
        }
        else if (creature instanceof Prey) {
            setCell(emptyCell[1], emptyCell[0], PREY);
            preys.add(creature);
        }
        else {
            invalidCreatureInput = true;
        }

        creature.setX(emptyCell[1]);
        creature.setY(emptyCell[0]);
    }

    private void killPrey(Creature prey) throws OutofThisWorldCreatureException {
        setCell(prey.getX(), prey.getY(), EMPTY);
        preys.remove(prey);
    }

    private void killHunter(Creature hunter) throws OutofThisWorldCreatureException {
        setCell(hunter.getX(), hunter.getY(), EMPTY);
        hunters.remove(hunter);
    }

    public void step() throws OutofThisWorldCreatureException {
        if (invalidCreatureInput) {
            throw new OutofThisWorldCreatureException();
        }

        ArrayList<Creature> creaturesThatDied = new ArrayList<Creature>();
        ArrayList<Creature> creaturesThatWasBorn = new ArrayList<Creature>();

        for (Creature creature : hunters) {
            if (creature.starve()) {
                creaturesThatDied.add(creature);

                setCell(creature.getX(), creature.getY(), EMPTY);
            }
            else {
                int dir = creature.move();

                if (dir < 0 || dir >= Environment.DIRS.length) {
                    throw new OutofThisWorldCreatureException();
                }

                if (dir != 0) {
                    int x2 = creature.getX() + Environment.DIRS[dir][0];
                    int y2 = creature.getY() + Environment.DIRS[dir][1];

                    if (isValidCoordinate(x2, y2)) {
                        if (isMovable(x2, y2)) {
                            setCell(creature.getX(), creature.getY(), EMPTY);

                            creature.setX(x2);
                            creature.setY(y2);

                            setCell(creature.getX(), creature.getY(), HUNTER);
                        }
                        else if (isEatable(x2, y2)) {
                            creature.setHungerLevel(0);

                            Creature huntedPrey = null;

                            for (Creature prey : preys) {
                                if (prey.getX() == x2 && prey.getY() == y2) {
                                    huntedPrey = prey;
                                }
                            }

                            killPrey(huntedPrey);

                            setCell(creature.getX(), creature.getY(), EMPTY);

                            creature.setX(x2);
                            creature.setY(y2);

                            setCell(creature.getX(), creature.getY(), HUNTER);
                        }
                        else {
                            throw new OutofThisWorldCreatureException();
                        }
                    }
                    else {
                        throw new OutofThisWorldCreatureException();
                    }
                }

                dir = creature.breed();
                
                if (dir != -1) {
                    creature.setBreedTimer(0);

                    int x2 = creature.getX() + Environment.DIRS[dir][0];
                    int y2 = creature.getY() + Environment.DIRS[dir][1];

                    if (isValidCoordinate(x2, y2) && isMovable(x2, y2)) {
                        Hunter newborn = new Hunter(this);
                        newborn.setX(x2);
                        newborn.setY(y2);

                        creaturesThatWasBorn.add(newborn);

                        setCell(x2, y2, HUNTER);
                    }
                    else {
                        throw new OutofThisWorldCreatureException();
                    }
                }
            }

            creature.age();
        }

        for (Creature bornHunter : creaturesThatWasBorn) {
            hunters.add(bornHunter);
        }

        creaturesThatWasBorn = new ArrayList<Creature>();

        for (Creature diedHunter : creaturesThatDied) {
            killHunter(diedHunter);
        }

        creaturesThatDied = null;

        for (Creature creature : preys) {
            int dir = creature.move();

            if (dir < 0 || dir >= Environment.DIRS.length) {
                throw new OutofThisWorldCreatureException();
            }

            if (dir != 0) {
                int x2 = creature.getX() + Environment.DIRS[dir][0];
                int y2 = creature.getY() + Environment.DIRS[dir][1];

                if (isValidCoordinate(x2, y2) && isMovable(x2, y2)) {
                    setCell(creature.getX(), creature.getY(), EMPTY);

                    creature.setX(x2);
                    creature.setY(y2);

                    setCell(creature.getX(), creature.getY(), PREY);
                }
                else {
                    throw new OutofThisWorldCreatureException();
                }
            }

            dir = creature.breed();
            
            if (dir != -1) {
                creature.setBreedTimer(0);
                int x2 = creature.getX() + Environment.DIRS[dir][0];
                int y2 = creature.getY() + Environment.DIRS[dir][1];

                if (isValidCoordinate(x2, y2) && isMovable(x2, y2)) {
                    Prey newborn = new Prey(this);
                    newborn.setX(x2);
                    newborn.setY(y2);

                    creaturesThatWasBorn.add(newborn);

                    setCell(x2, y2, PREY);
                }
                else {
                    throw new OutofThisWorldCreatureException();
                }
            }

            creature.age();
        }

        for (Creature bornPrey : creaturesThatWasBorn) {
            preys.add(bornPrey);
        }

        printGrid();

        if (getMode() == 0) {
            String s = interactiveModeScanner.next();

            if (s.equals("n") == false) {
                System.exit(0);
            }
        }
    }

    public void printGrid() throws OutofThisWorldCreatureException{
        char[] topLine = new char[2 + 3 * getWidth()];

        for (int i = 0; i < topLine.length; i++) {
            topLine[i] = ' ';
        }

        for (int j = getWidth(); j >= 1; j--) {
            String st = "" + j;

            int topLineIndex = 2 + 3 * j - 1;
            int stIndex = st.length() - 1;

            while (stIndex >= 0) {
                topLine[topLineIndex--] = st.charAt(stIndex--);
            }
        }

        System.out.println(topLine);

        for (int i = 1; i <= getHeight(); i++) {
            String line = "";
            
            line += i;

            while (line.length() < 4) {
                line += " ";
            }

            for (int j = 1; j <= getWidth(); j++) {
                line += getCell(j, i);

                if (j < getWidth()) {
                    line += "  ";
                }
            }

            System.out.println(line);
        }
    }

    public void info() {
        String st = "";
        st += "Prey : " + preys.size() + ", ";

        int cnt = 0;

        for (Creature creature : hunters) {
            if (creature instanceof UltimateHunter) {
                cnt++;
            }
        }

        st += "Hunter : " + (hunters.size() - cnt) + ", ";
        st += "UltimateHunter : " + cnt;

        System.out.println(st);
    }

    public void setWidth(int _width) {
        width = _width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int _height) {
        height = _height;
    }

    public int getHeight() {
        return height;
    }

    public void setMode(int _mode) {
        mode = _mode;
    }

    public int getMode() {
        return mode;
    }
}
