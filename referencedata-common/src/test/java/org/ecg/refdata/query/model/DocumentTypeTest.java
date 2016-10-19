package org.ecg.refdata.query.model;

import org.ecg.refdata.query.model.impl.DocumentTypeImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DocumentTypeTest {

    public DocumentTypeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void serializationTest() {

        DocumentType documentType = new DocumentTypeImpl("sa", true, true, "Ads");

        byte[] ser = serialize(documentType);
        Object o = deserialize(ser);

    }

    /**
     * Method comming from org.ecg.tools.object.ObjectSerializer
     *
     * Deserialize Object from a byte array
     *
     * @param opData - bytes array for operation data
     * @return Object
     */
    public static final Object deserialize(byte[] opData) {
        Object Object = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new ByteArrayInputStream(opData));
            Object = (Object) in.readObject();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (ClassNotFoundException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                //ignore
            }
        }
        return Object;
    }

    /**
     * Method comming from org.ecg.tools.object.ObjectSerializer
     *
     * Serialize Object to a byte array
     *
     * @param Object - operation data for serialization (should contains only
     * initialized objects)
     * @return array bytes from Object
     */
    public static final byte[] serialize(Object Object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(Object);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                //ignore
            }
        }
        return bos.toByteArray();
    }
}
