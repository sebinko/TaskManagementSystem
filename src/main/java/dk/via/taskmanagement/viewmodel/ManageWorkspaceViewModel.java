package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.utilities.Auth;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ManageWorkspaceViewModel implements PropertyChangeListener {
    private final Model model;

    StringProperty workspaceName;

    ListProperty<User> currentUsers;
    ListProperty<User> usersWithoutWorkspace;

    private ObjectProperty<User> usersWithoutWorkspaceSelected;

    private StringProperty userWithoutWorkspaceSelectedText;


    public ManageWorkspaceViewModel(Model model) {
        this.model = model;

        currentUsers = new SimpleListProperty<>(FXCollections.observableArrayList());
        usersWithoutWorkspace = new SimpleListProperty<>(FXCollections.observableArrayList());
        workspaceName = new SimpleStringProperty();
        usersWithoutWorkspaceSelected = new SimpleObjectProperty<>();
        userWithoutWorkspaceSelectedText = new SimpleStringProperty();
        model.addPropertyChangeListener(this);

    }

    public void init() {
        ArrayList<User> usersWithoutWorkspace = model.getUsersWithoutWorkspace();
        this.usersWithoutWorkspace.addAll(usersWithoutWorkspace);

        ArrayList<User> usersForWorkspace = model.getUsersForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());
        this.currentUsers.addAll(usersForWorkspace);
    }

    public void bindCurrentUsers(ObjectProperty<ObservableList<User>> property) {
        property.bind(currentUsers);
    }

    public void bindUsersWithoutWorkspace(ObjectProperty<ObservableList<User>> property) {
        property.bind(usersWithoutWorkspace);
    }

    public void bindUsersWithoutWorkspaceSelected(ReadOnlyObjectProperty<User> property) {
        usersWithoutWorkspaceSelected.bind(property);
    }

    public void bindUserWithoutWorkspaceSelectedText(StringProperty property) {
        property.bind(userWithoutWorkspaceSelectedText);
    }

    public void bindWorkspaceName(StringProperty property) {
        property.bindBidirectional(workspaceName);
    }

    public void onSelect() {
        if (usersWithoutWorkspaceSelected.get() != null) {
            userWithoutWorkspaceSelectedText.set("Selected: "+usersWithoutWorkspaceSelected.get().getUserName());
        } else {
            userWithoutWorkspaceSelectedText.set("Selected: ");
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("addWorkSpaceUser")) {
            if (Auth.getInstance().getCurrentUser() == null) {
                return;
            }

            this.currentUsers.clear();
            ArrayList<User> usersForWorkspace = model.getUsersForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());
            this.currentUsers.addAll(usersForWorkspace);

            this.usersWithoutWorkspace.clear();
            ArrayList<User> usersWithoutWorkspace = model.getUsersWithoutWorkspace();
            this.usersWithoutWorkspace.addAll(usersWithoutWorkspace);
        }
    }

    public void addUserToWorkspace() {
        User user = usersWithoutWorkspaceSelected.get();
        Workspace workspace = Auth.getInstance().getCurrentUser().getWorkspace();
        model.addWorkSpaceUser(workspace, user);
//        usersWithoutWorkspaceSelected.set(null);
    }
}
