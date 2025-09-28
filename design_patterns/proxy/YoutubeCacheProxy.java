package design_patterns.proxy;

import java.util.*;

record Video(String id, String title, int duration) {
}

interface YoutubeService {
    public List<Video> getTrendingVideo() throws InterruptedException;
}

class YoutubeServiceImpl implements YoutubeService {

    @Override
    public List<Video> getTrendingVideo() throws InterruptedException {
        Thread.sleep(120);
        return List.of(
                new Video("xd2d2", "New Song 2027", 23));
    }

}

public class YoutubeCacheProxy implements YoutubeService {
    final YoutubeService service;
    List<Video> cache;

    YoutubeCacheProxy(YoutubeService service) {
        this.service = service;
    }

    @Override
    public List<Video> getTrendingVideo() {
        if (cache != null)
            return cache;
        try {
            return cache = service.getTrendingVideo();
        } catch (Exception e) {
            throw new IllegalArgumentException("Not present");
        }

    }

}
