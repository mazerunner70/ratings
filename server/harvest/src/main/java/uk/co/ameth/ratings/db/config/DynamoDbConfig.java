package uk.co.ameth.ratings.db.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import uk.co.ameth.ratings.db.repositories.ReviewRepository;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = ReviewRepository.class)
public class DynamoDbConfig {


    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDbEndpoint;

    @Value("${amazon.aws.accessKey}")
    private String amazonAwsAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAwsSecretKey;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        System.out.println("Initiaising db1:");
        System.out.println("endpoint:"+amazonDynamoDbEndpoint);

//        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAwsCredentials());
//        if (!StringUtils.isEmpty(amazonDynamoDbEndpoint)) {
//            amazonDynamoDB.setEndpoint(amazonDynamoDbEndpoint);
//        }
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(amazonDynamoDbEndpoint, "eu-west-1"))
                .build();
        System.out.println("Initiaised db1");
        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAwsCredentials() {
        return new BasicAWSCredentials(amazonAwsAccessKey, amazonAwsSecretKey);
    }


    @Bean(name="mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector handlerMappingIntrospector() {
        return new HandlerMappingIntrospector((applicationContext));
    }

}
