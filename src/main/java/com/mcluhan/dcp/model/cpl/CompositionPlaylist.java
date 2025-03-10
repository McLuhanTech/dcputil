package com.mcluhan.dcp.model.cpl;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "CompositionPlaylist")
public class CompositionPlaylist {

    @XmlElement(name = "Id")
    private String id;

    @XmlElement(name = "AnnotationText")
    private String annotationText;

    @XmlElement(name = "IssueDate")
    private String issueDate;

    @XmlElement(name = "Issuer")
    private String Issuer;

    @XmlElement(name = "Creator")
    private String creator;

    @XmlElement(name = "ContentTitleText")
    private String contentTitleText;

    @XmlElement(name = "ContentKind")
    private String contentKind;

    @XmlElement(name = "ReelList")
    private ReelList reelList;
}
