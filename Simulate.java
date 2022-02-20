public class Simulate {
    public static void main(String[] args) {
        try {
            int m = 20, n = 20;
            int mode = 1;
            int obstacles = 10;
            Environment grid = new Environment(m,n,obstacles,mode);
            int numberOfHunters = 5;
            Creature org;

            for (int i = 0; i < numberOfHunters; i++) {
                org = new Hunter(grid);
                grid.putCreature(org);
            }

            int numberOfPreys = 100;

            for (int i = 0; i < numberOfPreys; i++) {
                org = new Prey(grid);
                grid.putCreature(org);
            }

            for (int i = 0; i < 10; i++) {
                org = new UltimateHunter(grid);
                grid.putCreature(org);
            }

            grid.printGrid();

            System.out.println("");

            int simulationSteps = 1000;

            for (int i = 0; i < simulationSteps; i++) {
                grid.step();
                if (i % 100 == 0)
                    grid.info();
                System.out.println("");
            }
        }
        catch (OutofThisWorldCreatureException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}
