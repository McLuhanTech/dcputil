package com.mcluhan.dcp.iterator.cpl;

import com.mcluhan.dcp.context.DcpContext;
import com.mcluhan.dcp.iterator.FileFindIterator;
import com.mcluhan.dcp.model.cpl.*;
import com.mcluhan.dcp.util.StrUtils;
import com.mcluhan.dcp.util.XmlUtils;
import com.mcluhan.dcp.model.pkl.PackingList;
import com.mcluhan.dcp.model.pkl.PklAsset;
import com.mcluhan.dcp.model.pkl.PklAssetList;
import com.mcluhan.dcp.model.pkl.PklXml;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link com.mcluhan.dcp.parse.DcpParseConfig#cplFindStrategy}为0时, 使用当前对象解析.
 */
public class CplFindByTagIterator extends FileFindIterator {

    @Override
    public void findFile(String path, DcpContext context) {
        this.findFileByType(path, XmlUtils.CPL, context);
        if (context.getCplList().isEmpty()) {
            this.findFileByType(path, XmlUtils.XML, context);
        }
    }

    public void findFileByType(String path, String type, DcpContext context) {
        Map<String, String> uuidMappingPath = context.getUuidMappingPath();

        for (PklXml pklXml : context.getPklList()) {
            PackingList pkl = pklXml.getPkl();
            PklAssetList assetList = pkl.getAssetList();

            if (assetList != null) {
                List<PklAsset> asset = assetList.getAsset();
                if (asset != null) {
                    for (PklAsset pklAsset : asset) {
                        String assetType = pklAsset.getType();

                        if (StrUtils.containIgnoreCase(assetType, type)) {
                            String cplPath = uuidMappingPath.get(pklAsset.getId());

                            if (cplPath != null) {
                                String fullPath = path + File.separator + cplPath;
                                File file = new File(fullPath);
                                CompositionPlaylist cpl = XmlUtils.cplXmlToJavaObject(file);

                                if (cpl != null) {
                                    CplXml cplXml = new CplXml();
                                    cplXml.setPath(path);
                                    cplXml.setFileName(cplPath);
                                    cplXml.setFullPath(fullPath);
                                    cplXml.setCplUuid(cpl.getId());
                                    cplXml.setSize(file.length());

                                    Map<String, MainPicture> pictureUuidMap = new HashMap<>();
                                    Map<String, MainSound> soundUuidMap = new HashMap<>();
                                    Map<String, MainSubtitle> subTitleUuidMap = new HashMap<>();

                                    ReelList reelList = cpl.getReelList();
                                    if (reelList != null) {
                                        List<Reel> reels = reelList.getReel();
                                        if (reels != null) {
                                            for (Reel reel : reels) {
                                                CplAssetList cplAssetList = reel.getAssetList();
                                                if (cplAssetList != null) {
                                                    MainPicture mainPicture = cplAssetList.getMainPicture();
                                                    if (mainPicture != null) {
                                                        pictureUuidMap.put(mainPicture.getId(), mainPicture);
                                                    }

                                                    MainSound mainSound = cplAssetList.getMainSound();
                                                    if (mainSound != null) {
                                                        soundUuidMap.put(mainSound.getId(), mainSound);
                                                    }

                                                    MainSubtitle mainSubtitle = cplAssetList.getMainSubtitle();
                                                    if (mainSubtitle != null) {
                                                        subTitleUuidMap.put(mainSubtitle.getId(), mainSubtitle);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (!pictureUuidMap.isEmpty()) {
                                        cplXml.setPictureUuidMap(pictureUuidMap);
                                    }
                                    if (!soundUuidMap.isEmpty()) {
                                        cplXml.setSoundUuidMap(soundUuidMap);
                                    }
                                    if (!subTitleUuidMap.isEmpty()) {
                                        cplXml.setSubTitleUuidMap(subTitleUuidMap);
                                    }

                                    pklXml.setCplXml(cplXml);
                                    context.getCplList().add(cplXml);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
