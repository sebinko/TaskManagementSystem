package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;

public class ViewModelFactory {
    private final LoginViewModel loginViewModel;
    private final RegisterViewModel registerViewModel;

    public ViewModelFactory(Model model) {
        loginViewModel = new LoginViewModel(model);
        registerViewModel = new RegisterViewModel(model);
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }
}
