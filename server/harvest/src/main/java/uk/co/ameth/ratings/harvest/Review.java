package uk.co.ameth.ratings.harvest;

public class Review {

    private String platform;

    private long timestamp;

    private int rating;

    private String text;

    private String version;

    @Override
    public String toString() {
        return "Review{" +
                "platform='" + platform + '\'' +
                ", timestamp=" + timestamp +
                ", rating=" + rating +
                ", text='" + text + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public static String[] properties = new String[] {"platform", "rating", "text", "timestamp", "version"};

}
