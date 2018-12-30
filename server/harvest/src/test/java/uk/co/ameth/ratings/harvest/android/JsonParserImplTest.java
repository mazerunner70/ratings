package uk.co.ameth.ratings.harvest.android;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import uk.co.ameth.ratings.harvest.Review;
import uk.co.ameth.ratings.harvest.ios.RssParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(JsonParserImplTest.JsonParserConfiguration.class)
class JsonParserImplTest {


    @TestConfiguration
    static class JsonParserConfiguration {
        public JsonParserConfiguration() {
            System.out.println("1120");
        }
        @Bean
        public JsonParser jsonParser() {
            System.out.println("1123");
            return new JsonParserImpl();
        }
    }

    @Autowired
    private JsonParser jsonParser;


    @Test
    void parseJson() throws IOException {
        String jsonString = getJsonString();
        List<Review> reviews = jsonParser.parseJson(jsonString);
    }

    private String getJsonString() throws IOException {
        File file = ResourceUtils.getFile("classpath:config/and-test1.json");
        return new String(Files.readAllBytes(file.toPath()));
    }
}