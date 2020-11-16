package xyz.dreeks.audhumla.exception;

import java.util.ArrayList;

public class ProfileError extends Exception {

    public ProfileError(ArrayList<String> errors) {
        super("Could not load the following profiles: " + String.join(", ", errors) + "\nPlease remove those profile and recreate them to fix them.");
    }

}
