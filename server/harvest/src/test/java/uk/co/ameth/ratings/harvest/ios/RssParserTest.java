package uk.co.ameth.ratings.harvest.ios;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;
import uk.co.ameth.ratings.harvest.Review;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(RssParserTest.RssReaderConfiguration.class)
class RssParserTest {

    @TestConfiguration
    static class RssReaderConfiguration {
        public RssReaderConfiguration() {
            System.out.println("1120");
        }
        @Bean
        public RssParser rssReader() {
            System.out.println("1123");
            return new RssParser();
        }
    }

    @Autowired
    private RssParser rssParser;

    @MockBean
    private HttpReader httpReader;

    @Test
    void asRssEntries() throws IOException, ParserConfigurationException, SAXException {
        System.out.println("1126");
        System.out.println("current directory: "+System.getProperty("user.dir"));
        System.out.println(rssParser);
        File file = ResourceUtils.getFile("classpath:config/rss-ios.xml");
        String xmlString =  new String(Files.readAllBytes(file.toPath()));
        List<Review> rssEntries = rssParser.asRssEntries(xmlString);
        assertNotNull(rssEntries);
    }
}