//Jose Mora
//CIS 255
//Project 5
//Hangman.java

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
	
  public static boolean[] letterGuessed;
  public static char[] letters;
  public static final int MAX_NUMBER_OF_GUESSES = 10;

  public static void main(String[] args) {
	  
    try {
      int games = 0;
      int wins = 0;
      int losses= 0;
      
      String file= "wordList.txt";
      Scanner keyboard = new Scanner(new File(file));
      Scanner s = new Scanner(System.in);
      ArrayList<String> wordList = new ArrayList();
      
      while (keyboard.hasNext()){
        String letterGuessed = keyboard.next();
        wordList.add(letterGuessed);
      }		
      
      char continueOn;
      
      do {
    	Random r = new Random();  
        int numberOfWords = wordList.size();
        int wordNum = r.nextInt(numberOfWords);
        String hiddenWord = (String)wordList.get(wordNum);
        int lettersChosen = hiddenWord.length();
        games++;
        
		letters = new char[lettersChosen];
        letterGuessed = new boolean[lettersChosen];
        
        for (int x = 0; x < lettersChosen; x++){
         letters[x] = hiddenWord.charAt(0);	
         letterGuessed[x] = false;
        }
        
        boolean[] listOfLetters = new boolean[26];
        for (int x = 0; x < 26; x++) {
          listOfLetters[x] = false;
        }
        
        ArrayList<Character> guess = new ArrayList();
        boolean wordGuessed = false;
        int numberCorrect = 0;
        int guessesLeft = 0;
        
        do {
          System.out.println(completeWord());
          System.out.println("You have " + (MAX_NUMBER_OF_GUESSES - guessesLeft) + " chances left");
          
          if (guess.size() > 0) {
            System.out.println("Your Guess: " + guess + "\n");
          }
   
          System.out.print("Choose a letter.\n");
          
          try {
            String line = s.nextLine();
            
            if (line.length() > 1) {
              throw new LongInputException();
            }
            
            char letterPosition = line.charAt(0);  
            int guessIntValue = Character.getNumericValue(letterPosition) - MAX_NUMBER_OF_GUESSES;
            
            if ((guessIntValue >= 26) || (guessIntValue < 0)) {
              throw new LettersOnlyException();
            }
            
            if (listOfLetters[guessIntValue] == true) {
              System.out.println("Letter already guessed");
            } else {
              listOfLetters[guessIntValue] = true;
              boolean correctLetter = false;
              for (int x = 0; x < lettersChosen; x++) {
                if (letters[x] == letterPosition) {
                  letterGuessed[x] = true;
                  numberCorrect++;
                  correctLetter = true;
                }
              }
              
              if (correctLetter == false) {
                guessesLeft++;
              }
              
              if (numberCorrect == lettersChosen) {
                wordGuessed = true;
              }
            }
          }
          
          catch (LongInputException ex) {
            System.out.println(ex.getMessage() + "\n");
          }
          
          catch (LettersOnlyException ex) {
            System.out.println(ex.getMessage() + "\n");
          }
          
        } while ((wordGuessed == false) && (guessesLeft < MAX_NUMBER_OF_GUESSES));
        
        if (wordGuessed) {
          System.out.println(completeWord() + "\nThat's right!");
          wins++;
        } else {
          System.out.println("That's wrong. The correct word was " + hiddenWord);
         losses++;
        }
        
        System.out.println("\nYou have " + wins + " Wins and " + losses + " Losses ");
        double winPercent = (wins*100) / games;
        System.out.println("You won " +  (winPercent) + "% of all games\n");     
        System.out.println("Keep Playing?\nEnter Y to continue or any other key to quit.");
        
        continueOn = s.nextLine().toUpperCase().charAt(0);  
      } while (continueOn == 'Y');
    }
    
    catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage());
    }
  }
  
  public static String completeWord() {
	  String completeWord = "";
	    for (int x = 0; x < letters.length; x++) {
	      if (letterGuessed[x] == true) {
	        completeWord = completeWord + letters[x];
	      } else {
	        completeWord = completeWord + "_ ";
	      }
	    }
	    return completeWord;
  }
}
