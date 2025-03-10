package com.mcluhan.dcp.model.assetmap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetMapXml {

    /**
     * 所在目录
     */
    private String path;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 全局全名, 所在目录 + 文件名
     */
    private String fullPath;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * AssetMap解析的java对象
     */
    private AssetMap assetMap;
}
