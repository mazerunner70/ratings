package uk.co.ameth.ratings.db.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import uk.co.ameth.ratings.harvest.Review;

import java.util.Objects;

@DynamoDBTable(tableName = "Reviews")
public class ReviewEntry {

    private String id;

    private String platform;

    private long timestamp;

    private int rating;

    private String text;

    private String version;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @DynamoDBAttribute
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @DynamoDBAttribute
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @DynamoDBAttribute
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewEntry)) return false;
        ReviewEntry that = (ReviewEntry) o;
        return getTimestamp() == that.getTimestamp() &&
                getRating() == that.getRating() &&
                getId().equals(that.getId()) &&
                getPlatform().equals(that.getPlatform()) &&
                Objects.equals(getText(), that.getText()) &&
                getVersion().equals(that.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlatform(), getTimestamp(), getRating(), getText(), getVersion());
    }

    @Override
    public String toString() {
        return "ReviewEntry{" +
                "id='" + id + '\'' +
                ", platform='" + platform + '\'' +
                ", timestamp=" + timestamp +
                ", rating=" + rating +
                ", text='" + text + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public static ReviewEntry asReviewEntry(Review review) {
        ReviewEntry reviewEntry = new ReviewEntry();
        reviewEntry.setTimestamp(review.getTimestamp());
        reviewEntry.setText(review.getText());
        reviewEntry.setRating(review.getRating());
        reviewEntry.setPlatform(review.getPlatform());
        reviewEntry.setVersion(review.getVersion());
        return reviewEntry;
    }
}
