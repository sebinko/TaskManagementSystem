package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModel {
    private final Model model;

    private StringProperty username;
    private StringProperty password;
    private StringProperty message;

    public LoginViewModel(Model model) {
        this.model = model;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        message = new SimpleStringProperty();
    }

    public void bindUsername(StringProperty property) {
        property.bindBidirectional(username);
    }

    public void bindPassword(StringProperty property) {
        property.bindBidirectional(password);
    }

    public void bindMessage(StringProperty property) {
        property.bind(message);
    }

    public void login() {

    }
}
