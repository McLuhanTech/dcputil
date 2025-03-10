package com.mcluhan.dcp.context;

import com.mcluhan.dcp.inspect.*;
import com.mcluhan.dcp.iterator.FileFindIterator;
import com.mcluhan.dcp.iterator.assetmap.AssetMapFindByNameIterator;
import com.mcluhan.dcp.iterator.assetmap.AssetMapFindIterator;
import com.mcluhan.dcp.iterator.cpl.CplFindByNameIterator;
import com.mcluhan.dcp.iterator.cpl.CplFindByTagIterator;
import com.mcluhan.dcp.iterator.cpl.CplFindIterator;
import com.mcluhan.dcp.iterator.pkl.PklFindByNameIterator;
import com.mcluhan.dcp.iterator.pkl.PklFindByTagIterator;
import com.mcluhan.dcp.iterator.pkl.PklFindIterator;

public class DcpFactory {

    static FileFindIterator assetMapIterator = new AssetMapFindIterator();
    static FileFindIterator assetMapByNameIterator = new AssetMapFindByNameIterator();

    static FileFindIterator cplIterator = new CplFindIterator();
    static FileFindIterator cplByNameIterator = new CplFindByNameIterator();
    static FileFindIterator cplByTagIterator = new CplFindByTagIterator();

    static FileFindIterator pklIterator = new PklFindIterator();
    static FileFindIterator pklByNameIterator = new PklFindByNameIterator();
    static FileFindIterator pklByTagIterator = new PklFindByTagIterator();

    static DcpVerify repeatVerify = new RepeatVerify();
    static DcpVerify cplSignVerify = new CplSignVerify();
    static DcpVerify pklSignVerify = new PklSignVerify();
    static DcpVerify hashVerify = new HashVerify();
    static DcpVerify sizeVerify = new SignVerify();
    static DcpVerify containAssetMapAllFileVerify = new ContainAssetMapAllFileVerify();
}
