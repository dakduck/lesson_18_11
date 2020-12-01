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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StaxParserSale {
    static final String SALE = "sale";
    static final String ID = "id";
    static final String SELLERID = "sellerId";
    static final String ITEMID = "itemId";
    static final String AMOUNT = "amount";
    static final String DATE = "date";

    @SuppressWarnings({ "unchecked", "null" })
    public LinkedList<Sale> readConfig(String configFile) {
        LinkedList<Sale> items = new LinkedList<>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Sale item = null;
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart().equals(SALE)) {
                        item = new Sale();
                        // We read the attributes from this tag and add the date
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(ID)) {
                                item.setId(Integer.parseInt(attribute.getValue()));
                            }
                        }
                    }
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(SELLERID)) {
                            event = eventReader.nextEvent();
                            item.setSeller(Main.listOfSellers.get(Integer.parseInt(event.asCharacters().getData())));
                            continue;
                        }
                    }
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ITEMID)) {
                            event = eventReader.nextEvent();
                            item.setItem(Main.listOfItems.get(Integer.parseInt(event.asCharacters().getData())));
                            continue;
                        }
                    }
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(AMOUNT)) {
                            event = eventReader.nextEvent();
                            item.setAmount(Integer.parseInt(event.asCharacters().getData()));
                            continue;
                        }
                    }
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(DATE)) {
                            event = eventReader.nextEvent();
                            String[] data = event.asCharacters().getData().split("\\.");
                            item.setDate(LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0])));
                            continue;
                        }
                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(SALE)) {
                        items.add(item);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }
}
