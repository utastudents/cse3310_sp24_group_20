package uta.cse3310;

import java.io.BufferedReader; 
import java.io.FileReader; 
import java.util.ArrayList; 
import java.lang.StringBuilder;
import java.io.IOException; 

public class WordList { 

     private String fileName; 
     private ArrayList<Word> words = new ArrayList<Word>();  

     public WordList(String fileName) {
          this.fileName = fileName;  
     } 
     public String wordsFromFile() {  
          try {
             BufferedReader fileReader = new BufferedReader(new FileReader(fileName)); 
             String buffer = null;  
             while((buffer = fileReader.readLine()) != null) {
                  Word word = new Word(buffer, buffer.length());
                  words.add(word); 
             }    
          }
          catch(IOException e) { 
               System.err.println("\n!!! Incorrect File Name !!! :" + e); 
               System.exit(-1); 
          }
          
          StringBuilder returnWordString = new StringBuilder(); 
             for(int i = 0; i < words.size(); i++) {
                  returnWordString.append(words.get(i)).append("\n");
             }
          return returnWordString.toString();
     }

}