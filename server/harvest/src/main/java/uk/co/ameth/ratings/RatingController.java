package uk.co.ameth.ratings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.ameth.ratings.harvest.Review;
import uk.co.ameth.ratings.harvest.ios.RssParser;

import java.util.List;

@Component
public class RatingController {


    @Autowired
    RssParser rssParser;

    public void processRatings() {
        processIosRatings();
    }

    private void processIosRatings() {
        Rating rating = getLatestRating();
        List<Rating> ratingList = loadLatestRatings();
        List<Rating> newRatingsList = removeRatingsBeforeDate(ratingList, rating.getTimeStamp());
        updateDb(newRatingsList);
    }

    private void updateDb(List<Rating> newRatingsList) {

    }

    private List<Rating> removeRatingsBeforeDate(List<Rating> ratingList, long timeStamp) {
        return null;
    }

    private List<Rating> loadLatestRatings() {
        List<Review> rssEntries = rssParser.loadRssEntries();
        return Rating.Builder.asRatings(rssEntries);
    }

    private Rating getLatestRating() {
        return null;
    }


}
