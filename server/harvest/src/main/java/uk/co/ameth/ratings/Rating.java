package uk.co.ameth.ratings;

import uk.co.ameth.ratings.harvest.Review;

import java.util.List;

public class Rating {

    public static class Builder {
        public static List<Rating> asRatings(List<Review> rssEntries) {
            return null;
        }
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    private long timeStamp;

}
