import java.util.ArrayList;

public class Prey extends Creature {
    public Prey(Environment e) {
        super(e);
        HUNGER_LEVEL_LIMIT = 0;
        BREED_TIMER_PERIOD = 3;
        setName("Prey");
    }

    public int move() throws OutofThisWorldCreatureException {
        ArrayList<Integer> possibleMoves = new ArrayList<Integer>();

        possibleMoves.add(0);
        
        for (int dir = 0; dir < Environment.DIRS.length; dir++) {
            int x2 = getX() + Environment.DIRS[dir][0];
            int y2 = getY() + Environment.DIRS[dir][1];

            if (getEnv().isValidCoordinate(x2, y2) && getEnv().isMovable(x2, y2)) {
                possibleMoves.add(dir);
            }
        }

        int randomIndex = (int) (Math.floor(Math.random() * possibleMoves.size()));

        return possibleMoves.get(randomIndex);
    }

    public int breed() throws OutofThisWorldCreatureException {
        if (getBreedTimer() < BREED_TIMER_PERIOD) {
            return -1;
        }

        ArrayList<Integer> possiblePlaces = new ArrayList<Integer>();
        
        for (int dir = 0; dir < Environment.DIRS.length; dir++) {
            int x2 = getX() + Environment.DIRS[dir][0];
            int y2 = getY() + Environment.DIRS[dir][1];

            if (getEnv().isValidCoordinate(x2, y2) && getEnv().isMovable(x2, y2)) {
                possiblePlaces.add(dir);
            }
        }

        if (possiblePlaces.size() == 0) {
            return -1;
        }

        int randomIndex = (int) (Math.floor(Math.random() * possiblePlaces.size()));

        return possiblePlaces.get(randomIndex);
    }
}
