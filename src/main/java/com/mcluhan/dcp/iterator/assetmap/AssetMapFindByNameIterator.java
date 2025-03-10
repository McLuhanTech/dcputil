package com.mcluhan.dcp.iterator.assetmap;

import com.mcluhan.dcp.context.DcpContext;
import com.mcluhan.dcp.iterator.FileFindIterator;
import com.mcluhan.dcp.util.XmlUtils;
import com.mcluhan.dcp.model.assetmap.AssetMap;
import com.mcluhan.dcp.model.assetmap.AssetMapXml;
import com.mcluhan.dcp.util.StrUtils;

import java.io.File;

/**
 * {@link com.mcluhan.dcp.parse.DcpParseConfig#assetMapFindStrategy}为0时, 使用当前对象解析.
 */
public class AssetMapFindByNameIterator extends FileFindIterator {

    @Override
    public void findFile(String path, DcpContext context) {
        File[] files = context.getFiles();
        for (File file : files) {
            String name = file.getName();

            if (StrUtils.containIgnoreCase(name, XmlUtils.ASSETMAP)) {
                AssetMap assetMap = XmlUtils.assetMapXmlToJavaObject(file);

                if (assetMap != null) {
                    AssetMapXml assetMapXml = new AssetMapXml(path, name, path + File.separator + name, file.length(), assetMap);
                    context.setAssetMapXml(assetMapXml);
                    return;
                }
            }
        }
    }
}
