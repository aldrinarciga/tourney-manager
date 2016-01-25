package app.models;

import javafx.beans.property.BooleanProperty;

/**
 * Created by samsung on 1/25/2016.
 */
public class Player {
    private String firstName;
    private String lastName;
    private boolean rated;

    public Player(String firstName, String lastName, boolean isRated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rated = isRated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }
}
