package uta.cse3310;

import java.util.List;
import java.util.Date;

public class ChatBox {

         
        private String sender;
        private String content;
        private Date timestamp;


        public String getSender() {
            return sender;
        }
        public void setSender(String sender) {
            this.sender = sender;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public Date getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

    

    private List<String> messages; 


     public ChatBox(List<String> messages) { 
        this.messages = messages;

    } 
    public void addMessage(String msg) {
        

    } 
    public void displayChat() {
        
    }

    public List<String> getMessages() {
        return messages;
    }
    
    
    
}