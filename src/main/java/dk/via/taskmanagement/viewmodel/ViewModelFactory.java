package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;

public class ViewModelFactory {
    private final LoginViewModel loginViewModel;
    private final RegisterViewModel registerViewModel;
    private final CreateWorkspaceViewModel createWorkspaceViewModel;

    public ViewModelFactory(Model model) {
        loginViewModel = new LoginViewModel(model);
        registerViewModel = new RegisterViewModel(model);
        createWorkspaceViewModel = new CreateWorkspaceViewModel(model);
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }

    public CreateWorkspaceViewModel getCreateWorkspaceViewModel() {
        return createWorkspaceViewModel;
    }
}
