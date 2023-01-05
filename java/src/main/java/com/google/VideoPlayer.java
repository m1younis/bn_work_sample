
package com.google;

import java.util.*;

/** The class representing the video player simulator. */
public class VideoPlayer {
    private final VideoLibrary library;

    private Video current = null;                       // Added as part of PLAY
    private boolean paused = false;                     // Added as part of PAUSE
    private final List<VideoPlaylist> playlists;        // Added as part of CREATE_PLAYLIST

    public VideoPlayer() {
        this.library = new VideoLibrary();
        this.playlists = new ArrayList<>();
    }

    /** Returns the number of videos initially available in the library. */
    public void numberOfVideos() {
        System.out.printf("%s videos in the library\n", this.library.getVideos().size());
    }

    /** Displays (by title, natural order) each {@link Video} in the library, given it is not
     * "flagged". */
    public void showAllVideos() {
        if (!this.library.getVideos().isEmpty()) {
            final List<Video> vl = this.library.getVideos();
            vl.sort(Comparator.comparing(Video::getTitle));
            System.out.println("Here's a list of all available videos:");
            vl.forEach(
                vid -> {
                    if (vid.isFlagged())
                        System.out.printf("\t%s - FLAGGED (reason: %s)\n", vid, vid.getFlag());
                    else
                        System.out.printf("\t%s\n", vid);
                }
            );
        }
    }

    /** Plays a {@link Video} given it is not "flagged". */
    public void playVideo(String id) {
        if (!this.library.getVideos().isEmpty()) {
            final Video vid = this.library.getVideo(id);
            if (vid == null)
                System.out.println("Cannot play video: Video does not exist");
            else {
                if (vid.isFlagged()) {
                    System.out.printf(
                        "Cannot play video: Video is currently flagged (reason: %s)\n",
                        vid.getFlag()
                    );
                } else {
                    if (this.current != null)
                        System.out.printf("Stopping video: %s\n", this.current.getTitle());
                    if (this.paused)
                        this.paused = false;
                    this.current = vid;
                    System.out.printf("Playing video: %s\n", vid.getTitle());
                }
            }
        }
    }

    /** Stops the current {@link Video} from playing. */
    public void stopVideo() {
        if (this.current == null)
            System.out.println("Cannot stop video: No video is currently playing");
        else {
            System.out.printf("Stopping video: %s\n", this.current.getTitle());
            this.current = null;
            this.paused = false;
        }
    }

    /** Plays a random {@link Video} given it is not "flagged". */
    public void playRandomVideo() {
        if (this.library.getVideos().isEmpty())
            System.out.println("No videos available");
        else {
            final List<String> uf = new ArrayList<>();
            this.library.getVideos().forEach(
                vid -> {
                    if (!vid.isFlagged())
                        uf.add(vid.getVideoId());
                }
            );

            if (uf.isEmpty())
                System.out.println("No videos available");
            else
                this.playVideo(uf.get(new Random().nextInt(uf.size())));
        }
    }

    /** Pauses the {@link Video} currently playing. */
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

    /** Continues the current {@link Video} given it is paused. */
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

    /** Displays the status of the {@link Video} currently playing. */
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

    /** Searches for/retrieves a {@link VideoPlaylist} by name. */
    private VideoPlaylist retrievePlaylist(String name) {
        for (VideoPlaylist pl : this.playlists) {
            if (name.equalsIgnoreCase(pl.getName()))
                return pl;
        }

        return null;
    }

    /** Creates a new {@link VideoPlaylist}, assigning it a given (unique) name. */
    public void createPlaylist(String name) {
        if (this.retrievePlaylist(name) != null) {
            System.out.println(
                "Cannot create playlist: A playlist with the same name already exists"
            );
        } else {
            this.playlists.add(new VideoPlaylist(name));
            System.out.printf("Successfully created new playlist: %s\n", name);
        }
    }

    /** Adds a {@link Video} (by ID) to a named, existing {@link VideoPlaylist} given it exists,
     * not "flagged" and not already present. */
    public void addVideoToPlaylist(String name, String id) {
        final VideoPlaylist vp = this.retrievePlaylist(name);
        if (vp == null)
            System.out.printf("Cannot add video to %s: Playlist does not exist\n", name);
        else {
            final Video vid = this.library.getVideo(id);
            if (vid == null)
                System.out.printf("Cannot add video to %s: Video does not exist\n", name);
            else {
                if (vid.isFlagged()) {
                    System.out.printf(
                        "Cannot add video to %s: Video is currently flagged (reason: %s)\n",
                        name,
                        vid.getFlag()
                    );
                } else {
                    final List<Video> pl = vp.getVideos();
                    if (!pl.contains(vid)) {
                        pl.add(vid);
                        System.out.printf("Added video to %s: %s\n", name, vid.getTitle());
                    } else
                        System.out.printf("Cannot add video to %s: Video already added\n", name);
                }
            }
        }
    }

    /** Displays (by name, natural order) each {@link VideoPlaylist} currently in the library. */
    public void showAllPlaylists() {
        if (this.playlists.isEmpty())
            System.out.println("No playlists exist yet");
        else {
            System.out.println("Showing all playlists:");
            this.playlists.sort(Comparator.comparing(VideoPlaylist::getName));
            this.playlists.forEach(pl -> System.out.printf("\t%s\n", pl.getName()));
        }
    }

    /** Displays each {@link Video} in a named, existing {@link VideoPlaylist} given it isn't
     * "flagged". */
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
                pl.forEach(
                    vid -> {
                        if (vid.isFlagged())
                            System.out.printf("\t%s - FLAGGED (reason: %s)\n", vid, vid.getFlag());
                        else
                            System.out.printf("\t%s\n", vid);
                    }
                );
            }
        }
    }

    /** Removes a {@link Video} (by its ID) from a named, existing {@link VideoPlaylist}
     * given it exists. */
    public void removeFromPlaylist(String name, String id) {
        final VideoPlaylist vp = this.retrievePlaylist(name);
        if (vp == null)
            System.out.printf("Cannot remove video from %s: Playlist does not exist\n", name);
        else {
            final Video vid = this.library.getVideo(id);
            if (vid == null)
                System.out.printf("Cannot remove video from %s: Video does not exist\n", name);
            else {
                final List<Video> pl = vp.getVideos();
                if (!pl.contains(vid)) {
                    System.out.printf(
                        "Cannot remove video from %s: Video is not in playlist\n",
                        name
                    );
                } else {
                    pl.remove(vid);
                    System.out.printf("Removed video from %s: %s\n", name, vid.getTitle());
                }
            }
        }
    }

    /** Removes all videos from a named {@link VideoPlaylist} given it exists. */
    public void clearPlaylist(String name) {
        final VideoPlaylist vp = this.retrievePlaylist(name);
        if (vp == null)
            System.out.printf("Cannot clear playlist %s: Playlist does not exist\n", name);
        else {
            vp.getVideos().clear();
            System.out.printf("Successfully removed all videos from %s\n", name);
        }
    }

    /** Removes a named {@link VideoPlaylist} from the library, given it exists. */
    public void deletePlaylist(String name) {
        if (this.retrievePlaylist(name) == null)
            System.out.printf("Cannot delete playlist %s: Playlist does not exist\n", name);
        else {
            this.playlists.removeIf(pl -> name.equalsIgnoreCase(pl.getName()));
            System.out.printf("Deleted playlist: %s\n", name);
        }
    }

    /** Searches for/retrieves a {@link Video} either by title or tag. Includes an option to play
     * once retrieved. */
    private void searchVideosBy(String term, int func) {
        if (!this.library.getVideos().isEmpty()) {
            final List<Video> vl = new ArrayList<>();
            if (func == 1) {
                this.library.getVideos().forEach(
                    vid -> {
                        if (!vid.isFlagged()) {
                            if (vid.getTitle().toLowerCase().contains(term.toLowerCase()))
                                vl.add(vid);
                        }
                    }
                );
            } else {
                this.library.getVideos().forEach(
                    vid -> {
                        if (!vid.isFlagged()) {
                            if (vid.tagExists(term))
                                vl.add(vid);
                        }
                    }
                );
            }

            if (vl.isEmpty())
                System.out.printf("No search results for %s\n", term);
            else {
                vl.sort(Comparator.comparing(Video::getTitle));
                System.out.printf("Here are the results for %s:\n", term);
                for (int i = 1; i <= vl.size(); i++)
                    System.out.printf("\t%d) %s\n", i, vl.get(i - 1));

                System.out.println(
                    "Would you like to play any of the above? If yes, specify the number of the " +
                    "video.\nIf your answer is not a valid number, we will assume it's a no."
                );

                int n;
                try {
                    n = new Scanner(System.in).nextInt();
                } catch (InputMismatchException e) {
                    n = 0;
                }
                if (n > 0 && n <= vl.size())
                    this.playVideo(vl.get(n - 1).getVideoId());
            }
        } else
            System.out.println("No videos available");
    }

    /** Calls {@link #searchVideosBy(String, int)} with a given title supplied to search for a
     * {@link Video}. */
    public void searchVideos(String term) {
        this.searchVideosBy(term, 1);
    }

    /** Calls {@link #searchVideosBy(String, int)} with a given tag supplied to search for a
     * {@link Video}. */
    public void searchVideosWithTag(String tag) {
        this.searchVideosBy(tag, 2);
    }

    /** Marks a {@link Video} (by ID) as "flagged" supplying a reason, given it exists. */
    public void flagVideo(String id, String reason) {
        if (!this.library.getVideos().isEmpty()) {
            final Video vid = this.library.getVideo(id);
            if (vid == null)
                System.out.println("Cannot flag video: Video does not exist");
            else {
                if (vid.isFlagged())
                    System.out.println("Cannot flag video: Video is already flagged");
                else {
                    if (this.current == vid)
                        this.stopVideo();
                    vid.flag(reason);
                    System.out.printf(
                        "Successfully flagged video: %s (reason: %s)\n",
                        vid.getTitle(),
                        vid.getFlag()
                    );
                }
            }
        }
    }

    /** Calls {@link #flagVideo(String, String)} given that no reason is supplied. */
    public void flagVideo(String id) {
        this.flagVideo(id, null);
    }

    /** Removes the "flag" from a {@link Video} (by ID) given it exists and is "flagged". */
    public void allowVideo(String id) {
        if (!this.library.getVideos().isEmpty()) {
            final Video vid = this.library.getVideo(id);
            if (vid == null)
                System.out.println("Cannot remove flag from video: Video does not exist");
            else {
                if (vid.isFlagged()) {
                    vid.unflag();
                    System.out.printf(
                        "Successfully removed flag from video: %s\n",
                        vid.getTitle()
                    );
                } else
                    System.out.println("Cannot remove flag from video: Video is not flagged");
            }
        }
    }
}
