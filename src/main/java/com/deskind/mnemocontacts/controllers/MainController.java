package com.deskind.mnemocontacts.controllers;

import com.deskind.mnemocontacts.services.GoogleService;
import com.deskind.mnemocontacts.utilities.PhoneNumberProcessor;
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
        PhoneNumberProcessor phoneNumberProcessor = new  PhoneNumberProcessor();
        
        try {
            //Getting people service
            people = GoogleService.getPeopleService();
            // Request connections.
            ListConnectionsResponse response = people.people().connections()
                    .list("people/me")
                    .setRequestMaskIncludeField("person.names,person.emailAddresses,person.phoneNumbers")
                    .setPageSize(5)
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
            //Get person names
            List<Name> names = p.getNames();
            //Get person PhoneNumbers
            List<PhoneNumber> phones = p.getPhoneNumbers();
            //Initialize titled pane and anchore pane with new Instance 
            titledPane = new TitledPane();
            anchorPane = new AnchorPane();
            //Compose anchore pane
            for(PhoneNumber phoneNumber : phones){
                anchorPane.getChildren().add(new Label(phoneNumber.getValue()));
                anchorPane.getChildren().add(phoneNumberProcessor.processPhoneNumber(phoneNumber));
            }
            //Configure titled pane
            titledPane.setText(names.get(0).getDisplayName());
            titledPane.setContent(anchorPane);
            //Add pane to collection
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
