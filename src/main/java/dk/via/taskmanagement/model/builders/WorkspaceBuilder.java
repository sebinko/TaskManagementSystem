package dk.via.taskmanagement.model.builders;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.util.ArrayList;

public class WorkspaceBuilder {
        private Integer id;
        private String name;
        private ArrayList<User> users;

        public WorkspaceBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public WorkspaceBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public WorkspaceBuilder setUsers(ArrayList<User> users) {
            this.users = users;
            return this;
        }

        public Workspace build() {
            Workspace workspace = new Workspace(name);
            workspace.setId(id);
            workspace.getUsers().addAll(users);
            return workspace;
        }
}
