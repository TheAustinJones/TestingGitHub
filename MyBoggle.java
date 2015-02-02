
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Owner
 */
public class MyBoggle {     //read though comments before you do the info sheet
     
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("CS/COE 1501 Spring 2015");
        System.out.println("Assignment 1\n");
        //System.out.print("Word lists for data file: ");
        //Scanner scanner = new Scanner(System.in);
        String fileName = "C://Users/Owner/Documents/NetBeansProjects/Austin's Stuff/src/";// put the path base here
        //fileName = fileName + scanner.nextLine();           /////////// ^^^^just set's up getting the info to start the game
        String d = "simple";//// program assumes you enter a board. Defaults to simple
        String boardName = "";
        /*try{
            if (args[0].equals("-b")){
                fileName = fileName + args[1];
                boardName = args[1];
            }
            else if (args[2].equals("-b")){
                fileName = fileName + args[3];
                boardName = args[3];
            }
        }
        catch(Exception e){
            
        }
        try{
            if(args[0].equals("-d")){
                d = args[1];
            }
            else if (args[2].equals("-d")){
                d = args[3];
            }
        }
        catch(Exception e){
            
        }*/
        fileName = fileName + "board2.txt";//delete these 2 b4 trying to use command prompt
        boardName = "board2.txt";
        System.out.println();
        System.out.println("Board " + boardName.substring(5, 6) + ":");
        
        Scanner fileScan = new Scanner(new FileInputStream(fileName));
        String fileLine = fileScan.nextLine();
        String letters[][] = {{"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}};
        int row = 0;
        int col = 0;
        for(int i = 0; i < 16; i++){
            System.out.print(fileLine.substring(i, i+1) + " ");//prints out the board
            letters[col][row] = fileLine.substring(i, i+1).toLowerCase();
            row++;
            if((i + 1) % 4 == 0){
                System.out.println();
                row = 0;
                col++;
            }
        }
        Scanner dictScan = new Scanner(new FileInputStream("C://Users/Owner/Documents/NetBeansProjects/Austin's Stuff/src/dictionary.txt"));
        String  currentWord;
        //System.out.print(d);
        if(d.equals("simple")){
            SimpleDictionary dict = new SimpleDictionary();
            ArrayList<String> boggleList = new ArrayList<String>();
            //int count = 0;
            while(dictScan.hasNextLine()){
                currentWord = dictScan.nextLine();
                dict.add(currentWord);
                
            }
            
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 4; j++){
                    String lettersCopy[][] = {{"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}};
                    for(int k = 0; k < 4; k ++){
                        for(int h = 0; h < 4; h++){
                            lettersCopy[h][k] = letters[h][k];
                        }
                    }
                    boggleWords(dict, boggleList, letters[j][i], lettersCopy, i, j);
                }
            }
            System.out.println("");
            //boggleList.add("asdg");
            boggleList.sort(null);
            
            
            System.out.print("Write as many words that are on the board as you can, separated by spaces: ");
            Scanner scanner = new Scanner(System.in);
            String guesses = scanner.nextLine();
            scanner.close();
            
            //System.out.println("Got here");
            guesses = guesses + " ";
            
            
            
            //System.out.println("here");'
            ArrayList<String> guessArray = new ArrayList<String>();
            while(guesses.contains(" ")){
                guessArray.add(guesses.substring(0, guesses.indexOf(" ")));
                guesses = guesses.substring(guesses.indexOf(" ") + 1);
            }
            ArrayList<String> correctGuesses = new ArrayList<String>();
            for(int i = 0; i < guessArray.size(); i ++){
                if(boggleList.contains(guessArray.get(i))){
                    correctGuesses.add(guessArray.get(i));
                }
            }
            System.out.println();
            System.out.println("Valid words in this boggle:");
            for(int i = 0; i < boggleList.size(); i++){
                System.out.println(boggleList.get(i));
            }
            System.out.println();
            System.out.println("Valid guessed words:");
            for(int i = 0; i < correctGuesses.size(); i++){
                System.out.println(correctGuesses.get(i));
            }
            System.out.println("\nYou guessed " + correctGuesses.size() + " words correctly");
            
            System.out.println("There were " + boggleList.size() + " words in the board.");
            System.out.println("You correctly stated " + (double)correctGuesses.size() / (double)boggleList.size() * 100 + " precent of the words.");
             
             
             
             
             
             
        }
        else{
            
        }
        
        
        
        //System.out.println("There were " + + " total words:");
        
    }
    
    private static void boggleWords(DictionaryInterface dict, ArrayList<String> storeWords, String current, String[][] board, int currentX, int currentY){
        //current = current + board[currentY][currentX];
        if(current.equals("*")){
            current = "";
            String lettersCopy[][] = {{"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}};
            
            for(int g = 97; g < 123; g++){
                for(int k = 0; k < 4; k ++){
                    for(int h = 0; h < 4; h++){
                        lettersCopy[h][k] = board[h][k];
                    }
                }
                //System.out.print();
                lettersCopy[currentY][currentX] = "";
                boggleWords(dict, storeWords, Character.toString((char)g), lettersCopy, currentX, currentY);
            }
        }
        StringBuilder build = new StringBuilder(current);
        int search = dict.search(build);
        //System.out.print("2");
        boolean arePrefixes = false;
        //System.out.println(search);
        if(search == 0){
            return;
        }
        else if(search == 1){
            arePrefixes = true;
        }
        else if(search == 2){
            if(!storeWords.contains(current)){
                if(current.length() > 2){
                    storeWords.add(current);
                }
            }
            return;
        }
        else if(search == 3){
            if(!storeWords.contains(current)){
                if(current.length() > 2){
                    storeWords.add(current);
                }
            }
            arePrefixes = true;
        }
        if(arePrefixes){
            //System.out.print("2");
            board[currentY][currentX] = "";//might be changing letters
            for(int i = -1; i < 2; i++){
                for(int j = -1; j < 2; j++){
                    try{
                        if(board[currentY + j][currentX + i].equals("")){
                            
                        }
                        else if(board[currentY + j][currentX + i].equals("*")){
                            String lettersCopy[][] = {{"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}};
                            for(int k = 0; k < 4; k ++){
                                for(int h = 0; h < 4; h++){
                                    lettersCopy[h][k] = board[h][k];
                                }
                            }
                            for(int g = 97; g < 123; g++){
                                //lettersCopy[currentY][currentX] = "";
                                boggleWords(dict, storeWords, current + Character.toString((char)g), lettersCopy, currentX + i, currentY + j);
                            }
                        }
                        else{
                            //if(i == 1 && j == -1){
                                //System.out.print("^");
                            //}
                            String lettersCopy[][] = {{"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}, {"-", "-", "-", "-"}};
                            for(int k = 0; k < 4; k ++){
                                for(int h = 0; h < 4; h++){
                                    lettersCopy[h][k] = board[h][k];
                                }
                            }
                            //lettersCopy[currentY][currentX] = "";
                            boggleWords(dict, storeWords, current + board[currentY + j][currentX + i], lettersCopy, currentX + i, currentY + j);
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        
                    }
                }
            }
        }
    }
}