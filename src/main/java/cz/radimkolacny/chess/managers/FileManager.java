/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.managers;
import cz.radimkolacny.chess.gamefield.Player;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
/**
 *
 * @author radimkolacny
 */
public class FileManager {
    
    private final File fileWithResults = new File("src/resources/file_with_results_of_game.txt");
    private DefaultListModel<String> previousGames = new DefaultListModel<>();

    public ListModel getPreviousGames() {
        return previousGames;
    }
    
    public void saveResultOfGame(Player winner, Player losser, String typeOfEndGame){
        
        String resultOfGameInText = createTextForSave(winner, losser, typeOfEndGame);
        LocalDateTime dateAndTime = LocalDateTime.now();
        
        try {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileWithResults,true))) {
                bufferedWriter.append(dateAndTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")) + ": " + resultOfGameInText);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadPreviousGames(){
        
            String previousGame = "";
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileWithResults))) {
                while((previousGame = bufferedReader.readLine()) != null){
                    previousGames.addElement(previousGame);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
        }
    }
    
    private String createTextForSave(Player winner, Player losser, String typeOfEndGame){
        
        int pointForWinner;
        if(typeOfEndGame.equals("CHECKMATE")){
            pointForWinner = 1;
        }
        else{
            pointForWinner = 0;
        }
        return String.format("%s (%d - 0) %s", winner, pointForWinner, losser);
    }
    
    public void serializeGame(GameManager gameManager) throws FileNotFoundException, IOException{
        
        try (FileOutputStream fos = new FileOutputStream("game.ser"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            System.out.println("start serialize");
            oos.writeObject(gameManager);
        }
    }
}
