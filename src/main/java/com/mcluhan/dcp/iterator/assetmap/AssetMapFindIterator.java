package com.mcluhan.dcp.iterator.assetmap;

import com.mcluhan.dcp.context.DcpContext;

/**
 * {@link com.mcluhan.dcp.parse.DcpParseConfig#assetMapFindStrategy}为1时, 使用当前对象解析.
 */
public class AssetMapFindIterator extends AssetMapFindByNameIterator {

    public void findFile(String path, DcpContext context) {
        throw new RuntimeException("No implemented");
    }
}
