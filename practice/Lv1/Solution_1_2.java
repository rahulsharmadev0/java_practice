package Lv1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Solution_1_2 {
    public static void main(String[] args) {
        Source.main(args);
    }

}

class Source {
    public static void main(String[] args) {
        List<App> apps = new ArrayList<>();
        apps.add(new App("App1", 1500, 4.5));
        apps.add(new App("App2", 800, 3.9));
        apps.add(new App("App3", 5000, 4.8));
        apps.add(new App("App4", 300, 3.7));
        apps.add(new App("App5", 20000, 4.9));

        MobileAppAnalyzer analyzer = new MobileAppAnalyzer();

        System.out.println(analyzer.filterRatings(apps));

        System.out.println(analyzer.getHighestDownloadApp(apps));

    }

}

class MobileAppAnalyzer {

    public List<App> filterRatings(List<App> apps) {
        // Use Stream API to filter rating >= 4.0 and downloads >= 1000
        return apps.stream().filter(app -> app.getRating() >= 4.0 && app.getDownloadCount() >= 1000).toList();

    }

    public Optional<App> getHighestDownloadApp(List<App> apps) {
        // Use Stream API to find the app with the max download count
        return apps.stream().max(Comparator.comparingInt(App::getDownloadCount));
    }
}

class App {

    private String name;
    private int downloadCount;
    private double rating;

    public App(String name, int downloadCount, double rating) {
        this.name = name;
        this.downloadCount = downloadCount;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "App{" +
                "name='" + name +
                ", downloadCount=" + downloadCount +
                ", rating=" + rating +
                '}';
    }
}
