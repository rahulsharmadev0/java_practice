package design_patterns.adapter;


/*
Legacy Audio Player Adapter
❓Problem:
You're building a media player. You already have an interface:
interface MediaPlayer {
    void play(String audioType, String fileName);
}

You have a basic AudioPlayer that only supports mp3 files:
class AudioPlayer implements MediaPlayer {
    public void play(String audioType, String fileName) {
        if(audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file: " + fileName);
        } else {
            // Need to handle other formats
        }
    }
}

You now have legacy audio decoders for mp4 and vlc:
class VlcPlayer {
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file: " + fileName);
    }
}

class Mp4Player {
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}

Task:
    - Implement an adapter that allows AudioPlayer to support vlc and mp4 formats.
    - You must follow the Adapter Design Pattern.
    - Do not change VlcPlayer and Mp4Player.
Concepts Involved:
    - Adapter Pattern (two-level delegation)
    - Interface-based design
    - Runtime decision logic
 */
public class AudioPlayerAdapterDemo {
    public static void main(String[] args) {
        var mp3Player = new AudioPlayerFactory("mp3");
        var mp4Player = new AudioPlayerFactory("mp4");

        mp4Player.play("one in time");
        mp3Player.play("OyaOya");
    }
}

interface MediaPlayer {
    void play(String audioType, String fileName);
}

class VlcPlayer {
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file: " + fileName);
    }
}

class Mp4Player {
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}

class Mp3Player {
    public void playMp3(String fileName) {
        System.out.println("Playing mp3 file: " + fileName);
    }
}

interface Player {
    void play(String fileName);
}
class VLcPlayerAdapter implements Player {
    final  VlcPlayer player = new VlcPlayer();

    @Override
    public void play( String fileName) {
        player.playVlc(fileName);
    }
}
class Mp4PlayerAdapter implements Player {
    final  Mp4Player player = new Mp4Player();

    @Override
    public void play(String fileName) {
        player.playMp4(fileName);
    }
}

class Mp3PlayerAdapter implements Player {
    Mp3Player player = new Mp3Player();
    @Override
    public void play(String fileName) {
        player.playMp3(fileName);
    }
}

class UnknownPlayerAdapter implements Player {
    @Override
    public void play(String fileName) {
        throw new IllegalArgumentException("Unknown Player");
    }
}

class AudioPlayerFactory implements Player {
    final Player player;

    AudioPlayerFactory(String audioType) {
        this.player = switch (audioType.toLowerCase()) {
            case "mp4" -> new Mp4PlayerAdapter();
            case "vlc" -> new VLcPlayerAdapter();
            case "mp3" ->  new Mp3PlayerAdapter();
            default -> new UnknownPlayerAdapter();
        };
    }

    @Override
    public void play(String fileName) {
        player.play(fileName);
    }
}

record AudioPlayer(Player player) implements MediaPlayer {
    @Override
    public void play(String audioType, String fileName) {
        player.play(fileName);
    }
}