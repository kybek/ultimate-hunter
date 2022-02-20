import java.util.ArrayList;

public class UltimateHunter extends Hunter {
    public UltimateHunter(Environment e) {
        super(e);
        setName("Ultimate Hunter");
    }

    public double howGoodIsMove(int x, int y) {
        if (getEnv().preys.size() == 0) {
            return 0;
        }

        double sumX = 0;
        double sumY = 0;

        for (Creature prey : getEnv().preys) {
            sumX += prey.getX();
            sumY += prey.getY();
        }

        sumX /= getEnv().preys.size();
        sumY /= getEnv().preys.size();

        
        return (Math.sqrt((sumX - x) * (sumX - x) + (sumY - y) * (sumY - y)));
    }

    public int move() throws OutofThisWorldCreatureException {
        ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
        ArrayList<Integer> possiblePreys = new ArrayList<Integer>();

        possibleMoves.add(0);
        
        for (int dir = 0; dir < Environment.DIRS.length; dir++) {
            int x2 = getX() + Environment.DIRS[dir][0];
            int y2 = getY() + Environment.DIRS[dir][1];

            if (getEnv().isValidCoordinate(x2, y2) && getEnv().isMovable(x2, y2)) {
                possibleMoves.add(dir);
            }
            else if (getEnv().isValidCoordinate(x2, y2) && getEnv().isEatable(x2, y2)) {
                possiblePreys.add(dir);
            }
        }

        if (possiblePreys.size() > 0) {
            int randomIndex = (int) (Math.floor(Math.random() * possiblePreys.size()));

            return possiblePreys.get(randomIndex);
        }

        int bestIndex = 0;
        double bestValue = Double.MAX_VALUE;

        for (int i = 0; i < possibleMoves.size(); i++) {
            int dir = possibleMoves.get(i);
            int x2 = getX() + Environment.DIRS[dir][0];
            int y2 = getY() + Environment.DIRS[dir][1];
            double value = howGoodIsMove(x2, y2);

            if (value < bestValue) {
                bestValue = value;
                bestIndex = i;
            }
        }

        return possibleMoves.get(bestIndex);
    }
}
