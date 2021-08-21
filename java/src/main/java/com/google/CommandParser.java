
package com.google;

import java.util.List;

/** The class used to parse and execute a command given user input. */
public class CommandParser {
     private final VideoPlayer videoPlayer;

     public CommandParser(VideoPlayer videoPlayer) {
          this.videoPlayer = videoPlayer;
     }

     /**
      * Executes the given user command.
      */
     public void executeCommand(List<String> input) {
          if (input.isEmpty()) {
               System.out.println(
                         "Please enter a valid command, " +
                                   "type HELP for a list of available commands.");
               return;
          }

          switch (input.get(0).toUpperCase()) {
               case "NUMBER_OF_VIDEOS":
                    this.videoPlayer.numberOfVideos();
                    break;
               case "SHOW_ALL_VIDEOS":
                    this.videoPlayer.showAllVideos();
                    break;
               case "PLAY":
                    try {
                         this.videoPlayer.playVideo(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println("Please enter PLAY command followed by video_id.");
                    }
                    break;
               case "PLAY_RANDOM":
                    this.videoPlayer.playRandomVideo();
                    break;
               case "STOP":
                    this.videoPlayer.stopVideo();
                    break;
               case "PAUSE":
                    this.videoPlayer.pauseVideo();
                    break;
               case "CONTINUE":
                    this.videoPlayer.continueVideo();
                    break;
               case "SHOW_PLAYING":
                    this.videoPlayer.showPlaying();
                    break;
               case "CREATE_PLAYLIST":
                    try {
                         this.videoPlayer.createPlaylist(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter CREATE_PLAYLIST command followed by a " +
                                             "playlist name.");
                    }
                    break;
               case "ADD_TO_PLAYLIST":
                    try {
                         this.videoPlayer.addVideoToPlaylist(input.get(1), input.get(2));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter ADD_TO_PLAYLIST command followed by a "
                                             + "playlist name and video_id to add.");
                    }
                    break;
               case "REMOVE_FROM_PLAYLIST":
                    try {
                         this.videoPlayer.removeFromPlaylist(input.get(1), input.get(2));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter REMOVE_FROM_PLAYLIST command followed by a "
                                             + "playlist name and video_id to remove.");
                    }
                    break;
               case "CLEAR_PLAYLIST":
                    try {
                         this.videoPlayer.clearPlaylist(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter CLEAR_PLAYLIST command followed by a "
                                             + "playlist name.");
                    }
                    break;
               case "DELETE_PLAYLIST":
                    try {
                         this.videoPlayer.deletePlaylist(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter DELETE_PLAYLIST command followed by a " +
                                             "playlist name.");
                    }
                    break;
               case "SHOW_PLAYLIST":
                    try {
                         this.videoPlayer.showPlaylist(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println("Please enter SHOW_PLAYLIST command followed by a " +
                                   "playlist name.");
                    }
                    break;
               case "SHOW_ALL_PLAYLISTS":
                    this.videoPlayer.showAllPlaylists();
                    break;
               case "SEARCH_VIDEOS":
                    try {
                         this.videoPlayer.searchVideos(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println("Please enter SEARCH_VIDEOS command followed by a " +
                                   "search term.");
                    }
                    break;
               case "SEARCH_VIDEOS_WITH_TAG":
                    try {
                         this.videoPlayer.searchVideosWithTag(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter SEARCH_VIDEOS_WITH_TAG command followed by a " +
                                             "video tag.");
                    }
                    break;
               case "FLAG_VIDEO":
                    try {
                         this.videoPlayer.flagVideo(input.get(1), input.get(2));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         try {
                              this.videoPlayer.flagVideo(input.get(1));
                         } catch (ArrayIndexOutOfBoundsException f) {
                              System.out.println("Please enter FLAG_VIDEO command followed by a" +
                                        "video_id and an optional flag reason.");
                         }
                    }
                    break;
               case "ALLOW_VIDEO":
                    try {
                         this.videoPlayer.allowVideo(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println("Please enter ALLOW_VIDEO command followed by a " +
                                   "video_id.");
                    }
                    break;
               case "HELP":
                    this.getHelp();
                    break;
               default:
                    System.out.println(
                              "Please enter a valid command, type HELP for a list of "
                                        + "available commands.");
                    break;
          }
     }

     /**
      * Displays all available commands to the user.
      */
     private void getHelp() {
          System.out.println("Available commands:\n"
                              + "\tNUMBER_OF_VIDEOS - Shows how many videos are in the library.\n"
                              + "\tSHOW_ALL_VIDEOS - Lists all videos from the library.\n"
                              + "\tPLAY <video_id> - Plays specified video.\n"
                              + "\tPLAY_RANDOM - Plays a random video from the library.\n"
                              + "\tSTOP - Stop the current video.\n"
                              + "\tPAUSE - Pause the current video.\n"
                              + "\tCONTINUE - Resume the current paused video.\n"
                              + "\tSHOW_PLAYING - Displays the title, url and paused status of the video that is currently playing (or paused).\n"
                              + "\tCREATE_PLAYLIST <playlist_name> - Creates a new (empty) playlist with the provided name.\n"
                              + "\tADD_TO_PLAYLIST <playlist_name> <video_id> - Adds the requested video to the playlist.\n"
                              + "\tREMOVE_FROM_PLAYLIST <playlist_name> <video_id> - Removes the specified video from the specified playlist\n"
                              + "\tCLEAR_PLAYLIST <playlist_name> - Removes all the videos from the playlist.\n"
                              + "\tDELETE_PLAYLIST <playlist_name> - Deletes the playlist.\n"
                              + "\tSHOW_PLAYLIST <playlist_name> - List all the videos in this playlist.\n"
                              + "\tSHOW_ALL_PLAYLISTS - Display all the available playlists.\n"
                              + "\tSEARCH_VIDEOS <search_term> - Display all the videos whose titles contain the search_term.\n"
                              + "\tSEARCH_VIDEOS_WITH_TAG <tag_name> -Display all videos whose tags contains the provided tag.\n"
                              + "\tFLAG_VIDEO <video_id> <flag_reason> - Mark a video as flagged.\n"
                              + "\tALLOW_VIDEO <video_id> - Removes a flag from a video.\n"
                              + "\tHELP - Displays help.\n"
                              + "\tEXIT - Terminates the program execution.\n");
     }
}
