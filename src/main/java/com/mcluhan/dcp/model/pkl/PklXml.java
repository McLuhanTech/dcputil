package com.mcluhan.dcp.model.pkl;

import com.mcluhan.dcp.model.cpl.CplXml;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PklXml {

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
     * pkl uuid
     */
    private String pklUuid;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件哈希
     */
    private String hash;

    private PackingList pkl;

    /**
     * PKL对象的cpl
     */
    private CplXml cplXml;
}
