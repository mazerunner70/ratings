package uk.co.ameth.ratings.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.co.ameth.ratings.db.model.ReviewEntry;
import uk.co.ameth.ratings.db.repositories.ReviewRepository;
import uk.co.ameth.ratings.harvest.Review;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public void saveReviews(List<Review> reviews) {
        for (Review review : reviews) {
            ReviewEntry reviewEntry = ReviewEntry.asReviewEntry(review);
            List<ReviewEntry> reviewEntries = reviewRepository.findByPlatformAndTimestamp(reviewEntry.getPlatform(), reviewEntry.getTimestamp());
            if (reviewEntries.size() == 0) {
                reviewRepository.save(reviewEntry);
            }
        }
    }

}
