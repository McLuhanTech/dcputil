package com.mcluhan.dcp.inspect;

import com.mcluhan.dcp.context.DcpContext;
import com.mcluhan.dcp.model.cpl.CplXml;

public interface DcpVerify {

    default boolean verify(CplXml cplXml, DcpContext context) {
        throw new RuntimeException("No implemented");
    }
}
