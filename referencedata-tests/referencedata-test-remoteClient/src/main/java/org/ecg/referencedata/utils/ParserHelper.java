package org.ecg.referencedata.utils;

/**
 * ParserHelper
 *
 */
public class ParserHelper {

    /**
     * Funkcja wyciąga wartość pierwszego wystąpienia atrybutu namespace z xml,
     * jeżeli nie znajdzie to próbuje ustalić nazwę głównego węzła xml
     */
    public static final String parseNamespace(String xmlTest) {
        String xml = xmlTest;

        String namespace = "";
        if (xml != null) {

            try {
                if (xml.indexOf("<?xml ") != -1) {
                    xml = xml.substring(xml.indexOf("?>") + 2);
                }
                if (xml.indexOf("<") == -1) {
                    throw new Exception("brak elementu głównego, to nie jest xml");
                }

                while (xml.indexOf("<") == xml.indexOf("<!--")) {
                    if (xml.indexOf("-->") == -1) {
                        throw new Exception("brak zamknięcia komentarza, to nie jest xml");
                    }
                    xml = xml.substring(xml.indexOf("-->") + 3);
                }

                if (xml.indexOf("<") == -1) {
                    throw new Exception("brak elementu głównego, to nie jest xml");
                }

                xml = xml.substring(xml.indexOf("<"), xml.indexOf(">", xml.indexOf("<") + 1) + 1);

                xml = xml.replaceAll("[\n\t\r\\s]+", " ");

                if ((xml.indexOf(" ") == -1)) {
                    namespace = xml.substring(xml.indexOf("<") + 1, xml.indexOf(">"));
                    if (namespace.endsWith("/")) {
                        namespace = namespace.substring(0, namespace.length() - 1);
                    }
                    return namespace;
                } else {

                    String rootElementName = xml.substring(
                            xml.indexOf("<") + 1, xml.indexOf(" "));

                    String prefix = "";
                    if (rootElementName.indexOf(":") != -1) {
                        prefix = rootElementName.substring(0, rootElementName.indexOf(":"));
                        rootElementName = rootElementName.substring(rootElementName.indexOf(":"));
                    }
                    //System.out.println("RootElementName: " + rootElementName);
                    //System.out.println("RootElementPrefix: " + prefix);

                    if (prefix.length() > 0) {
                        namespace = xml.substring(xml.indexOf("xmlns:" + prefix)
                                + (6 + prefix.length()));
                        String qouta = testQouta(xml);
                        namespace = namespace.substring(namespace.indexOf(qouta) + 1);
                        namespace = namespace.substring(0, namespace.indexOf(qouta));
                        return namespace;
                    } else {

                        while (xml.indexOf(" xmlns") != -1) {
                            xml = xml.substring(xml.indexOf(" xmlns") + 6);
                            //System.out.println("XML: " + xml);
                            if (xml.indexOf(":") != -1
                                    && xml.indexOf(":") < xml.indexOf("=")) {
                                continue;
                            }

                            String qouta = testQouta(xml);
                            namespace = xml.substring(xml.indexOf(qouta) + 1);
                            namespace = namespace.substring(0, namespace.indexOf(qouta));
                            return namespace;
                        }

                        return rootElementName;
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException("Nieudana próba rozpoznania typu dokumentu - " + e.getMessage());
            }

        }

        throw new RuntimeException("Nieudana próba rozpoznania typu dokumentu.");
    }

    private static final String testQouta(String xmlFragment) {
        if (xmlFragment.indexOf("'") != -1 && ((xmlFragment.indexOf("\"") == -1)
                || (xmlFragment.indexOf("\"") != -1 && xmlFragment.indexOf("'") < xmlFragment.indexOf("\"")))) {
            return "'";
        }

        return "\"";
    }
}
