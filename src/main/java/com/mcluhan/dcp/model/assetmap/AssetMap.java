package com.mcluhan.dcp.model.assetmap;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "AssetMap")
public class AssetMap {

    @XmlElement(name = "Id")
    private String id;

    @XmlElement(name = "AnnotationText")
    private String annotationText;

    @XmlElement(name = "VolumeCount")
    private Integer volumeCount;

    @XmlElement(name = "IssueDate")
    private String issueDate;

    @XmlElement(name = "Issuer")
    private String issuer;

    @XmlElement(name = "Creator")
    private String creator;

    @XmlElement(name = "AssetList")
    private AssetMapAssetList assetList;
}
