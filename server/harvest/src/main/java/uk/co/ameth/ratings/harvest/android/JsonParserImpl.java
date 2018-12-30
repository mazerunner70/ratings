package uk.co.ameth.ratings.harvest.android;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import uk.co.ameth.ratings.harvest.Review;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class JsonParserImpl implements JsonParser {

    @Override
    public List<Review> parseJson(String jsonString) throws IOException {
        JsonNode jsonNode = getRootNode(jsonString);
        return extractReviews(jsonNode);
    }

    private List<Review> extractReviews(@NotNull JsonNode jsonNode) {
//        System.out.println("Child node count"+jsonNode.size());
        Iterator<JsonNode> iterator = jsonNode.elements();
        List<Review> reviews = new ArrayList<>();
        while (iterator.hasNext()) {
            reviews.add(node2Review(iterator.next()));
        }
        return reviews;
    }

    private Review node2Review(JsonNode jsonNode) {
//        System.out.println("Node: "+jsonNode);
        Review review = new Review();
        review.setRating(jsonNode.get("Star Rating").asInt());
        review.setText(jsonNode.get("Review Text").asText());
        review.setTimestamp(jsonNode.get("Review Submit Millis Since Epoch").asLong());
        review.setVersion(jsonNode.get("App Version Name").asText());
        review.setPlatform("and");
//        System.out.println("review: "+review.toString());
        return review;
    }

    private JsonNode getRootNode(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonString);
    }

}
