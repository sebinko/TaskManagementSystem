package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.validation.UserValidation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegisterViewModel {
    private final Model model;

    private StringProperty username;
    private StringProperty password;
    private StringProperty passwordConfirmation;
    private StringProperty message;

    public RegisterViewModel(Model model) {
        this.model = model;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        passwordConfirmation = new SimpleStringProperty();
        message = new SimpleStringProperty();
    }

    public void bindUsername(StringProperty property) {
        property.bindBidirectional(username);
    }

    public void bindPassword(StringProperty property) {
        property.bindBidirectional(password);
    }

    public void bindPasswordConfirmation(StringProperty property) {
        property.bindBidirectional(passwordConfirmation);
    }

    public void bindMessage(StringProperty property) {
        property.bind(message);
    }

    public void register() {
        if (!UserValidation.validateUsername(username.get())) {
            message.set("Username must be at least 8 characters long");
            return;
        }

        if (!UserValidation.validatePassword(password.get())) {
            message.set("Password must be at least 8 characters long");
            return;
        }

        if (!UserValidation.validatePasswordConfirmation(password.get(), passwordConfirmation.get())) {
            message.set("Passwords do not match");
            return;
        }

//        model.register(username.get(), password.get());
    }


}
