package com.mcluhan.dcp.iterator.pkl;

import com.mcluhan.dcp.context.DcpContext;
import com.mcluhan.dcp.util.StrUtils;
import com.mcluhan.dcp.util.XmlUtils;
import com.mcluhan.dcp.model.pkl.PackingList;
import com.mcluhan.dcp.model.pkl.PklXml;

import java.io.File;
import java.util.List;

/**
 * {@link com.mcluhan.dcp.parse.DcpParseConfig#pklFindStrategy}为1时, 使用当前对象解析.
 */
public class PklFindByNameIterator extends PklFindByTagIterator {

    @Override
    public void findFile(String path, DcpContext context) {
        if (context.getParseConfig().isOpenEfficientFindForPKl()) {
            super.findFile(path, context);
        }

        List<PklXml> pklList = context.getPklList();

        if (pklList.isEmpty()) {
            File[] files = context.getFiles();

            for (File file : files) {
                String name = file.getName();

                if (StrUtils.containIgnoreCase(name, XmlUtils.PKL)) {
                    PackingList packingList = XmlUtils.pklXmlToJavaObject(file);

                    if (packingList != null) {
                        PklXml pklXml = new PklXml(path, name, path + File.separator + name,
                                packingList.getId(), file.length(), null, packingList,  null);
                        context.getPklList().add(pklXml);
                    }
                }
            }
        }
    }
}
