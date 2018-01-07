package org.aikodi.rejuse.io.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * A class of objects that read from an XML stream and create objects.
 *
 * @param <R> The type of object created by this reader.
 */
public interface XMLReader<R> {

   /**
    * Read 1 object from the given reader. The reader is advanced
    * until the closing tag of the returned object has been read.
    *
    * @param reader The reader from which the object is created.
    * @return The created object.
    * @throws XMLStreamException An exception was thrown while parsing
    * the XML stream.
    */
   R read(XMLStreamReader reader) throws XMLStreamException;
}
