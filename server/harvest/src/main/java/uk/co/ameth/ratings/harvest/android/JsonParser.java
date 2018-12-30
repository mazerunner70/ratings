package uk.co.ameth.ratings.harvest.android;

import org.springframework.stereotype.Service;
import uk.co.ameth.ratings.harvest.Review;

import java.io.IOException;
import java.util.List;

public interface JsonParser {

    List<Review> parseJson(String csvString) throws IOException;

}
