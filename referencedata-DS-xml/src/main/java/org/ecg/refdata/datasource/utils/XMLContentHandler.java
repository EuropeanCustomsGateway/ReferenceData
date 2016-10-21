package org.ecg.refdata.datasource.utils;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Namespace ignoring content handler for SAX.
 *
 */
public class XMLContentHandler implements ContentHandler {

    private ContentHandler delegate;

    public XMLContentHandler(ContentHandler delegate) {
        this.delegate = delegate;
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        delegate.characters(ch, start, length);
    }

    /**
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException {
        delegate.endDocument();
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        delegate.endElement(uri, localName, name);
    }

    /**
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    public void endPrefixMapping(String prefix) throws SAXException {
        delegate.endPrefixMapping(prefix);
    }

    /**
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
        delegate.ignorableWhitespace(ch, start, length);
    }

    /**
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
     * java.lang.String)
     */
    public void processingInstruction(String target, String data)
            throws SAXException {
        delegate.processingInstruction(target, data);
    }

    /**
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator(Locator locator) {
        delegate.setDocumentLocator(locator);
    }

    /**
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    public void skippedEntity(String name) throws SAXException {
        delegate.skippedEntity(name);
    }

    /**
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException {
        delegate.startDocument();
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String name,
            Attributes atts) throws SAXException {
        // ignore uri intentionally
        delegate.startElement("", localName, name, atts);
    }

    /**
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
     * java.lang.String)
     */
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        delegate.startPrefixMapping(prefix, uri);
    }

}
