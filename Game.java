import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Game {
  String[] wordList = new String[]
          {"abstract",
          "base",
          "codechallenge",
          "doublelinkedlist",
          "enum",
          "first",
          "general",
          "heritage",
          "index",
          "javascript"};
  Random random = new Random();
  String randomWord;
  Scanner scan = new Scanner(System.in);
  ArrayList<Character> blank;
  ArrayList<Character> wrongLetters;
  int correctGuessNum;
  int incorrectGuessNum;
  boolean playMore = true;


  public void gameStart() {
    // greet user welcoming to the game
    System.out.println("Hello there! Welcome to my Hang Man game!");

    // start game and setup when playMore is true
    while (playMore) {
      // game setup
      gameSetup();
      // indicate how many letters are in the word
      System.out.println("letters in word: " + randomWord.length());

      while (true) {
        // get guess from player
        char guess = getGuess();

        // check if guess was in the word and update the blank
        boolean isCorrect = updateBlank(blank, guess);
        if (isCorrect) {
          correctGuessNum++;
        } else {
          incorrectGuessNum++;
          wrongLetters.add(guess);
          System.out.println(guess + " is not in the word.");
        }

        // displays how many guesses have been made
        displayStatus();

        // if there is no blank anymore, which means all letters of the word are guessed correctly,
        if (!blank.contains('_')) {
          displayGameEnd();
          playMore = isPlayMore();
          break;
        }
      }
    }
  }

  private void gameSetup() {
    // get random word from the list
    randomWord = wordList[random.nextInt(wordList.length)];
    // generate blank space with underscores to indicate positions of characters in the word
    blank = new ArrayList<>();
    for (int i = 0; i < randomWord.length(); i++) {
      blank.add('_');
    }
    // set correct or incorrect guess numbers to 0
    correctGuessNum = 0;
    incorrectGuessNum = 0;
    wrongLetters = new ArrayList<>();
    playMore = true;
  }

  private char getGuess() {
    String inputLetter;
    while (true) {
      System.out.print("Please enter one letter: ");
      inputLetter = scan.next();
      if (inputLetter.length() != 1) {
        System.out.println("You entered more than one or no letter. Please try again.");
      } else {
        if (wrongLetters.contains(inputLetter.charAt(0)) || blank.contains(inputLetter.charAt(0))) {
          System.out.println("You already used that letter.");
        }
        else {
          break;
        }
      }
    }
    return inputLetter.charAt(0);
  }

  private void displayGameEnd() {
    System.out.println();
    System.out.println("**********************");
    System.out.println("***You won the game***");
    System.out.println("The letter was: " + randomWord);
    System.out.println("You have guessed " + (correctGuessNum + incorrectGuessNum) + " times total");
    System.out.println("**********************");
    System.out.println();
  }

  private void displayStatus() {
    // letters in blank means it is included in the word in that position
    // letters no in the blank means the letter is not included in the word
    System.out.println("");
    System.out.println("========Status=========");
    System.out.println(getString(blank));
    System.out.println(wrongLetters.toString());
    System.out.println("correct guesses :" + correctGuessNum + " times");
    System.out.println("Incorrect guesses :" + incorrectGuessNum + " times");
  }

  private boolean isPlayMore() {
    System.out.print("enter 'q' to quit or enter any letter to play again: ");
    String moreGameInput = scan.next();

    if (Objects.equals(moreGameInput, "q")) {
      System.out.println("Thank you for playing!");
      return false;
    }
    return true;
  }

  private boolean updateBlank(ArrayList<Character> blank, char guess) {
    boolean isCorrect = false;
    for (int i = 0; i < randomWord.length(); i++) {
      if (guess == randomWord.charAt(i)) {
        blank.set(i, guess);
        isCorrect = true;
      }
    }
    return isCorrect;
  }

  private String getString(ArrayList<Character> blank) {
    StringBuilder builder = new StringBuilder(blank.size());
    for(Character c: blank)
    {
      builder.append(c);
    }
    return builder.toString();
  }
}