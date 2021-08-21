
package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/** A class used to represent a video library entity. */
public class VideoLibrary {
     private final HashMap<String, Video> videos;

     public VideoLibrary() {
          this.videos = new HashMap<>();
          try {
               Scanner scanner = new Scanner(new File(this.getClass().getResource("/videos.txt").getFile()));
               while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] split = line.split("\\|");
                    String title = split[0].strip(),
                              id = split[1].strip();
                    List<String> tags;
                    if (split.length > 2)
                         tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(Collectors.toList());
                    else
                         tags = new ArrayList<>();

                    this.videos.put(id, new Video(title, id, tags));
               }
          } catch (FileNotFoundException e) {
               System.out.println("Couldn't find videos.txt");
               e.printStackTrace();
          }
     }

     /** Returns a read-only collection of videos present in the library. */
     public List<Video> getVideos() {
          return new ArrayList<>(this.videos.values());
     }

     /** Retrieves a video from the library; returns null if not found. */
     public Video getVideo(String id) {
          return this.videos.get(id);
     }
}
