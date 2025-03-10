package com.mcluhan.dcp.model.assetmap;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.NONE)
public class AssetMapAssetList {

    @XmlElement(name = "Asset")
    private List<AssetMapAsset> assetList;
}
