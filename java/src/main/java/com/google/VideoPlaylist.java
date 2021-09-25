
package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a video playlist entity. */
public class VideoPlaylist {
	private final String name;
	private final List<Video> videos;

	public VideoPlaylist(String name) {
		this.name = name;
		this.videos = new ArrayList<>();
	}

	/** Returns a read-only collection of videos present in the playlist. */
	public List<Video> getVideos() {
		return this.videos;
	}
}
