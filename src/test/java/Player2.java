import static java.util.stream.IntStream.range;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Player2 {

    public static final int MANAGER_COST = 10;
    public static final int DEV_COST = 5;
    public static final int SALE_COST = 5;

    private int id;
    private int cash;
    private int devs;
    private int sales;
    private int managers;
    private int features;
    private int bugs;
    private int turn = 0;
    private int market;

    private final Map<Integer, Integer> marketShares = new HashMap<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Player2 player = new Player2();

        while (true) {

            player.setId(in.nextInt());
            int playerCount = in.nextInt();
            player.setTurn(in.nextInt());

            player.setMarket(in.nextInt());
            player.setCash(in.nextInt());
            player.setDevs(in.nextInt());
            player.setSales( in.nextInt());
            player.setManagers(in.nextInt());
            player.setFeatures(in.nextInt());
            player.setBugs(in.nextInt());

            System.err.println(player);

            range(0, playerCount).forEach(k -> {
                int startUpId = in.nextInt();
                int marketShare = in.nextInt();
                int employeesCount = in.nextInt();
                player.getMarketShares().put(startUpId, marketShare);
                System.err.println("id: " + startUpId + "   market share:" + marketShare);
            });

            Instruction instruction = player.getInstruction();
            System.out.println(
                    String.format("%d %d %d %d %d", instruction.getDevsToHire(), instruction.getSalesToHire(),
                            instruction.getManagersToHire(), instruction.getDebugRate(),
                            instruction.getSalesAggressivenessRate()));
        }
    }

    public Instruction getInstruction() {
        Instruction instruction = new Instruction();

        double factor = 0.03 * (managers + sales + devs) + 1;
        double cost = factor * (managers * MANAGER_COST + devs * DEV_COST + sales * SALE_COST);
        int playerCount = marketShares.size();
        int market = marketShares.get(id);
        double revenue = market * (10 * Math.pow(1.05, turn * 1.0 / playerCount));

        int managerToHire;
        if (cost < 0.8 * revenue || managers < 3) {
            managerToHire = 1;
        } else {
            managerToHire = 0;
        }
        int available = Math.min(managers * 5 - devs - sales, managers * 2);
        int devsToHire, salesToHire;
        if (devs > sales) {
            salesToHire = Math.max(0, Math.min(50 - sales, available));
            devsToHire = Math.max(0, Math.min(50 - devs, available - salesToHire));
        } else {
            devsToHire = Math.max(0, Math.min(50 - devs, available));
            salesToHire = Math.max(0, Math.min(50 - sales, available - devsToHire));
        }

        instruction.setDevsToHire(devsToHire);
        instruction.setSalesToHire(salesToHire);
        instruction.setManagersToHire(managerToHire);
        if (bugs > 10) {
            instruction.setDebugRate(100);
        } else {
            instruction.setDebugRate(20);
        }
        if (marketShares.values().stream().mapToInt(Integer::valueOf).sum() > 90) {
            instruction.setSalesAggressivenessRate(80);
        } else {
            instruction.setSalesAggressivenessRate(0);
        }

        return instruction;
    }

    public static class Instruction {

        private int devsToHire;
        private int salesToHire;
        private int managersToHire;
        private int debugRate;
        private int salesAggressivenessRate;

        public int getDevsToHire() {
            return devsToHire;
        }

        public void setDevsToHire(int devsToHire) {
            this.devsToHire = devsToHire;
        }

        public int getSalesToHire() {
            return salesToHire;
        }

        public void setSalesToHire(int salesToHire) {
            this.salesToHire = salesToHire;
        }

        public int getManagersToHire() {
            return managersToHire;
        }

        public void setManagersToHire(int managersToHire) {
            this.managersToHire = managersToHire;
        }

        public int getDebugRate() {
            return debugRate;
        }

        public void setDebugRate(int debugRate) {
            this.debugRate = debugRate;
        }

        public int getSalesAggressivenessRate() {
            return salesAggressivenessRate;
        }

        public void setSalesAggressivenessRate(int salesAggressivenessRate) {
            this.salesAggressivenessRate = salesAggressivenessRate;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getDevs() {
        return devs;
    }

    public void setDevs(int devs) {
        this.devs = devs;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getManagers() {
        return managers;
    }

    public void setManagers(int managers) {
        this.managers = managers;
    }

    public int getFeatures() {
        return features;
    }

    public void setFeatures(int features) {
        this.features = features;
    }

    public int getBugs() {
        return bugs;
    }

    public void setBugs(int bugs) {
        this.bugs = bugs;
    }

    public Map<Integer, Integer> getMarketShares() {
        return marketShares;
    }

    public int getMarket() {
        return market;
    }

    public void setMarket(int market) {
        this.market = market;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "Player{" +
                "cash=" + cash +
                ", devs=" + devs +
                ", sales=" + sales +
                ", managers=" + managers +
                ", features=" + features +
                ", bugs=" + bugs +
                ", market=" + market +
                '}';
    }
}
