package forum;

import java.time.LocalDateTime;

public class Entry {

    // The name of the person who made the forum post.
    private String userName;

    // The actual text of the forum post.
    private String message;

    // The date and time when this Entry was created.
    private LocalDateTime dateTime;

    // Constructor: creates a new forum entry from a username and message.
    public Entry(String userName, String message) {
        this.userName = userName;
        this.message = message;

        // Record the current date and time when the entry is created.
        this.dateTime = LocalDateTime.now();
    }

    // Getter for the username.
    public String getUserName() {
        return userName;
    }

    // Getter for the message.
    public String getMessage() {
        return message;
    }

    // Getter for the submission date and time.
    public LocalDateTime getDateTime() {
        return dateTime;
    }
}