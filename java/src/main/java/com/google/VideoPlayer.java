package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  private boolean playing = false;                          // Added as part of PLAY
  private Video current = null;                             // Added as part of PLAY
  private final Random rand = new Random();                 // Added as part of PLAY_RANDOM
  private boolean paused = false;                           // Added as part of PAUSE
  private final Map<String, VideoPlaylist> playlists;       // Added as part of CREATE_PLAYLIST

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playlists = new TreeMap<>();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  /** A method to format video tags in the required format. */
  private String formatVideoTags(List<String> t) {
    if (t.isEmpty())
      return "[]";

    final StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < t.size(); i++) {
      if (i == t.size() - 1)
        sb.append(t.get(i) + "]");
      else
        sb.append(t.get(i) + " ");
    }

    return sb.toString();
  }

  public void showAllVideos() {
    if (!this.videoLibrary.getVideos().isEmpty()) {
      final List<Video> vl = this.videoLibrary.getVideos();
      vl.sort(Comparator.comparing(Video::getTitle));
      System.out.println("Here's a list of all available videos:");
      vl.forEach(v -> System.out.printf("\t%s (%s) %s\n",
              v.getTitle(), v.getVideoId(), this.formatVideoTags(v.getTags())));
    }
  }

  public void playVideo(String videoId) {
    if (!this.videoLibrary.getVideos().isEmpty()) {
      final Video v = this.videoLibrary.getVideo(videoId);
      if (v == null)
        System.out.println("Cannot play video: Video does not exist");
      else {
        if (this.paused)
          this.paused = false;
        if (!this.playing)
          this.playing = true;
        else
          System.out.println("Stopping video: " + this.current.getTitle());
        this.current = v;
        System.out.println("Playing video: " + v.getTitle());
      }
    }
  }

  public void stopVideo() {
    if (!this.playing)
      System.out.println("Cannot stop video: No video is currently playing");
    else {
      System.out.println("Stopping video: " + this.current.getTitle());
      this.playing = false;
      this.paused = true;
      this.current = null;
    }
  }

  public void playRandomVideo() {
    if (this.videoLibrary.getVideos().isEmpty())
      System.out.println("No videos available");
    else {
      final int range = this.videoLibrary.getVideos().size();
      this.playVideo(this.videoLibrary.getVideos().get(rand.nextInt(range)).getVideoId());
    }
  }

  public void pauseVideo() {
    if (!this.playing)
      System.out.println("Cannot pause video: No video is currently playing");
    else {
      if (!this.paused) {
        this.paused = true;
        System.out.println("Pausing video: " + this.current.getTitle());
      } else
        System.out.println("Video already paused: " + this.current.getTitle());
    }
  }

  public void continueVideo() {
    if (!this.playing)
      System.out.println("Cannot continue video: No video is currently playing");
    else {
      if (!this.paused)
        System.out.println("Cannot continue video: Video is not paused");
      else {
        this.paused = false;
        System.out.println("Continuing video: " + this.current.getTitle());
      }
    }
  }

  /** A method added to output video data in the required format. */
  private String formatVideo(Video v) {
    return v.getTitle() + " (" + v.getVideoId() + ") " + this.formatVideoTags(v.getTags());
  }

  public void showPlaying() {
    if (!this.playing)
      System.out.println("No video is currently playing");
    else {
      if (!this.paused)
        System.out.println("Currently playing: " + this.formatVideo(this.current));
      else
        System.out.println("Currently playing: " + this.formatVideo(this.current) + " - PAUSED");
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

  public void createPlaylist(String playlistName) {
    if (this.retrievePlaylist(playlistName) != null)
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    else {
      this.playlists.put(playlistName, new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: " + playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    if (this.retrievePlaylist(playlistName) == null)
      System.out.printf("Cannot add video to %s: Playlist does not exist\n", playlistName);
    else {
      final List<Video> playlist = this.retrievePlaylist(playlistName).getVideos();
      if (this.videoLibrary.getVideo(videoId) == null)
        System.out.printf("Cannot add video to %s: Video does not exist\n", playlistName);
      else {
        final Video vid = this.videoLibrary.getVideo(videoId);
        if (!playlist.contains(vid)) {
          playlist.add(vid);
          System.out.printf("Added video to %s: %s\n", playlistName, vid.getTitle());
        } else
          System.out.printf("Cannot add video to %s: Video already added\n", playlistName);
      }
    }
  }

  public void showAllPlaylists() {
    if (this.playlists.isEmpty())
      System.out.println("No playlists exist yet");
    else {
      System.out.println("Showing all playlists:");
      this.playlists.forEach((key, val) -> System.out.printf("\t%s\n", key));
    }
  }

  public void showPlaylist(String playlistName) {
    if (this.retrievePlaylist(playlistName) == null)
      System.out.printf("Cannot show playlist %s: Playlist does not exist\n", playlistName);
    else {
      final List<Video> playlist = this.retrievePlaylist(playlistName).getVideos();
      System.out.printf("Showing playlist: %s\n", playlistName);
      if (playlist.isEmpty())
        System.out.println("\tNo videos here yet");
      else
        playlist.forEach(vid -> System.out.printf("\t%s (%s) %s\n",
                vid.getTitle(), vid.getVideoId(), this.formatVideoTags(vid.getTags())));
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if (this.retrievePlaylist(playlistName) == null)
      System.out.printf("Cannot remove video from %s: Playlist does not exist\n", playlistName);
    else {
      final List<Video> playlist = this.retrievePlaylist(playlistName).getVideos();
      if (this.videoLibrary.getVideo(videoId) == null)
        System.out.printf("Cannot remove video from %s: Video does not exist\n", playlistName);
      else {
        final Video vid = this.videoLibrary.getVideo(videoId);
        if (!playlist.contains(vid))
          System.out.printf("Cannot remove video from %s: Video is not in playlist\n", playlistName);
        else {
          playlist.remove(vid);
          System.out.printf("Removed video from %s: %s\n", playlistName, vid.getTitle());
        }
      }
    }
  }

  public void clearPlaylist(String playlistName) {
    if (this.retrievePlaylist(playlistName) == null)
      System.out.printf("Cannot clear playlist %s: Playlist does not exist\n", playlistName);
    else {
      this.retrievePlaylist(playlistName).getVideos().clear();
      System.out.printf("Successfully removed all videos from %s\n", playlistName);
    }
  }

  public void deletePlaylist(String playlistName) {
    if (this.retrievePlaylist(playlistName) == null)
      System.out.printf("Cannot delete playlist %s: Playlist does not exist\n", playlistName);
    else {
      for (String key : this.playlists.keySet()) {
        if (key.equalsIgnoreCase(playlistName))
          this.playlists.remove(key);
      }
      System.out.printf("Deleted playlist: %s\n", playlistName);
    }
  }

  /** A method to determine whether a given {@link String} corresponds to an existing tag. */
  private boolean tagExists(String t, List<String> l) {
    for (String s : l) {
      if (s.equalsIgnoreCase(t))
        return true;
    }
    return false;
  }

  private void searchVidsBy(String term, int func) {
    if (!this.videoLibrary.getVideos().isEmpty()) {
      final List<Video> vl = new ArrayList<>();
      this.videoLibrary.getVideos().forEach(v -> {
        if (func == 1) {
          if (v.getTitle().toLowerCase().contains(term.toLowerCase()))
            vl.add(v);
        } else {
            if (this.tagExists(term, v.getTags()))
              vl.add(v);
        }
      });

      if (vl.isEmpty())
        System.out.printf("No search results for %s\n", term);
      else {
        vl.sort(Comparator.comparing(Video::getTitle));
        System.out.printf("Here are the results for %s:\n", term);
        for (int i = 1; i <= vl.size(); i++)
          System.out.printf("\t%d) %s\n", i, this.formatVideo(vl.get(i - 1)));

        System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
                "If your answer is not a valid number, we will assume it's a no.");
        int num;
        try {
          num = new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
          num = 0;
        }
        if (num > 0 && num <= vl.size())
          this.playVideo(vl.get(num - 1).getVideoId());
      }
    } else
      System.out.println("No videos available");
  }

  public void searchVideos(String searchTerm) {
    this.searchVidsBy(searchTerm, 1);
  }

  public void searchVideosWithTag(String videoTag) {
    this.searchVidsBy(videoTag, 2);
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}