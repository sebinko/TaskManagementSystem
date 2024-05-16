package dk.via.taskmanagement.model;

import java.io.Serializable;

public class Workspace implements Serializable {
    private final int id;
    private String name;
    private WorkspaceUserList workspaceUserList;

    public Workspace(String name, WorkspaceUserList workspaceUserList) {
        this.id = 0;
        this.name = name;
        this.workspaceUserList = workspaceUserList;
    }

    public Workspace(int id, String name, WorkspaceUserList workspaceUserList) {
        this.id = id;
        this.name = name;
        this.workspaceUserList = workspaceUserList;
    }

    public String getName() {
        return name;
    }

    public WorkspaceUserList getWorkspaceUserList() {
        return workspaceUserList;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", workspaceUserList=" + workspaceUserList +
                '}';
    }
}
