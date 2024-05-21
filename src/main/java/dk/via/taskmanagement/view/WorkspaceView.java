package dk.via.taskmanagement.view;

import dk.via.taskmanagement.utilities.Auth;
import dk.via.taskmanagement.viewmodel.WorkspaceViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

public class WorkspaceView {
    private ViewHandler viewHandler;
    private WorkspaceViewModel viewModel;

    @FXML
    Label workspaceName;

    @FXML
    Button manageWorkspaceButton;

    @FXML
    Region manageWorkspaceRegion;

    public void init(ViewHandler viewHandler, WorkspaceViewModel workspaceViewModel, Region root) {
        this.viewHandler = viewHandler;
        viewModel = workspaceViewModel;

        viewModel.bindWorkspaceName(workspaceName.textProperty());


        // TODO toto do viewmodelu
        workspaceName.setText(Auth.getInstance().getCurrentUser().getWorkspace().getName());

        // if user is admin then show manage workspace button and region otherwise hide
        boolean isAdmin = Auth.getInstance().getCurrentUser().getRole().equals("admin");

        manageWorkspaceButton.setVisible(isAdmin);
        manageWorkspaceRegion.setVisible(isAdmin);
    }

    @FXML
    public void openManageWorkspaceView() {
        viewHandler.openView(ViewFactory.MANAGE_WORKSPACE);
    }
}
