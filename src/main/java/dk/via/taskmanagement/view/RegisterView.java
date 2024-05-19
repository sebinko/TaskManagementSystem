package dk.via.taskmanagement.view;

import dk.via.taskmanagement.viewmodel.RegisterViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;



public class RegisterView {
    private ViewHandler viewHandler;
    private RegisterViewModel registerViewModel;
    private Region root;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    TextField passwordConfirmation;

    @FXML
    ComboBox<String> role;

    @FXML
    Label message;


    public void init(ViewHandler viewHandler, RegisterViewModel registerViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.registerViewModel = registerViewModel;
        this.root = root;

        role.getItems().addAll("admin", "member");

        registerViewModel.bindUsername(username.textProperty());
        registerViewModel.bindPassword(password.textProperty());
        registerViewModel.bindPasswordConfirmation(passwordConfirmation.textProperty());
        registerViewModel.bindMessage(message.textProperty());
        registerViewModel.bindRole(role.valueProperty());
    }

    @FXML
    public void openWelcomeView() {
        viewHandler.openView(ViewFactory.WELCOME);
    }

    @FXML
    public void register() {
        registerViewModel.register();
    }

}
