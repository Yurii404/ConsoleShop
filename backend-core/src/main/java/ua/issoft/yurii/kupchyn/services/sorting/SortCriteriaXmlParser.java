package ua.issoft.yurii.kupchyn.services.sorting;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SortCriteriaXmlParser {

    private final String fileName;

    public SortCriteriaXmlParser(String fileName) throws XMLParserException {
        try {
            this.fileName = getClass().getClassLoader().getResource(fileName).getPath();
        } catch (NullPointerException e) {
            throw new XMLParserException("Failed to find file", e);
        }

    }

    public List<SortCriteria> getCriteria() throws XMLParserException {
        List<SortCriteria> criteriaList = new ArrayList<>();

        try {
            XMLStreamReader xmlReader = getXmlReader();

            while (xmlReader.hasNext()) {
                xmlReader.next();
                if (xmlReader.isStartElement() && xmlReader.getLocalName().equals("criteria")) {
                    String field = xmlReader.getAttributeValue(null, "field");
                    String order = xmlReader.getAttributeValue(null, "order");

                    criteriaList.add(new SortCriteria(field, order));
                }
            }

        } catch (XMLParserInnerException | XMLStreamException e) {
            throw new XMLParserException("Failed parse xml criteria", e);
        }
        return criteriaList;
    }

    private XMLStreamReader getXmlReader() {
        try {
            return XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new XMLParserInnerException("Failed to create xml reader", e);
        }
    }

    private static class XMLParserInnerException extends RuntimeException {
        public XMLParserInnerException(String message) {
            super(message);
        }

        public XMLParserInnerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

