package com.mcluhan.dcp.util;

import com.mcluhan.dcp.context.DcpContext;
import com.mcluhan.dcp.inspect.DcpVerify;
import com.mcluhan.dcp.parse.Dcp;
import com.mcluhan.dcp.parse.DcpParseConfig;
import com.mcluhan.dcp.model.cpl.CplXml;
import com.mcluhan.dcp.model.pkl.PklXml;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DcpUtils {

    /**
     * 校验CPL签名
     * @param file cpl文件
     * @return true, cpl签名校验通过
     */
    public static boolean verifyCplSign(File file) {
        return XmlUtils.verifyXmlSign(file, false);
    }

    /**
     * 校验PKL签名
     * @param file pkl文件
     * @return true, pkl签名校验通过
     */
    public static boolean verifyPklSign(File file) {
        return XmlUtils.verifyXmlSign(file, false);
    }

    /**
     * 校验Kdm签名
     * @param file kdm文件
     * @return true, kdm签名校验通过
     */
    public static boolean verifyKdmSign(File file) {
        return XmlUtils.verifyXmlSign(file, true);
    }

    /**
     * 查找当前目录下的所有dcp
     * @param config 解析dcp时的一些配置
     * @return 获取输入目录下的所有合法DCP
     */
    public static List<Dcp> findDcp(DcpParseConfig config) {
        List<Dcp> dcpList = new ArrayList<>();
        if (config == null) {
            log.info("Dcp parse config is null");
            return dcpList;
        }

        String path = config.getPath();
        if (path == null || path.isEmpty()) {
            log.info("Dcp path is empty");
            config.setError("Dcp path is empty");
            return dcpList;
        }

        File file = new File(path);
        if (!file.exists()) {
            log.info("Dcp path is not exist");
            config.setError("Dcp path is not exist");
            return dcpList;
        }

        findDcp(file, new DcpContext(config), dcpList);
        return dcpList;
    }

    private static void findDcp(File file, DcpContext context, List<Dcp> dcpList) {
        String path = file.getPath();
        File[] files = file.listFiles();
        if (files != null) {
            boolean flag = false;
            for (File f : files) {
                String name = f.getName();
                if (name.equals(".") || name.equals("..")) {
                    continue;
                }

                if (f.isDirectory()) {
                    findDcp(f, context, dcpList);
                } else {
                    if (flag) {
                        continue;
                    }
                    context.setFiles(files);
                    // 1 Find AssetMap
                    context.getAssetMapFileFindIterator().findFile(path, context);
                    if (context.getAssetMapXml() != null) {
                        flag = true;
                        // 2 Find PKL
                        context.getPklFileFindIterator().findFile(path, context);

                        if (!context.getPklList().isEmpty()) {
                            // 3 Find CPL
                            context.getCplFileFindIterator().findFile(path, context);
                        }
                    }

                    for (PklXml pklXml : context.getPklList()) {
                        CplXml cplXml = pklXml.getCplXml();
                        if (cplXml != null) {
                            boolean add = false;
                            for (DcpVerify verify : context.getVerifies()) {
                                add = verify.verify(cplXml, context);
                                if (!add) {
                                    break;
                                }
                            }

                            if (add) {
                                Dcp dcp = new Dcp();
                                dcp.setPath(path);
                                dcp.setAssetMap(context.getAssetMapXml());
                                dcp.setCpl(cplXml);
                                dcp.setSize(file.length());
                                dcp.setCount(files.length);
                                dcp.setPkl(pklXml);

                                dcpList.add(dcp);
                                context.getMap().put(cplXml.getCplUuid(), dcp);
                            }
                        }
                    }

                    context.setAssetMapXml(null);
                    context.getCplList().clear();
                    context.getPklList().clear();
                    context.getUuidMappingPath().clear();
                    context.setFiles(null);
                }
            }
        }
    }
}
