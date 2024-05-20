package dk.via.taskmanagement.validation;

public class WorkspaceValidation {
    public static boolean validateName(String name) {
        return !name.isEmpty();
    }
}
