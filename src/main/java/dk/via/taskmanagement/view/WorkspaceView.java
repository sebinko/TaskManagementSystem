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

    public void init(ViewHandler viewHandler, WorkspaceViewModel workspaceViewModel, Region root) {
        this.viewHandler = viewHandler;
        viewModel = workspaceViewModel;

        viewModel.bindWorkspaceName(workspaceName.textProperty());

        workspaceName.setText(Auth.getInstance().getCurrentUser().getWorkspace().getName());
    }
}
