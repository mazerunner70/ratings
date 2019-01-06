package uk.co.ameth.ratings.harvest.ios;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class HttpReader {

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public void setIosAppId(String iosAppId) {
        System.out.println("setting"+iosAppId);this.iosAppId = iosAppId;
    }

    @Value("${IOS_URL}")
    private String iosUrl;

    @Value("${IOS_APP_ID}")
    private String iosAppId;

    private CloseableHttpClient httpClient;

    public HttpReader() {
        httpClient = HttpClients.createDefault();
    }


    public String loadRss() {
        System.out.println("iosId:"+iosAppId);
        String fullUrl = iosUrl.replaceFirst("xxx", iosAppId);
        System.out.println("Loading from url "+fullUrl);
        HttpGet httpGet = new HttpGet(fullUrl);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("Response status line: "+httpResponse.getStatusLine());
            HttpEntity httpEntity = httpResponse.getEntity();
            return asString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String asString(HttpEntity httpEntity) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        httpEntity.writeTo(byteArrayOutputStream);
        String string = byteArrayOutputStream.toString();
        String unescapedString = StringEscapeUtils.unescapeXml(string);
        String reescapedString = unescapedString.replaceAll("&", "&amp;");
        byteArrayOutputStream.close();
        return reescapedString;
    }

}
