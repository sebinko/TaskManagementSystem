package dk.via.taskmanagement.view;

import dk.via.taskmanagement.viewmodel.RegisterViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;



public class RegisterView {
    private ViewHandler viewHandler;
    private RegisterViewModel registerViewModel;
    private Region root;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    TextField passwordConfirmation;

    @FXML
    Label message;

    public void init(ViewHandler viewHandler, RegisterViewModel registerViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.registerViewModel = registerViewModel;
        this.root = root;

        registerViewModel.bindUsername(username.textProperty());
        registerViewModel.bindPassword(password.textProperty());
        registerViewModel.bindPasswordConfirmation(passwordConfirmation.textProperty());
        registerViewModel.bindMessage(message.textProperty());
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
