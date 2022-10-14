package za.co.nemesisnet.supreme.invention.model;

import java.io.Serializable;

// must implement Serializable in order to be sent
public class Message implements Serializable {
    private  String text;
 public Message()
 {

 }
    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
     public void setText(String text) {
         this.text = text;
     }
}