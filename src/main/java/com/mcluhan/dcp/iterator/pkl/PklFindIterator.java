package com.mcluhan.dcp.iterator.pkl;

import com.mcluhan.dcp.context.DcpContext;

/**
 * {@link com.mcluhan.dcp.parse.DcpParseConfig#pklFindStrategy}为2时, 使用当前对象解析.
 */
public class PklFindIterator extends PklFindByNameIterator {

    @Override
    public void findFile(String path, DcpContext context) {
        throw new RuntimeException("No implemented");
    }
}
