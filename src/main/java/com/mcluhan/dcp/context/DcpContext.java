package com.mcluhan.dcp.context;

import com.mcluhan.dcp.inspect.DcpVerify;
import com.mcluhan.dcp.iterator.FileFindIterator;
import com.mcluhan.dcp.parse.Dcp;
import com.mcluhan.dcp.parse.DcpParseConfig;
import com.mcluhan.dcp.model.assetmap.AssetMapXml;
import com.mcluhan.dcp.model.cpl.CplXml;
import com.mcluhan.dcp.model.pkl.PklXml;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DcpContext {

    private DcpParseConfig parseConfig;

    private FileFindIterator assetMapFileFindIterator;
    private FileFindIterator cplFileFindIterator;
    private FileFindIterator pklFileFindIterator;

    private File[] files;

    /**
     * AssetMap内容映射
     * key uuid
     * value path
     */
    private Map<String, String> uuidMappingPath = new HashMap<>();

    private AssetMapXml assetMapXml;
    private List<CplXml> cplList = new ArrayList<>();
    private List<PklXml> pklList = new ArrayList<>();

    /**
     * 重复判断DCP是否重复
     * key cpl.uuid
     * value dcp
     */
    private Map<String, Dcp> map;

    private List<DcpVerify> verifies = new ArrayList<>();

    public DcpContext(DcpParseConfig parseConfig) {
        this.parseConfig = parseConfig;

        if (parseConfig.getAssetMapFindStrategy() == 0) {
            assetMapFileFindIterator = DcpFactory.assetMapByNameIterator;
        } else {
            assetMapFileFindIterator = DcpFactory.assetMapIterator;
        }

        if (parseConfig.getCplFindStrategy() == 0) {
            cplFileFindIterator = DcpFactory.cplByTagIterator;
        } else if (parseConfig.getCplFindStrategy() == 1){
            cplFileFindIterator = DcpFactory.cplByNameIterator;
        } else {
            cplFileFindIterator = DcpFactory.cplIterator;
        }

        if (parseConfig.getPklFindStrategy() == 0) {
            pklFileFindIterator = DcpFactory.pklByTagIterator;
        } else if (parseConfig.getPklFindStrategy() == 1) {
            pklFileFindIterator = DcpFactory.pklByNameIterator;
        } else {
            pklFileFindIterator = DcpFactory.pklIterator;
        }

        if (parseConfig.getRepeat() == 0 || parseConfig.getRepeat() == 1) {
            if (parseConfig.isThreads()) {
                map = new ConcurrentHashMap<>();
            } else {
                map = new HashMap<>();
            }
        }

        verifies.add(DcpFactory.repeatVerify);
        if (parseConfig.isContainAssetMapAllFile()) {
            verifies.add(DcpFactory.containAssetMapAllFileVerify);
        }

        if (parseConfig.isVerifySize()) {
            verifies.add(DcpFactory.sizeVerify);
        }

        if (parseConfig.isVerifyCplSign()) {
            verifies.add(DcpFactory.cplSignVerify);
        }
        if (parseConfig.isVerifyPklSign()) {
            verifies.add(DcpFactory.pklSignVerify);
        }

        if (parseConfig.isVerifyHash()) {
            verifies.add(DcpFactory.hashVerify);
        }
    }
}
