package uk.co.ameth.ratings.harvest.ios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import uk.co.ameth.ratings.db.ReviewService;
import uk.co.ameth.ratings.harvest.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@EnableWebMvc
public class IosPollController {


    @Autowired
    RssParser rssParser;

    @Autowired
    ReviewService reviewService;

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public Map<String, String> ping() {
        Map<String, String> pong = new HashMap<>();
        String iosAppId = "--";
        System.out.println("7685");
        iosAppId = System.getenv("IOS_APP_ID");
        List<Review> reviews = rssParser.loadRssEntries();
        reviewService.saveReviews(reviews);
        pong.put("pong", "Hello, World!"+ iosAppId);
        return pong;
    }
}
