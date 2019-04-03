import java.util.Scanner;

public class MissingLetters {
    static FindMissingLetters findMissingLetters = new FindMissingLetters();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter a sentence: ");
            String sentence = scanner.nextLine();
            String missingLetters = findMissingLetters.getMissingLetters(sentence);
            System.out.println("Missing letters from sentence: " + missingLetters);

            System.out.print("Do you want to quit? ");
            String exitConfirmation = scanner.nextLine().toLowerCase();
            if (exitConfirmation.contains("y")) {
                System.exit(0);
            }
        }
    }
}