package com.mcluhan.dcp.iterator.pkl;

import com.mcluhan.dcp.context.DcpContext;
import com.mcluhan.dcp.iterator.FileFindIterator;
import com.mcluhan.dcp.model.assetmap.*;
import com.mcluhan.dcp.util.XmlUtils;
import com.mcluhan.dcp.model.pkl.PackingList;
import com.mcluhan.dcp.model.pkl.PklXml;

import java.io.File;
import java.util.List;

/**
 * {@link com.mcluhan.dcp.parse.DcpParseConfig#pklFindStrategy}为0时, 使用当前对象解析.
 */
public class PklFindByTagIterator extends FileFindIterator {

    @Override
    public void findFile(String path, DcpContext context) {
        AssetMap assetMap = context.getAssetMapXml().getAssetMap();
        AssetMapAssetList assetList = assetMap.getAssetList();

        if (assetList != null) {
            List<AssetMapAsset> asset = assetList.getAssetList();

            if (asset != null) {
                for (AssetMapAsset assetMapAsset : asset) {
                    ChunkList chunkList = assetMapAsset.getChunkList();

                    if (chunkList != null) {
                        List<Chunk> chunks = chunkList.getChunks();
                        if (chunks != null && !chunks.isEmpty()) {
                            Chunk chunk = chunks.get(0);
                            String p = chunk.getPath();
                            String fileFullPath = path + File.separator + p;

                            context.getUuidMappingPath().put(assetMapAsset.getId(), p);

                            String packingList = assetMapAsset.getPackingList();
                            if (packingList != null) {
                                File file = new File(fileFullPath);
                                PackingList pkl = XmlUtils.pklXmlToJavaObject(file);
                                if (pkl != null) {
                                    PklXml pklXml = new PklXml(path, p, fileFullPath,
                                            pkl.getId(), file.length(), null, pkl, null);
                                    context.getPklList().add(pklXml);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
