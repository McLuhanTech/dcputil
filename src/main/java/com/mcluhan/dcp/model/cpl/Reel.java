package com.mcluhan.dcp.model.cpl;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.NONE)
public class Reel {

    @XmlElement(name = "Id")
    protected String id;

    @XmlElement(name = "AssetList")
    protected CplAssetList assetList;
}
