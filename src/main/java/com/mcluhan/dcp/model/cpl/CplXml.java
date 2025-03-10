package com.mcluhan.dcp.model.cpl;

import lombok.Data;

import java.util.Map;

@Data
public class CplXml {

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
     * cpl uuid
     */
    private String cplUuid;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件哈希
     */
    private String hash;

    // ========== MainPicture ==========
    /**
     * key MainPicture Uuid
     * value MainPicture
     */
    private Map<String, MainPicture> pictureUuidMap;

    // ========== MainSound ==========
    /**
     * key MainSound Uuid
     * value MainSound
     */
    private Map<String, MainSound> soundUuidMap;

    // ========== MainSubtitle ==========
    /**
     * key MainSubtitle Uuid
     * value MainSubtitle
     */
    private Map<String, MainSubtitle> subTitleUuidMap;
}
