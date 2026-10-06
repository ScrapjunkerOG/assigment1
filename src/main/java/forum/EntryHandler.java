package forum;

import java.util.ArrayList;
import java.util.List;

public class EntryHandler {

    // A list containing every forum entry currently stored.
    private List<Entry> entries = new ArrayList<>();

    // Creates an Entry and adds it to the list.
    public void addEntry(String userName, String message) {

        // Create an Entry object using the supplied information.
        Entry entry = new Entry(userName, message);

        // Store the new entry in our list.
        entries.add(entry);
    }

    // Returns all entries currently stored in the forum.
    public List<Entry> getAllEntries() {

        return entries;
    }

    // Searches for a word or phrase in usernames and messages.
    public List<Entry> search(String searchTerm) {

        // This will contain only the entries that match the search.
        List<Entry> results = new ArrayList<>();

        // Convert the search term to lowercase so the search is
        // case-insensitive. For example, "Hello" matches "hello".
        searchTerm = searchTerm.toLowerCase();

        // Go through every Entry stored in the forum.
        for (Entry entry : entries) {

            // Get the username and convert it to lowercase.
            String userName =
                    entry.getUserName().toLowerCase();

            // Get the message and convert it to lowercase.
            String message =
                    entry.getMessage().toLowerCase();

            // Check whether the search term occurs in either
            // the username OR the message.
            if (userName.contains(searchTerm)
                    || message.contains(searchTerm)) {

                // If it matches, add the Entry to the search results.
                results.add(entry);
            }
        }

        // Give the matching entries back to whoever called search().
        return results;
    }
}