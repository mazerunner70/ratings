package uk.co.ameth.ratings.db.repositories;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;
import uk.co.ameth.ratings.db.model.ReviewEntry;
import uk.co.ameth.ratings.harvest.ios.RssParser;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000",
        "amazon.aws.accessKey=testaccesskey",
        "amazon.aws.secretkey=testsecretkey"
})
class ReviewRepositoryTest {



    private DynamoDBMapper dynamoDBMapper;

    @TestConfiguration
    static class ReviewRepositoryConfiguration {
        public ReviewRepositoryConfiguration() {
            System.out.println("1120");
        }
        @Value("${amazon.dynamodb.endpoint}")
        private String amazonDynamoDbEndpoint;

        @Value("${amazon.aws.accessKey}")
        private String amazonAwsAccessKey;

        @Value("${amazon.aws.secretkey}")
        private String amazonAwsSecretKey;
        @Bean
        public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAwsCredentials());
        if (!StringUtils.isEmpty(amazonDynamoDbEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDbEndpoint);
        }
            return amazonDynamoDB;
        }
        @Bean
        public AWSCredentials amazonAwsCredentials() {
            return new BasicAWSCredentials(amazonAwsAccessKey, amazonAwsSecretKey);
        }

    }


    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    ReviewRepository reviewRepository;

    private static DynamoDBProxyServer dynamoDBProxyServer;

    @BeforeAll
    public static void setupDb() throws Exception {
        System.setProperty("sqlite4java.library.path", "native-libs");
        String port = "8000";
        dynamoDBProxyServer = ServerRunner.createServerFromCommandLineArgs(
                new String[] {"-inMemory", "-port", port}
        );
        dynamoDBProxyServer.start();
    }

    @AfterAll
    public static void tearDownDb() throws Exception {
        dynamoDBProxyServer.stop();
    }

    @BeforeEach
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(ReviewEntry.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L)
        );
        amazonDynamoDB.createTable(tableRequest);
    }

    @Test
    public void test1() {
        ReviewEntry reviewEntry = new ReviewEntry();
        reviewEntry.setPlatform("and");
        reviewEntry.setRating(5);
        reviewEntry.setText("Hello World");
        reviewEntry.setTimestamp(1234456578L);
        reviewRepository.save(reviewEntry);
        List<ReviewEntry> result = (List<ReviewEntry>)reviewRepository.findAll();
        assertTrue(result.size()>0);
        assertEquals(result.size(), 1);
        System.out.println(reviewEntry.toString());

    }

}