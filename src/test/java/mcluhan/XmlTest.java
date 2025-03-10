package mcluhan;

import com.mcluhan.dcp.model.assetmap.AssetMap;
import com.mcluhan.dcp.model.cpl.CompositionPlaylist;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.FileReader;

public class XmlTest {

    @Test
    public void kdmXmlToJavaObject() {

    }

    @Test
    public void pklXmlToJavaObject() {

    }

    @Test
    public void cplXmlToJavaObject() {
        try {
            // 创建 JAXBContext
            JAXBContext context = JAXBContext.newInstance(CompositionPlaylist.class);

            // 创建 Unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 创建 SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // 创建 XMLReader
            XMLReader reader = factory.newSAXParser().getXMLReader();

            // 创建 SAXSource
            InputSource inputSource = new InputSource(new FileReader("C:\\Users\\Administrator\\Desktop\\vf-2\\MOVIE PENGANTIN IBLIS\\CPL_9b7dfd36-0eba-4235-9762-cb25d20c8204.xml"));
            SAXSource source = new SAXSource(reader, inputSource);

            // 解析 XML
            CompositionPlaylist playlist = (CompositionPlaylist) unmarshaller.unmarshal(source);

            System.out.println("==" + playlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void assetMapXmlToJavaObject() {
        try {
            // 创建 JAXBContext
            JAXBContext context = JAXBContext.newInstance(AssetMap.class);

            // 创建 Unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 创建 SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // 创建 XMLReader
            XMLReader reader = factory.newSAXParser().getXMLReader();

            // 创建 SAXSource
            InputSource inputSource = new InputSource(new FileReader("C:\\Users\\Administrator\\Desktop\\dcp_player_installer\\DCPs\\CNX_Channel-ID_TST_C_EN-XX_INT_51-HI-VI_2K_CNX_20200512_EBL_IOP_OV\\CNX_Channel-ID_TST_C_EN-XX_INT_51-HI-VI_2K_CNX_20200512_EBL_IOP_OV\\ASSETMAP"));
            SAXSource source = new SAXSource(reader, inputSource);

            // 解析 XML
            AssetMap assetMap = (AssetMap) unmarshaller.unmarshal(source);

            System.out.println("==" + assetMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
