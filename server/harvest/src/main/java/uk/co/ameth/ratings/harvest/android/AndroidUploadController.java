package uk.co.ameth.ratings.harvest.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import uk.co.ameth.ratings.db.ReviewService;
import uk.co.ameth.ratings.harvest.Review;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableWebMvc
public class AndroidUploadController {

    @Autowired
    JsonParser jsonParser;

    @Autowired
    ReviewService reviewService;

    @RequestMapping(path = "/review/android/upload", method = RequestMethod.POST)
    public Map<String, Object> androidReviewUpload(@RequestBody String body) throws IOException {
        Map<String, Object> pong = new HashMap<>();
        List<Review> reviews = jsonParser.parseJson(body);
        reviewService.saveReviews(reviews);
        Map<String, String> sublevel = new HashMap<>();
        sublevel.put("Greeting", "Hello, World!" );
        sublevel.put("request", body );

        pong.put("pong", sublevel);
        return pong;
    }
}
