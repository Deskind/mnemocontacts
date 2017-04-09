
package com.deskind.mnemocontacts.utilities;

import com.google.api.services.people.v1.model.PhoneNumber;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class PhoneNumberProcessor {
    
    public HBox processPhoneNumber(PhoneNumber phoneNumber){
        String n = phoneNumber.getValue();
        String num = n.replaceAll("\\ ", "");
        String number = num.replaceAll("\\-", "");
        HBox hBox = new HBox(10);
        Image image = null;
        
        String [] numberParts = new String[3];
        
        numberParts[0] = number.substring(number.length()-7, number.length()-4);
        numberParts[1] = number.substring(number.length()-4, number.length()-2);
        numberParts[2] = number.substring(number.length()-2);
        
        for(int i = 0 ; i < numberParts.length; i++){
            
            try {
                image = new Image(new FileInputStream("c:\\Users\\Desk1nd\\Documents\\NetBeansProjects\\mnemocontacts\\cards\\"+numberParts[i]+".jpg"), 150, 200, true, true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PhoneNumberProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(image == null){
                System.out.println("No such card!");
            }else{
                System.out.println("Everything is ok!!");
                hBox.getChildren().add(new ImageView(image));
            }
            
        }
        return hBox;
    }
    
}
