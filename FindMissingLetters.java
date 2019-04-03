public class FindMissingLetters {
    public String getMissingLetters(String input) {

        boolean[] containsLetter = new boolean[26];
        String lowercaseInput = input.toLowerCase();
        int valueOfCharLowerA = (int) 'a';

        for (int i = 0; i < lowercaseInput.length(); i++) {
            int valueOfCharInInput = (int) lowercaseInput.charAt(i);
            int containsLetterIndex = valueOfCharInInput - valueOfCharLowerA;
            if (containsLetterIndex >= 0 && containsLetterIndex < 26) {
                containsLetter[containsLetterIndex] = true;
            }
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (!containsLetter[i]) {
                output.append((char) (valueOfCharLowerA + i));
            }
        }

        return output.toString();
    }
}