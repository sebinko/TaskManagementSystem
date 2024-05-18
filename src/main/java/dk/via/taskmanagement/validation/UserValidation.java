package dk.via.taskmanagement.validation;

public class UserValidation {
    public static boolean validateUsername(String username) {
        return username != null && username.length() >= 8;
    }

    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 8;
    }

    public static boolean validatePasswordConfirmation(String password, String passwordConfirmation) {
        return password != null && password.equals(passwordConfirmation);
    }
}
