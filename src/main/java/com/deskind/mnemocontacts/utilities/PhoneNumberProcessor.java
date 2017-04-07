
package com.deskind.mnemocontacts.utilities;

import com.google.api.services.people.v1.model.PhoneNumber;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class PhoneNumberProcessor {
    
    public HBox processPhoneNumber(PhoneNumber phoneNumber){
        String number = phoneNumber.getValue();
        HBox hBox = new HBox(5);
        Image image = null;
        
        String [] numberParts = new String[3];
        
        numberParts[0] = number.substring(number.length()-7, number.length()-4);
        numberParts[1] = number.substring(number.length()-4, number.length()-2);
        numberParts[2] = number.substring(number.length()-2);
        
        for(int i = 0 ; i < numberParts.length; i++){
            image = new Image("/cards/"+numberParts[i]+".jpg");
            if(image == null){
                System.out.println("No such card!");
            }else{
                System.out.println("Everything is ok!!");
                hBox.getChildren().add(new ImageView(image));
            }
            
        }
        System.out.println("------->" + numberParts.length);
        
        return hBox;
    }
    
}
