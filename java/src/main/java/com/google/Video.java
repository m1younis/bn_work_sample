
package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video entity. */
public class Video {
     private final String title;
     private final String id;
     private final List<String> tags;

     private boolean flagged = false;                            // Added as part of FLAG_VIDEO
     private String reason = null;                               // Added as part of FLAG_VIDEO

     public Video(String title, String id, List<String> tags) {
          this.title = title;
          this.id = id;
          this.tags = Collections.unmodifiableList(tags);
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

     /** Returns the video's "flagged" status. */
     public boolean isFlagged() {
          return this.flagged;
     }

     /** Returns the reason for which the video has been flagged. */
     public String getFlag() {
          return this.reason;
     }

     /** Marks the video as "flagged" with a given reason, if supplied. */
     public void flag(String reason) {
          this.flagged = true;
          if (reason == null)
               this.reason = "Not supplied";
          else
               this.reason = reason;
     }
}
