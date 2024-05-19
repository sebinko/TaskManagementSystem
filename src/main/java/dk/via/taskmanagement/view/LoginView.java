package dk.via.taskmanagement.view;

import dk.via.taskmanagement.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;


public class LoginView {
    private ViewHandler viewHandler;
    private LoginViewModel loginViewModel;

    private Region root;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Label message;

    public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.loginViewModel = loginViewModel;
        this.root = root;

        loginViewModel.bindUsername(username.textProperty());
        loginViewModel.bindPassword(password.textProperty());
        loginViewModel.bindMessage(message.textProperty());
    }

    @FXML
    public void login() {
        loginViewModel.login();
    }

    @FXML
    public void openWelcomeView() {
        viewHandler.openView(ViewFactory.WELCOME);
    }
}
