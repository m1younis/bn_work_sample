
package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video entity. */
public class Video {
     private final String title;
     private final String id;
     private final List<String> tags;

     public Video(String title, String id, List<String> tags) {
          this.title = title;
          this.id = id;
          this.tags = Collections.unmodifiableList(tags);
     }

     /** Returns the title of the video. */
     public String getTitle() {
          return this.title;
     }

     /** Returns the video id of the video. */
     public String getVideoId() {
          return this.id;
     }

     /** Returns a read-only collection of a video's tags. */
     public List<String> getTags() {
          return this.tags;
     }
}
