
package com.google;

import java.util.*;

/** A class representing the video player simulator. */
public class VideoPlayer {
	private final VideoLibrary library;

	private Video current = null;                               // Added as part of PLAY
	private boolean paused = false;                             // Added as part of PAUSE
	private final Map<String, VideoPlaylist> playlists;         // Added as part of CREATE_PLAYLIST

	public VideoPlayer() {
		this.library = new VideoLibrary();
		this.playlists = new TreeMap<>();
	}

	public void numberOfVideos() {
		System.out.printf("%s videos in the library\n", this.library.getVideos().size());
	}

	public void showAllVideos() {
		if (!this.library.getVideos().isEmpty()) {
			final List<Video> vl = this.library.getVideos();
			vl.sort(Comparator.comparing(Video::getTitle));
			System.out.println("Here's a list of all available videos:");
			vl.forEach(v -> {
				if (v.isFlagged())
					System.out.printf("\t%s - FLAGGED (reason: %s)\n", v, v.getFlag());
				else
					System.out.printf("\t%s\n", v);
			});
		}
	}

	public void playVideo(String id) {
		if (!this.library.getVideos().isEmpty()) {
			final Video v = this.library.getVideo(id);
			if (v == null)
				System.out.println("Cannot play video: Video does not exist");
			else {
				if (v.isFlagged())
					System.out.printf("Cannot play video: Video is currently flagged (reason: %s)\n", v.getFlag());
				else {
					if (this.current != null)
						System.out.printf("Stopping video: %s\n", this.current.getTitle());
					if (this.paused)
						this.paused = false;
					this.current = v;
					System.out.printf("Playing video: %s\n", v.getTitle());
				}
			}
		}
	}

	public void stopVideo() {
		if (this.current == null)
			System.out.println("Cannot stop video: No video is currently playing");
		else {
			System.out.printf("Stopping video: %s\n", this.current.getTitle());
			this.current = null;
			this.paused = false;
		}
	}

	public void playRandomVideo() {
		if (this.library.getVideos().isEmpty())
			System.out.println("No videos available");
		else {
			final List<String> uf = new ArrayList<>();
			this.library.getVideos().forEach(v -> {
				if (!v.isFlagged())
					uf.add(v.getVideoId());
			});
			if (uf.isEmpty())
				System.out.println("No videos available");
			else
				this.playVideo(uf.get(new Random().nextInt(uf.size())));
		}
	}

	public void pauseVideo() {
		if (this.current == null)
			System.out.println("Cannot pause video: No video is currently playing");
		else {
			if (!this.paused) {
				this.paused = true;
				System.out.printf("Pausing video: %s\n", this.current.getTitle());
			} else
				System.out.printf("Video already paused: %s\n", this.current.getTitle());
		}
	}

	public void continueVideo() {
		if (this.current == null)
			System.out.println("Cannot continue video: No video is currently playing");
		else {
			if (!this.paused)
				System.out.println("Cannot continue video: Video is not paused");
			else {
				this.paused = false;
				System.out.printf("Continuing video: %s\n", this.current.getTitle());
			}
		}
	}

	public void showPlaying() {
		if (this.current == null)
			System.out.println("No video is currently playing");
		else {
			if (!this.paused)
				System.out.printf("Currently playing: %s\n", this.current);
			else
				System.out.printf("Currently playing: %s - PAUSED\n", this.current);
		}
	}

	/** A method to search for/retrieve a specific {@link VideoPlaylist} object. */
	private VideoPlaylist retrievePlaylist(String name) {
		for (String key : this.playlists.keySet()) {
			if (key.equalsIgnoreCase(name))
				return this.playlists.get(key);
		}

		return null;
	}

	public void createPlaylist(String name) {
		if (this.retrievePlaylist(name) != null)
			System.out.println("Cannot create playlist: A playlist with the same name already exists");
		else {
			this.playlists.put(name, new VideoPlaylist(name));
			System.out.printf("Successfully created new playlist: %s\n", name);
		}
	}

	public void addVideoToPlaylist(String name, String id) {
		final VideoPlaylist vp = this.retrievePlaylist(name);
		if (vp == null)
			System.out.printf("Cannot add video to %s: Playlist does not exist\n", name);
		else {
			final Video v = this.library.getVideo(id);
			if (v == null)
				System.out.printf("Cannot add video to %s: Video does not exist\n", name);
			else {
				if (v.isFlagged())
					System.out.printf("Cannot add video to %s: Video is currently flagged (reason: %s)\n", name, v.getFlag());
				else {
					final List<Video> pl = vp.getVideos();
					if (!pl.contains(v)) {
						pl.add(v);
						System.out.printf("Added video to %s: %s\n", name, v.getTitle());
					} else
						System.out.printf("Cannot add video to %s: Video already added\n", name);
				}
			}
		}
	}

	public void showAllPlaylists() {
		if (this.playlists.isEmpty())
			System.out.println("No playlists exist yet");
		else {
			System.out.println("Showing all playlists:");
			this.playlists.keySet().forEach(name -> System.out.printf("\t%s\n", name));
		}
	}

	public void showPlaylist(String name) {
		final VideoPlaylist vp = this.retrievePlaylist(name);
		if (vp == null)
			System.out.printf("Cannot show playlist %s: Playlist does not exist\n", name);
		else {
			final List<Video> pl = vp.getVideos();
			System.out.printf("Showing playlist: %s\n", name);
			if (pl.isEmpty())
				System.out.println("\tNo videos here yet");
			else {
				pl.forEach(v -> {
					if (v.isFlagged())
						System.out.printf("\t%s - FLAGGED (reason: %s)\n", v, v.getFlag());
					else
						System.out.printf("\t%s\n", v);
				});
			}
		}
	}

	public void removeFromPlaylist(String name, String id) {
		final VideoPlaylist vp = this.retrievePlaylist(name);
		if (vp == null)
			System.out.printf("Cannot remove video from %s: Playlist does not exist\n", name);
		else {
			final Video v = this.library.getVideo(id);
			if (v == null)
				System.out.printf("Cannot remove video from %s: Video does not exist\n", name);
			else {
				final List<Video> pl = vp.getVideos();
				if (!pl.contains(v))
					System.out.printf("Cannot remove video from %s: Video is not in playlist\n", name);
				else {
					pl.remove(v);
					System.out.printf("Removed video from %s: %s\n", name, v.getTitle());
				}
			}
		}
	}

	public void clearPlaylist(String name) {
		final VideoPlaylist vp = this.retrievePlaylist(name);
		if (vp == null)
			System.out.printf("Cannot clear playlist %s: Playlist does not exist\n", name);
		else {
			vp.getVideos().clear();
			System.out.printf("Successfully removed all videos from %s\n", name);
		}
	}

	public void deletePlaylist(String name) {
		if (this.retrievePlaylist(name) == null)
			System.out.printf("Cannot delete playlist %s: Playlist does not exist\n", name);
		else {
			for (String key : this.playlists.keySet()) {
				if (key.equalsIgnoreCase(name))
					this.playlists.remove(key);
			}
			System.out.printf("Deleted playlist: %s\n", name);
		}
	}

	private void searchVideosBy(String term, int func) {
		if (!this.library.getVideos().isEmpty()) {
			final List<Video> vl = new ArrayList<>();
			if (func == 1) {
				this.library.getVideos().forEach(v -> {
					if (!v.isFlagged()) {
						if (v.getTitle().toLowerCase().contains(term.toLowerCase()))
							vl.add(v);
					}
				});
			} else {
				this.library.getVideos().forEach(v -> {
					if (!v.isFlagged()) {
						if (v.tagExists(term))
							vl.add(v);
					}
				});
			}

			if (vl.isEmpty())
				System.out.printf("No search results for %s\n", term);
			else {
				vl.sort(Comparator.comparing(Video::getTitle));
				System.out.printf("Here are the results for %s:\n", term);
				for (int i = 1; i <= vl.size(); i++)
					System.out.printf("\t%d) %s\n", i, vl.get(i - 1));

				System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
						"If your answer is not a valid number, we will assume it's a no.");
				int x;
				try {
					x = new Scanner(System.in).nextInt();
				} catch (InputMismatchException e) {
					x = 0;
				}
				if (x > 0 && x <= vl.size())
					this.playVideo(vl.get(x - 1).getVideoId());
			}
		} else
			System.out.println("No videos available");
	}

	public void searchVideos(String term) {
		this.searchVideosBy(term, 1);
	}

	public void searchVideosWithTag(String tag) {
		this.searchVideosBy(tag, 2);
	}

	public void flagVideo(String id, String reason) {
		if (!this.library.getVideos().isEmpty()) {
			final Video v = this.library.getVideo(id);
			if (v == null)
				System.out.println("Cannot flag video: Video does not exist");
			else {
				if (v.isFlagged())
					System.out.println("Cannot flag video: Video is already flagged");
				else {
					if (this.current == v)
						this.stopVideo();
					v.flag(reason);
					System.out.printf("Successfully flagged video: %s (reason: %s)\n", v.getTitle(), v.getFlag());
				}
			}
		}
	}

	public void flagVideo(String id) {
		this.flagVideo(id, null);
	}

	public void allowVideo(String id) {
		if (!this.library.getVideos().isEmpty()) {
			final Video v = this.library.getVideo(id);
			if (v == null)
				System.out.println("Cannot remove flag from video: Video does not exist");
			else {
				if (v.isFlagged()) {
					v.unflag();
					System.out.printf("Successfully removed flag from video: %s\n", v.getTitle());
				} else
					System.out.println("Cannot remove flag from video: Video is not flagged");
			}
		}
	}
}
