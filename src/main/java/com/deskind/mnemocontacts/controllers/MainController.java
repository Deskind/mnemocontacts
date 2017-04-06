package com.deskind.mnemocontacts.controllers;

import com.deskind.mnemocontacts.services.GoogleService;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {
    List<Person> personsList = null;
    
    @FXML
    private Button showContacts;
    
    @FXML
    private Accordion accordionWithContacts;
    
    @FXML
    void handleShowContacts(ActionEvent event){
        //Loc vars
        People people;
        List<Person> list = null;
        
        TitledPane titledPane = null;
        AnchorPane anchorPane = null;
        List<TitledPane> titledPanes = new ArrayList<TitledPane>();
        
        try {
            //Getting people service
            people = GoogleService.getPeopleService();
            // Request connections.
            ListConnectionsResponse response = people.people().connections()
                    .list("people/me")
                    .setRequestMaskIncludeField("person.names,person.emailAddresses,person.phoneNumbers")
                    .setPageSize(20)
                    .execute();
            //Getting list of people from connections
            list = response.getConnections();
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //init list of persons
        personsList = list;
        //iterate over list and show mnemo contacts
        for(Person p : list){
            List<Name> names = p.getNames();
            List<PhoneNumber> phones = p.getPhoneNumbers();
            
            titledPane = new TitledPane();
                anchorPane = new AnchorPane();
                anchorPane.getChildren().add(new Label(phones.get(0).getValue()));
            titledPane.setText(names.get(0).getDisplayName());
            titledPane.setContent(anchorPane);
            
                
                
            titledPanes.add(titledPane);
        }
        accordionWithContacts.getPanes().addAll(titledPanes);
    }
    
    public void showMnemoContact(Person p){
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}
