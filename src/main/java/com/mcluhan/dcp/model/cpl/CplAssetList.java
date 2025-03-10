package com.mcluhan.dcp.model.cpl;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.NONE)
public class CplAssetList {

    @XmlElement(name = "MainPicture")
    private MainPicture mainPicture;

    @XmlElement(name = "MainSound")
    private MainSound mainSound;

    @XmlElement(name = "MainSubtitle")
    private MainSubtitle mainSubtitle;
}
