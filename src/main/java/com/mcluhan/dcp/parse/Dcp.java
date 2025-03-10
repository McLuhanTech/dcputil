package com.mcluhan.dcp.parse;

import com.mcluhan.dcp.model.assetmap.AssetMapXml;
import com.mcluhan.dcp.model.cpl.CplXml;
import com.mcluhan.dcp.model.pkl.PklXml;
import lombok.Data;

@Data
public class Dcp {

    /**
     * Dcp路径
     */
    private String path;

    /**
     * Dcp中的AssetMap(唯一)
     */
    private AssetMapXml assetMap;

    /**
     * CPL
     */
    private CplXml cpl;

    /**
     * PKL
     */
    private PklXml pkl;

    /**
     * 整个文件夹大小
     */
    private Long size;

    /**
     * 包含的文件数量
     */
    private Integer count;
}
