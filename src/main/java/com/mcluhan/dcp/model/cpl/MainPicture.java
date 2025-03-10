package com.mcluhan.dcp.model.cpl;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.NONE)
public class MainPicture {

    @XmlElement(name = "Id")
    private String id;

    @XmlElement(name = "EditRate")
    private String editRate;

    @XmlElement(name = "IntrinsicDuration")
    private Integer intrinsicDuration;

    @XmlElement(name = "EntryPoint")
    private Integer entryPoint;

    @XmlElement(name = "Duration")
    private Integer duration;

    @XmlElement(name = "Hash")
    private String hash;

    @XmlElement(name = "FrameRate")
    private String frameRate;

    @XmlElement(name = "ScreenAspectRatio")
    private String screenAspectRatio;

    @XmlElement(name = "KeyId")
    private String keyId;
}
