package com.mcluhan.dcp.inspect;

import com.mcluhan.dcp.context.DcpContext;
import com.mcluhan.dcp.parse.Dcp;
import com.mcluhan.dcp.model.cpl.CplXml;

public class RepeatVerify implements DcpVerify {

    @Override
    public boolean verify(CplXml cplXml, DcpContext context) {
        int repeat = context.getParseConfig().getRepeat();
        if (repeat == 0) {
            Dcp dcp = context.getMap().get(cplXml.getCplUuid());
            return dcp == null;
        } else if (repeat == 1) {
            throw new RuntimeException("No implemented");
        } else {
            return true;
        }
    }
}
