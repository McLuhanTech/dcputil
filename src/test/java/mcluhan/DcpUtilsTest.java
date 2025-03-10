package mcluhan;

import com.mcluhan.dcp.parse.Dcp;
import com.mcluhan.dcp.parse.DcpParseConfig;
import com.mcluhan.dcp.util.DcpUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class DcpUtilsTest {

    /**
     * 获取指定目录下的所有DCP
     */
    @Test
    public void testDcpFind() {
        DcpParseConfig config = new DcpParseConfig("E:\\Dcp");
        List<Dcp> dcp = DcpUtils.findDcp(config);
        System.out.println(dcp.size());
    }

    /**
     * CPL签名校验
     */
    @Test
    public void cplValidSign() {
        System.out.println(DcpUtils.verifyCplSign(new File("E:\\Dcp\\CNX_Channel-ID_TST_C_EN-XX_INT_51-HI-VI_2K_CNX_20200512_EBL_IOP_OV\\CPL_CNX_Channel-ID_TST_C_EN-XX_INT_51-HI-VI_2K_CNX_20200512_EBL_IOP_OV.xml")));
    }

    /**
     * PKL签名校验
     */
    @Test
    public void pklValidSign() {
        System.out.println(DcpUtils.verifyPklSign(new File("E:\\Dcp\\CNX_Channel-ID_TST_C_EN-XX_INT_51-HI-VI_2K_CNX_20200512_EBL_IOP_OV\\PKL_4c7884bc-e19e-42cc-977b-9f9dca56206a.xml")));
    }

    /**
     * KDM签名校验
     */
    @Test
    public void kdmValidSign() {
        DcpUtils.verifyKdmSign(new File("E:\\Dcp\\1.kdm"));
    }
}
