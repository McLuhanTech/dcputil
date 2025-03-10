package com.mcluhan.dcp.model.assetmap;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.NONE)
public class AssetMapAsset {

    @XmlElement(name = "Id")
    private String id;

    @XmlElement(name = "PackingList")
    private String packingList;

    @XmlElement(name = "ChunkList")
    private ChunkList chunkList;
}
