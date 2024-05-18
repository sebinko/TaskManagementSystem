package dk.via.taskmanagement.view;

import dk.via.taskmanagement.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class LoginView {
    private ViewHandler viewHandler;
    private LoginViewModel loginViewModel;

    private Region root;

    public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.loginViewModel = loginViewModel;
        this.root = root;
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
