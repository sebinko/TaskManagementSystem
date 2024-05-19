package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.validation.UserValidation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegisterViewModel {
    private final Model model;

    private StringProperty username;
    private StringProperty password;
    private StringProperty passwordConfirmation;
    private StringProperty message;

    private StringProperty role;

    public RegisterViewModel(Model model) {
        this.model = model;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        passwordConfirmation = new SimpleStringProperty();
        message = new SimpleStringProperty();
        role = new SimpleStringProperty();
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

    public void bindRole(ObjectProperty<String> property) {
        property.bindBidirectional(role);
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

        User user = new User(username.get(), password.get(), role.get(), null);

        model.createUser(user);
        message.set("User created successfully");
    }


}
