package uk.co.ameth.ratings.harvest.ios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import uk.co.ameth.ratings.harvest.Review;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssParser {

    @Autowired
    HttpReader httpReader;

    public List<Review> loadRssEntries() {
        String payload = httpReader.loadRss();
        return asRssEntries(payload);
    }

    public List<Review> asRssEntries(String xmlPayload) throws RuntimeException {
        Element element = null;
        try {
            element = getRootElement(xmlPayload);
            System.out.println("112"+ element.getNodeName()+": "+element.getNodeType()+": "+element.getChildNodes());
            System.out.println("113"+ element.getChildNodes().item(0));
            NodeList nodeList = element.getElementsByTagName("entry");
            List<Review> reviews = new ArrayList<>();
            // Parses 2018-12-27T14:13:02-07:00
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            for (int f = 0; f<nodeList.getLength(); ++f) {
                Node entryNode = nodeList.item(f);
                Node node = entryNode.getFirstChild();
                Review review = new Review();
                review.setPlatform("ios");
                while (node != null) {
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        String nodeName = node.getNodeName();
//                        System.out.println(nodeName+":"+node.getNodeType()+"::"+node.getTextContent());
                        switch(nodeName) {
                            case "updated":
                                review.setTimestamp(simpleDateFormat.parse(node.getTextContent()).getTime());
                                break;
                            case "im:rating":
                                review.setRating(Integer.parseInt(node.getTextContent()));
                                break;
                            case "im:version":
                                review.setVersion(node.getTextContent());
                                break;
                            case "content":
                                if (node.getAttributes().getNamedItem("type").getTextContent().equals("text")) {
                                    review.setText(node.getTextContent().replaceAll("\n", " "));
                                }
                                break;
                        }
                    }
                    node = node.getNextSibling();
                }
                System.out.println("-----"+f+"):"+ review);
                reviews.add(review);
            }
            return reviews;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse rss doc1", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse rss doc2", e);
        } catch (SAXException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse rss doc3", e);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to parse date", e);
        }
    }

    private Element getRootElement(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));
        document.getDocumentElement().normalize();
        return document.getDocumentElement();
    }
}
