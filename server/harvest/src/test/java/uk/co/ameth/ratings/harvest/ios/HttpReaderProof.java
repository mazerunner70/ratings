package uk.co.ameth.ratings.harvest.ios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


class HttpReaderProof {


    void harvestRss() throws IOException {
        HttpReader httpReader = new HttpReader();
        httpReader.setIosUrl("https://itunes.apple.com/gb/rss/customerreviews/id=xxx/sortBy=mostRecent/xml");
        httpReader.setIosAppId("<insert app id here>");
        String result = httpReader.loadRss();
        File file = new File("rss-ios.xml");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(result);
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException {
        HttpReaderProof httpReaderProof = new HttpReaderProof();
        httpReaderProof.harvestRss();
    }
}