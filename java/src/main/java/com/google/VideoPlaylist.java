
package com.google;

import java.util.ArrayList;
import java.util.List;

/** The class representing a video playlist entity. */
public class VideoPlaylist {
    private final String name;
    private final List<Video> videos;

    public VideoPlaylist(String name) {
        this.name = name;
        this.videos = new ArrayList<>();
    }

    /** Returns the playlist's name. */
    public String getName() {
        return this.name;
    }

    /** Returns a read-only collection of videos present in the playlist. */
    public List<Video> getVideos() {
        return this.videos;
    }
}
