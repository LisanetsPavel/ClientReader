package controller;

import application.ClientApp;
import client.RestBookClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by pc8 on 25.09.15.
 */
public class LoginController {

    @FXML
    TextField login;
    @FXML
    TextField password;

    private RestBookClient restBookClient;
    private ClientApp clientApp;

    public void setRestBookClient(RestBookClient restBookClient) {
        this.restBookClient = restBookClient;
    }

    public void setClientApp(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    public void init(){

    }

    @FXML
    private void handleEnter(){
        System.out.println(login.getText());
        System.out.println(password.getText());
    }

}
