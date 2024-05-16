package dk.via.taskmanagement;

import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.ModelManager;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.model.WorkspaceUserList;
import dk.via.taskmanagement.shared.Connector;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        Connector connector = (Connector) registry.lookup("Connector");
        Model model = new ModelManager(connector);

        System.out.println(model.getWorkspace(null));

        WorkspaceUserList workspaceUserList = new WorkspaceUserList();
        Workspace workspace = new Workspace("Workspace 2", workspaceUserList);

        System.out.println(model.createWorkspace(workspace));

        System.out.println(model.getWorkspace(null));

    }
}
