package com.mcluhan.dcp.iterator.cpl;

import com.mcluhan.dcp.context.DcpContext;

/**
 * {@link com.mcluhan.dcp.parse.DcpParseConfig#cplFindStrategy}为1时, 使用当前对象解析.
 */
public class CplFindByNameIterator extends CplFindByTagIterator {

    @Override
    public void findFile(String path, DcpContext context) {
        throw new RuntimeException("No implemented");
    }
}
