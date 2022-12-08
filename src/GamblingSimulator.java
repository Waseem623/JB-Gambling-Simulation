import java.util.Scanner;

public class GamblingSimulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to gambling simulator...");
        Gamble gamble = new Gamble();
        int choice = 1;
        while (choice == 1) {
            boolean isWon = gamble.playMaxDays();
            if (isWon) {
                System.out.println("Do you want to play again ? \n Enter 1 to play \n Enter 0 to stop");
                choice = sc.nextInt();
                gamble.resetGame();
            }
            else return;
        }
    }
}