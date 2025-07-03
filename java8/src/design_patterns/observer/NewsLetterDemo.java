package design_patterns.observer;

import java.util.HashMap;
import java.util.HashSet;

public class NewsLetterDemo {
    public static void main(String[] args) {
        News news = new News();
        news.subscribe("rahul", new EmailNewsObserver());
        news.subscribe("raju", new SMSNewsObserver());

        news.setNews("India win football game");
        news.setNews("USA involve in War with Russia");
        news.unsubscribe("raju");
        news.setNews("Amazon apply 70% on mobiles");
    }
}



interface NewsObserver {
    void update(String news);
}

class EmailNewsObserver implements NewsObserver {
    @Override
    public void update(String news) {
        System.out.println("Email News: " + news);
    }
}

class SMSNewsObserver implements NewsObserver {
    @Override
    public void update(String news) {
        System.out.println("SMS News: " + news);
    }
}

class News {
    private String news;
    final HashMap<String,NewsObserver> subscribed = new HashMap<>();

    void setNews(String news){
        this.news=news;
        for (String name : subscribed.keySet())
            subscribed.get(name).update(getNews() + " ::["+ name +"]");
    }


    void subscribe(String id,NewsObserver observer){
        subscribed.put(id, observer);
    }

    void unsubscribe(String id){
        subscribed.remove(id);
    }

    public String getNews() {
        return news;
    }
}