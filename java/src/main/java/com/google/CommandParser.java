
package com.google;

import java.util.List;

/** The class used to parse and execute a command given user input. */
public class CommandParser {
     private final VideoPlayer player;

     public CommandParser(VideoPlayer player) {
          this.player = player;
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
                    this.player.numberOfVideos();
                    break;
               case "SHOW_ALL_VIDEOS":
                    this.player.showAllVideos();
                    break;
               case "PLAY":
                    try {
                         this.player.playVideo(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println("Please enter PLAY command followed by video_id.");
                    }
                    break;
               case "PLAY_RANDOM":
                    this.player.playRandomVideo();
                    break;
               case "STOP":
                    this.player.stopVideo();
                    break;
               case "PAUSE":
                    this.player.pauseVideo();
                    break;
               case "CONTINUE":
                    this.player.continueVideo();
                    break;
               case "SHOW_PLAYING":
                    this.player.showPlaying();
                    break;
               case "CREATE_PLAYLIST":
                    try {
                         this.player.createPlaylist(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter CREATE_PLAYLIST command followed by a " +
                                             "playlist name.");
                    }
                    break;
               case "ADD_TO_PLAYLIST":
                    try {
                         this.player.addVideoToPlaylist(input.get(1), input.get(2));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter ADD_TO_PLAYLIST command followed by a "
                                             + "playlist name and video_id to add.");
                    }
                    break;
               case "REMOVE_FROM_PLAYLIST":
                    try {
                         this.player.removeFromPlaylist(input.get(1), input.get(2));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter REMOVE_FROM_PLAYLIST command followed by a "
                                             + "playlist name and video_id to remove.");
                    }
                    break;
               case "CLEAR_PLAYLIST":
                    try {
                         this.player.clearPlaylist(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter CLEAR_PLAYLIST command followed by a "
                                             + "playlist name.");
                    }
                    break;
               case "DELETE_PLAYLIST":
                    try {
                         this.player.deletePlaylist(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter DELETE_PLAYLIST command followed by a " +
                                             "playlist name.");
                    }
                    break;
               case "SHOW_PLAYLIST":
                    try {
                         this.player.showPlaylist(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println("Please enter SHOW_PLAYLIST command followed by a " +
                                   "playlist name.");
                    }
                    break;
               case "SHOW_ALL_PLAYLISTS":
                    this.player.showAllPlaylists();
                    break;
               case "SEARCH_VIDEOS":
                    try {
                         this.player.searchVideos(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println("Please enter SEARCH_VIDEOS command followed by a " +
                                   "search term.");
                    }
                    break;
               case "SEARCH_VIDEOS_WITH_TAG":
                    try {
                         this.player.searchVideosWithTag(input.get(1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         System.out.println(
                                   "Please enter SEARCH_VIDEOS_WITH_TAG command followed by a " +
                                             "video tag.");
                    }
                    break;
               case "FLAG_VIDEO":
                    try {
                         this.player.flagVideo(input.get(1), input.get(2));
                    } catch (ArrayIndexOutOfBoundsException e) {
                         try {
                              this.player.flagVideo(input.get(1));
                         } catch (ArrayIndexOutOfBoundsException f) {
                              System.out.println("Please enter FLAG_VIDEO command followed by a" +
                                        "video_id and an optional flag reason.");
                         }
                    }
                    break;
               case "ALLOW_VIDEO":
                    try {
                         this.player.allowVideo(input.get(1));
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
                              + "\tNUMBER_OF_VIDEOS - Displays the number of videos in the library.\n"
                              + "\tSHOW_ALL_VIDEOS - Lists all videos in the library.\n"
                              + "\tPLAY <video_id> - Plays a specified video from the library.\n"
                              + "\tPLAY_RANDOM - Plays a random video from the library.\n"
                              + "\tSTOP - Stops the current video.\n"
                              + "\tPAUSE - Pauses the current video.\n"
                              + "\tCONTINUE - Resumes the current video if paused.\n"
                              + "\tSHOW_PLAYING - Displays the title, URL and paused status of the current video.\n"
                              + "\tCREATE_PLAYLIST <playlist_name> - Creates a new (empty) playlist with the provided name.\n"
                              + "\tADD_TO_PLAYLIST <playlist_name> <video_id> - Adds a specified video to the named playlist.\n"
                              + "\tREMOVE_FROM_PLAYLIST <playlist_name> <video_id> - Removes a specified video from the named playlist.\n"
                              + "\tCLEAR_PLAYLIST <playlist_name> - Removes all videos from the named playlist.\n"
                              + "\tDELETE_PLAYLIST <playlist_name> - Deletes the named playlist.\n"
                              + "\tSHOW_PLAYLIST <playlist_name> - Lists all videos in the named playlist.\n"
                              + "\tSHOW_ALL_PLAYLISTS - Displays all available playlists.\n"
                              + "\tSEARCH_VIDEOS <search_term> - Displays all videos whose titles contain the provided term.\n"
                              + "\tSEARCH_VIDEOS_WITH_TAG <tag_name> - Displays all videos whose tags contains the provided tag.\n"
                              + "\tFLAG_VIDEO <video_id> <flag_reason> - Marks a specified video as flagged.\n"
                              + "\tALLOW_VIDEO <video_id> - Removes the flag from a specified video.\n"
                              + "\tHELP - Displays help information.\n"
                              + "\tEXIT - Terminates the program.\n");
     }
}
