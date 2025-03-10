package com.mcluhan.dcp.util;

import com.mcluhan.dcp.model.assetmap.AssetMap;
import com.mcluhan.dcp.model.cpl.CompositionPlaylist;
import com.mcluhan.dcp.model.pkl.PackingList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.crypto.*;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileReader;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

@Slf4j
public class XmlUtils {

    public static String ASSETMAP = "ASSETMAP";
    public static String CPL = "CPL";
    public static String PKL = "PKL";

    public static String XML = "XML";

    /**
     * AssetMap转换为xml
     */
    public static AssetMap assetMapXmlToJavaObject(File file) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();

            InputSource inputSource = new InputSource(new FileReader(file));
            SAXSource source = new SAXSource(reader, inputSource);

            JAXBContext context = JAXBContext.newInstance(AssetMap.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (AssetMap) unmarshaller.unmarshal(source);
        } catch (Exception e) {
            log.error("Parse AssetMap Error, file path {}", file.getAbsolutePath(), e);
            return null;
        }
    }

    public static PackingList pklXmlToJavaObject(String path) {
        return pklXmlToJavaObject(new File(path));
    }

    /**
     * PKL转换为xml
     */
    public static PackingList pklXmlToJavaObject(File file) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();

            InputSource inputSource = new InputSource(new FileReader(file));
            SAXSource source = new SAXSource(reader, inputSource);

            JAXBContext context = JAXBContext.newInstance(PackingList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return  (PackingList) unmarshaller.unmarshal(source);
        } catch (Exception e) {
            log.error("Parse PKL Error, file name {}", file.getAbsolutePath(), e);
            return null;
        }
    }

    /**
     * CPL转换为xml
     */
    public static CompositionPlaylist cplXmlToJavaObject(File file) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();

            InputSource inputSource = new InputSource(new FileReader(file));
            SAXSource source = new SAXSource(reader, inputSource);

            JAXBContext context = JAXBContext.newInstance(CompositionPlaylist.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (CompositionPlaylist) unmarshaller.unmarshal(source);
        } catch (Exception e) {
            log.error("Parse CPL Error, file name {}", file.getAbsolutePath(), e);
            return null;
        }
    }

    static boolean verifyXmlSign(File file, boolean isKdm) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            PublicKey publicKey = extractPublicKeyFromXML(doc);

            Node signatureNode = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature").item(0);
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
            DOMValidateContext valContext = new DOMValidateContext(publicKey, signatureNode);
            if (isKdm) {
                valContext.setURIDereferencer(new CustomURIDereferencer(doc));
            }

            XMLSignature signature = fac.unmarshalXMLSignature(valContext);
            return signature.validate(valContext);
        } catch (Exception e) {
            log.info("validateXml Exception", e);
        }
        return false;
    }

    private static PublicKey extractPublicKeyFromXML(Document doc) throws Exception {
        Element certElement = (Element) doc.getElementsByTagNameNS(XMLSignature.XMLNS, "X509Certificate").item(0);
        if (certElement == null) {
            throw new Exception("X509Certificate element not found in the XML document");
        }

        String base64Cert = certElement.getTextContent();
        base64Cert = base64Cert.replaceAll("\\s+", "");
        byte[] certBytes = Base64.getDecoder().decode(base64Cert);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new java.io.ByteArrayInputStream(certBytes));

        return cert.getPublicKey();
    }

    static class CustomURIDereferencer implements URIDereferencer {
        private final Document document;

        public CustomURIDereferencer(Document document) {
            this.document = document;
        }

        @Override
        public Data dereference(URIReference uriReference, XMLCryptoContext context) throws URIReferenceException {
            String uri = uriReference.getURI();
            if (uri.startsWith("#")) {
                String id = uri.substring(1);
                Element element = findElementById(document, id);
                if (element == null) {
                    throw new URIReferenceException("Cannot resolve element with ID " + id);
                }
                return new DOMSubTreeData(element, true);
            } else {
                throw new URIReferenceException("Unsupported URI: " + uri);
            }
        }

        private Element findElementById(Document doc, String id) {
            try {
                XPath xPath = XPathFactory.newInstance().newXPath();
                String expression = "//*[@*[.='" + id + "']]";
                return (Element) xPath.evaluate(expression, doc, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                log.info("findElementById Exception", e);
                return null;
            }
        }
    }

    static public class DOMSubTreeData implements NodeSetData {

        private final boolean excludeComments;
        @Getter
        private final Node root;

        public DOMSubTreeData(Node root, boolean excludeComments) {
            this.root = root;
            this.excludeComments = excludeComments;
        }

        @Override
        public Iterator<Node> iterator() {
            return new DelayedNodeIterator(root, excludeComments);
        }

        static class DelayedNodeIterator implements Iterator<Node> {
            private final Node root;
            private List<Node> nodeSet;
            private ListIterator<Node> li;
            private final boolean withComments;

            DelayedNodeIterator(Node root, boolean excludeComments) {
                this.root = root;
                this.withComments = !excludeComments;
            }

            public boolean hasNext() {
                if (nodeSet == null) {
                    nodeSet = dereferenceSameDocumentURI(root);
                    li = nodeSet.listIterator();
                }
                return li.hasNext();
            }

            public Node next() {
                if (nodeSet == null) {
                    nodeSet = dereferenceSameDocumentURI(root);
                    li = nodeSet.listIterator();
                }
                if (li.hasNext()) {
                    return li.next();
                } else {
                    throw new NoSuchElementException();
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            private List<Node> dereferenceSameDocumentURI(Node node) {
                List<Node> nodes = new ArrayList<>();
                if (node != null) {
                    nodeSetMinusCommentNodes(node, nodes, null);
                }
                return nodes;
            }

            private void nodeSetMinusCommentNodes(Node node, List<Node> nodeSet,
                                                  Node prevSibling) {
                Node pSibling = null;
                switch (node.getNodeType()) {
                    case Node.ELEMENT_NODE :
                        NamedNodeMap attrs = node.getAttributes();
                        if (attrs != null) {
                            for (int i = 0, len = attrs.getLength(); i < len; i++) {
                                nodeSet.add(attrs.item(i));
                            }
                        }
                        nodeSet.add(node);
                        for (Node child = node.getFirstChild(); child != null;
                             child = child.getNextSibling()) {
                            nodeSetMinusCommentNodes(child, nodeSet, pSibling);
                            pSibling = child;
                        }
                        break;
                    case Node.DOCUMENT_NODE:
                        for (Node child = node.getFirstChild(); child != null;
                             child = child.getNextSibling()) {
                            nodeSetMinusCommentNodes(child, nodeSet, pSibling);
                            pSibling = child;
                        }
                        break;
                    case Node.TEXT_NODE :
                    case Node.CDATA_SECTION_NODE:
                        if (prevSibling != null &&
                                (prevSibling.getNodeType() == Node.TEXT_NODE ||
                                        prevSibling.getNodeType() == Node.CDATA_SECTION_NODE)) {
                            return;
                        }
                        nodeSet.add(node);
                        break;
                    case Node.PROCESSING_INSTRUCTION_NODE :
                        nodeSet.add(node);
                        break;
                    case Node.COMMENT_NODE:
                        if (withComments) {
                            nodeSet.add(node);
                        }
                }
            }
        }
    }
}
