import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class StaxParserStock {
    static final String STOCK = "stock";
    static final String SELLERID = "sellerId";
    static final String ITEM = "item";
    static final String ITEMID = "itemId";
    static final String PRICE = "price";
    static final String AMOUNT = "amount";

    @SuppressWarnings({"unchecked", "null"})
    public Map<Seller, Map<Item, double[]>> readConfig(String configFile) {
        Map<Seller, Map<Item, double[]>> stockInfo = new HashMap<>();
        Map<Item, double[]> itemMap = new HashMap<>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Stock stock = null;
            Seller seller = null;
            Item item = null;
            //информация о конкретном товаре - цена(0) и кол-во(1)
            double[] info = new double[2];
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart().equals(STOCK)) {
                        stock = new Stock();
                        // We read the attributes from this tag and add the date
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(SELLERID)) {
                                seller = Main.listOfSellers.get(Integer.parseInt(attribute.getValue()));
                                stock.setSeller(seller);
                            }
                        }
                    }
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart().equals(SELLERID)) {
                            event = eventReader.nextEvent();
                            seller = Main.listOfSellers.get(Integer.parseInt(event.asCharacters().getData()));
                            stock.setSeller(seller);
                            continue;
                        } else if (event.asStartElement().getName().getLocalPart()
                                .equals(ITEM)) {
                            continue;
                        } else if (event.asStartElement().getName().getLocalPart()
                                .equals(ITEMID)) {
                            event = eventReader.nextEvent();
                            item = Main.listOfItems.get(Integer.parseInt(event.asCharacters().getData()));
                            continue;
                        } else if (event.asStartElement().getName().getLocalPart()
                                .equals(PRICE)) {
                            info = new double[2];
                            event = eventReader.nextEvent();
                            info[0] = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        } else if (event.asStartElement().getName().getLocalPart()
                                .equals(AMOUNT)) {
                            event = eventReader.nextEvent();
                            info[1] = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }
                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(STOCK)) {
                        stockInfo.put(seller, itemMap);
                       // System.out.println(seller.getSurname() + " " + itemMap.toString() + itemMap.get(item));
                    } else if (endElement.getName().getLocalPart().equals(ITEM)) {
                        itemMap.put(item, info);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return stockInfo;
    }
}
