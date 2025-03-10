package com.mcluhan.dcp.model.pkl;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Asset")
public class PklAsset {

    @XmlElement(name = "Id")
    protected String id;

    @XmlElement(name = "AnnotationText")
    protected String annotationText;

    @XmlElement(name = "Hash")
    protected String hash;

    @XmlElement(name = "Size")
    protected long size;

    @XmlElement(name = "Type")
    protected String type;

    @XmlElement(name = "OriginalFileName")
    protected String originalFileName;
}
