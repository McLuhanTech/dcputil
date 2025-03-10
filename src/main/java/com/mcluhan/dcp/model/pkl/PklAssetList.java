package com.mcluhan.dcp.model.pkl;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "AssetList")
public class PklAssetList {

	@XmlElement(name = "Asset")
	protected List<PklAsset> asset;
}
