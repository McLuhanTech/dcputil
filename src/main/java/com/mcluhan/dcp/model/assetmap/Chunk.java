package com.mcluhan.dcp.model.assetmap;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.NONE)
public class Chunk {

    @XmlElement(name = "Path")
    private String path;

    @XmlElement(name = "VolumeIndex")
    private String volumeIndex;
}
