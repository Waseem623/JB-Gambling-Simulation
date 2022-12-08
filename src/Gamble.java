import java.util.*;

public class Gamble {
    public static final int WIN_BET = 1;
    public static final int MAX_DAYS = 20;
    public static final String DAYS_WON_KEY = "Won";
    public static final String DAYS_LOST_KEY = "Lost";
    public final int maxStake;
    public final int minStake;
    int stake;
    int bet;
    int monthlyWonDays = 0;
    int monthlyLostDays = 0;
    int totalWonLostAmount = 0;
    int luckiestDay;
    int unLuckiestDay;
    HashMap<String, ArrayList<Integer>> daysWonLost;
    HashMap<Integer, Integer> dayAndAmountWon;
    boolean isWon;

    //Intialized the simulator with intial values
    public Gamble() {
        stake = 100;
        bet = 1;
        minStake = stake * 50 / 100;
        maxStake = stake + minStake;
        daysWonLost = new HashMap<>();
        daysWonLost.put(DAYS_WON_KEY, new ArrayList<>());
        daysWonLost.put(DAYS_LOST_KEY, new ArrayList<>());
        dayAndAmountWon = new HashMap<>();
    }

    //Function to make a single bet and update stake
    public void makeABet() {
        int betResult = (int) Math.floor(Math.random() * 2);
        if (betResult == WIN_BET)
            stake += bet;
        else
            stake -= bet;
    }

    //Function to gamble until maximum win or maximum loss limit
    public int playTillMaxStake() {
        while (stake < maxStake && stake > minStake) {
            makeABet();
        }
        return stake;
    }

    //Function to gamble daily for a month
    public boolean playMaxDays() {
        int day = 0;
        int currentDayStake;
        while (day < MAX_DAYS) {
            currentDayStake = playTillMaxStake();
            day++;
            stake = 100;
            dailyWinLoss(day, currentDayStake);
        }
        printMonthResult();
        return isWon;
    }

    //Function to print report for a month
    private void printMonthResult() {
        System.out.println("Total won days in month = " + monthlyWonDays + "\nWon days are as follows : ");
        System.out.println(daysWonLost.get(DAYS_WON_KEY));
        System.out.println("Total lost days in month = " + monthlyLostDays + "\nLost days are as follows :");
        System.out.println(daysWonLost.get(DAYS_LOST_KEY));

        if (totalWonLostAmount > 0) {
            isWon = true;
            System.out.println("You won by amount = " + totalWonLostAmount);

            findLuckiestUnluckiesDay();
            // System.out.println(entry.getKey() + " -> " + entry.getValue());
            System.out.println("Luckiest day = " + luckiestDay);
            System.out.println("Unluckiest day = " + unLuckiestDay);
        } else {
            System.out.println("Ooh.... you lost all amount");
            isWon = false;
        }
    }

    //Function to find luckiest and unluckiest day
    private void findLuckiestUnluckiesDay() {
        luckiestDay = 1;
        unLuckiestDay = 1;
        int maxWon = dayAndAmountWon.get(1);
        int maxLost = maxWon;

        for (Map.Entry<Integer, Integer> entry : dayAndAmountWon.entrySet()) {
            if (entry.getValue() > maxWon) {
                maxWon = entry.getValue();
                luckiestDay = entry.getKey();
            }
            if (entry.getValue() < maxLost) {
                maxLost = entry.getValue();
                unLuckiestDay = entry.getKey();
            }
        }
    }

    //Function to track daily win loss status and stake
    private void dailyWinLoss(int day, int currentDayStake) {
        int wonOrLostStake = Math.abs(currentDayStake - stake);
        if (currentDayStake == maxStake) {
            daysWonLost.get(DAYS_WON_KEY).add(day);
            monthlyWonDays++;
            totalWonLostAmount += wonOrLostStake;
            dayAndAmountWon.put(day, totalWonLostAmount);
        } else {
            daysWonLost.get(DAYS_LOST_KEY).add(day);
            monthlyLostDays++;
            totalWonLostAmount -= wonOrLostStake;
            dayAndAmountWon.put(day, totalWonLostAmount);
        }
    }

    //Function to reset gamble for new gamble
    public void resetGame() {
        totalWonLostAmount = 0;
        monthlyLostDays = 0;
        monthlyWonDays = 0;
        isWon = false;
        dayAndAmountWon.values().clear();
        daysWonLost.get(DAYS_WON_KEY).clear();
        daysWonLost.get(DAYS_LOST_KEY).clear();
    }
}