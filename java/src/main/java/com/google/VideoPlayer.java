
package com.google;

import java.util.*;

/** A class representing the video player simulator. */
public class VideoPlayer {
     private final VideoLibrary library;

     private boolean playing = false;                            // Added as part of PLAY
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
          if (!this.library.getVideos().isEmpty()) {
               final List<Video> vl = this.library.getVideos();
               vl.sort(Comparator.comparing(Video::getTitle));
               System.out.println("Here's a list of all available videos:");
               vl.forEach(v -> System.out.printf("\t%s (%s) %s\n",
                         v.getTitle(), v.getVideoId(), this.formatVideoTags(v.getTags())));
          }
     }

     public void playVideo(String id) {
          if (!this.library.getVideos().isEmpty()) {
               final Video v = this.library.getVideo(id);
               if (v == null)
                    System.out.println("Cannot play video: Video does not exist");
               else {
                    if (this.paused)
                         this.paused = false;
                    if (!this.playing)
                         this.playing = true;
                    else
                         System.out.printf("Stopping video: %s\n", this.current.getTitle());
                    this.current = v;
                    System.out.printf("Playing video: %s\n", v.getTitle());
               }
          }
     }

     public void stopVideo() {
          if (!this.playing)
               System.out.println("Cannot stop video: No video is currently playing");
          else {
               System.out.printf("Stopping video: %s\n", this.current.getTitle());
               this.playing = false;
               this.paused = true;
               this.current = null;
          }
     }

     public void playRandomVideo() {
          if (this.library.getVideos().isEmpty())
               System.out.println("No videos available");
          else {
               final int n = this.library.getVideos().size();
               this.playVideo(this.library.getVideos().get(new Random().nextInt(n)).getVideoId());
          }
     }

     public void pauseVideo() {
          if (!this.playing)
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
          if (!this.playing)
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

     /** A method added to output video data in the required format. */
     private String formatVideo(Video v) {
          return v.getTitle() + " (" + v.getVideoId() + ") " + this.formatVideoTags(v.getTags());
     }

     public void showPlaying() {
          if (!this.playing)
               System.out.println("No video is currently playing");
          else {
               if (!this.paused)
                    System.out.printf("Currently playing: %s\n", this.formatVideo(this.current));
               else
                    System.out.printf("Currently playing: %s - PAUSED\n", this.formatVideo(this.current));
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
          if (this.retrievePlaylist(name) == null)
               System.out.printf("Cannot add video to %s: Playlist does not exist\n", name);
          else {
               final List<Video> pl = this.retrievePlaylist(name).getVideos();
               if (this.library.getVideo(id) == null)
                    System.out.printf("Cannot add video to %s: Video does not exist\n", name);
               else {
                    final Video v = this.library.getVideo(id);
                    if (!pl.contains(v)) {
                         pl.add(v);
                         System.out.printf("Added video to %s: %s\n", name, v.getTitle());
                    } else
                         System.out.printf("Cannot add video to %s: Video already added\n", name);
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
          if (this.retrievePlaylist(name) == null)
               System.out.printf("Cannot show playlist %s: Playlist does not exist\n", name);
          else {
               final List<Video> pl = this.retrievePlaylist(name).getVideos();
               System.out.printf("Showing playlist: %s\n", name);
               if (pl.isEmpty())
                    System.out.println("\tNo videos here yet");
               else
                    pl.forEach(v -> System.out.printf("\t%s (%s) %s\n",
                              v.getTitle(), v.getVideoId(), this.formatVideoTags(v.getTags())));
          }
     }

     public void removeFromPlaylist(String name, String id) {
          if (this.retrievePlaylist(name) == null)
               System.out.printf("Cannot remove video from %s: Playlist does not exist\n", name);
          else {
               final List<Video> pl = this.retrievePlaylist(name).getVideos();
               if (this.library.getVideo(id) == null)
                    System.out.printf("Cannot remove video from %s: Video does not exist\n", name);
               else {
                    final Video v = this.library.getVideo(id);
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
          if (this.retrievePlaylist(name) == null)
               System.out.printf("Cannot clear playlist %s: Playlist does not exist\n", name);
          else {
               this.retrievePlaylist(name).getVideos().clear();
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

     /** A method to determine whether a given {@link String} corresponds to an existing tag. */
     private boolean tagExists(String t, List<String> l) {
          for (String s : l) {
               if (s.equalsIgnoreCase(t))
                    return true;
          }

          return false;
     }

     private void searchVidsBy(String term, int func) {
          if (!this.library.getVideos().isEmpty()) {
               final List<Video> vl = new ArrayList<>();
               this.library.getVideos().forEach(v -> {
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
          this.searchVidsBy(term, 1);
     }

     public void searchVideosWithTag(String tag) {
          this.searchVidsBy(tag, 2);
     }

     public void flagVideo(String id) {
          System.out.println("flagVideo needs implementation");
     }

     public void flagVideo(String id, String reason) {
          System.out.println("flagVideo needs implementation");
     }

     public void allowVideo(String id) {
          System.out.println("allowVideo needs implementation");
     }
}
