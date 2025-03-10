package com.mcluhan.dcp.model.pkl;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Data
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "PackingList")
public class PackingList {

    @XmlElement(name = "Id")
    private String id;

    @XmlElement(name = "AnnotationText")
    private String annotationText;

    @XmlElement(name = "IssueDate")
    private String issueDate;

    @XmlElement(name = "Issuer")
    private String issuer;

    @XmlElement(name = "Creator")
    private String creator;

    @XmlElement(name = "AssetList")
    private PklAssetList assetList;
}
