
package com.google;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** A class used to represent a video entity. */
public class Video {
    private final String title;
    private final String id;
    private final List<String> tags;

    private boolean flagged = false;        // Added as part of FLAG_VIDEO
    private String reason = null;           // Added as part of FLAG_VIDEO

    public Video(String title, String id, List<String> tags) {
        this.title = title;
        this.id = id;
        this.tags = Collections.unmodifiableList(tags);
    }

    /** Formats the video's tags as required in {@link String} form. */
    private String formatTags() {
        if (this.tags.isEmpty())
            return "[]";

        final StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.tags.size(); i++) {
            if (i == this.tags.size() - 1)
                sb.append(this.tags.get(i) + "]");
            else
                sb.append(this.tags.get(i) + " ");
        }

        return sb.toString();
    }

    /** Returns the video's title. */
    public String getTitle() {
        return this.title;
    }

    /** Returns the video's URL. */
    public String getVideoId() {
        return this.id;
    }

    /** Returns a read-only collection of a video's tags. */
    public List<String> getTags() {
        return this.tags;
    }

    /** Determines whether a given term exists amongst the video's tags. */
    public boolean tagExists(String term) {
        for (String tag : this.tags) {
            if (tag.equalsIgnoreCase(term))
                return true;
        }

        return false;
    }

    /** Returns the video's "flagged" status. */
    public boolean isFlagged() {
        return this.flagged;
    }

    /** Returns the reason for which the video has been "flagged". */
    public String getFlag() {
        return this.reason;
    }

    /** Marks the video as "flagged" with a given reason if supplied. */
    public void flag(String reason) {
        this.flagged = true;
        this.reason = Objects.requireNonNullElse(reason, "Not supplied");
    }

    /** Removes the video's "flag". */
    public void unflag() {
        this.flagged = false;
        this.reason = null;
    }

    /** Displays the video as required in {@link String} form. */
    @Override
    public String toString() {
        return this.title + " (" + this.id + ") " + this.formatTags();
    }
}
