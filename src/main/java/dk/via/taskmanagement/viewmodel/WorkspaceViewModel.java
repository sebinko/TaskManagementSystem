package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkspaceViewModel {
    private final Model model;

    StringProperty workspaceName;

    public WorkspaceViewModel(Model model) {
        this.model = model;
        workspaceName = new SimpleStringProperty();
    }

    public void bindWorkspaceName(StringProperty property) {
        property.bindBidirectional(workspaceName);
    }
}
