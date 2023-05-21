/*
 * Copyright (c) 2008-2023 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.view.component;

import org.jd.gui.api.API;
import org.jd.gui.api.model.Container;
import org.jd.gui.util.exception.ExceptionUtil;
import org.jd.gui.util.io.TextReader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class FormattedXmlFilePage extends XmlFilePage {

    public FormattedXmlFilePage(API api, Container.Entry entry) {
        super(api, entry, getFormattedXml(entry));
    }

    private static String getFormattedXml(Container.Entry entry) {
        String originalXml = TextReader.getText(entry.getInputStream());

        if (isAlreadyFormatted(originalXml)) {
            return originalXml;
        } else {
            try {
                InputSource src = new InputSource(new StringReader(originalXml));
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                transformerFactory.setAttribute("indent-number", 4);
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                Writer out = new StringWriter();
                transformer.transform(new DOMSource(document), new StreamResult(out));
                return out.toString();
            } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                assert ExceptionUtil.printStackTrace(e);
                return originalXml;
            }
        }
    }

    private static boolean isAlreadyFormatted(String originalXml) {
        return originalXml.split("\\r?\\n|\\r").length > 2;
    }
}
