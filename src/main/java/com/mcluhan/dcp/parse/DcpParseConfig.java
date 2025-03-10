package com.mcluhan.dcp.parse;

import com.mcluhan.dcp.model.assetmap.AssetMap;
import com.mcluhan.dcp.model.cpl.CompositionPlaylist;
import com.mcluhan.dcp.model.pkl.PackingList;
import lombok.Data;

import java.util.Set;

@Data
public class DcpParseConfig {

    /**
     * dcp路径
     */
    private String path;

    /**
     * false 单线程查找
     * true 多线程查找
     */
    private boolean threads = false;

    /**
     * 遇到重复DCP的处理，通过CPL文件的uuid来判断dcp是否重复。
     * 0, 结果不会重复, 发现有重复的直接跳过. 即结果返回第一个遇到的DCP
     * 1, 结果不会重复, 结果返回最后一个DCP
     * 2, 结果会重复, 重复的也会返回到结果中
     */
    private int repeat = 0;

    // ========== AssetMap查找配置 ==========
    /**
     * AssetMap查找策略
     * 0 默认配置, 将文件名中包含ASSETMAP的文件映射为{@link AssetMap}
     * 1 将指定的文件映射为{@link AssetMap},
     *      可以配合{@link #mappingAssetMapFileSuffix} {@link #notMappingAssetMapFileSuffix}使用
     */
    private int assetMapFindStrategy = 0;

    /**
     * {@link #assetMapFindStrategy}为1时有效。
     * 1 默认为空, 即将文件映射为ASSETMAP时, 只会将两个情况的文件映射为ASSETMAP.
     *      1 文件名中不包含.的文件，即文件名为 ASSETMAP
     *      2 文件名以.xml结尾的文件，即文件名为 ASSETMAP.xml
     * 2 如果ASSETMAP文件有其他的格式,，则可以往assetMapFileSuffix中添加值，如需要将ASSETMAP.TXT
     *  这个文件也映射为{@link AssetMap }
     *  则, 需要往assetMapFileSuffix中加入txt.
     */
    private Set<String> mappingAssetMapFileSuffix;

    /**
     * {@link #assetMapFindStrategy}为1时有效
     */
    private Set<String> notMappingAssetMapFileSuffix;

    /**
     * 高效查找.
     * true, 默认值, 如当{@link #assetMapFindStrategy}为1时, 先使用0进行查找, 有结果直接返回; 没有结果在使用1查找
     */
    private boolean openEfficientFindForAssetMap = true;

    // ========== cpl查找配置 ==========
    /**
     * CPL查找策略
     * 0 使用标签查找
     * 1 将文件名中包含CPL的文件尝试解析为{@link CompositionPlaylist}
     * 2 将指定文件尝试解析为{@link CompositionPlaylist}, 可以配合{@link #mappingCplFileSuffix}
     *      {@link #notMappingCplFileSuffix}使用
     */
    private int cplFindStrategy = 0;

    private Set<String> mappingCplFileSuffix;

    private Set<String> notMappingCplFileSuffix;

    /**
     * 高效查找.
     * true, 默认值, 如当{@link #cplFindStrategy}为2时, 先使用0进行查找, 有结果直接返回; 没有结果在使用1查找, 还是没有结果才使用2
     */
    private boolean openEfficientFindForCpl = true;


    // ========== pkl查找配置 ==========
    /**
     * PKL查找策略
     * 0 使用标签查找
     * 1 将文件名中包含PKL的文件尝试解析为{@link PackingList}
     * 2 将指定文件尝试解析为{@link PackingList}, 可以配合{@link #mappingPklFileSuffix}
     *      {@link #notMappingPklFileSuffix}使用
     */
    private int pklFindStrategy = 0;

    private Set<String> mappingPklFileSuffix;

    private Set<String> notMappingPklFileSuffix;

    /**
     * 高效查找.
     * true, 默认值, 如当{@link #pklFindStrategy}为2时, 先使用0进行查找, 有结果直接返回; 没有结果在使用1查找, 还是没有结果才使用2
     */
    private boolean openEfficientFindForPKl = true;

    // ========== 校验 ==========
    /**
     * false, 不验证cpl签名
     * true, cpl签名验证成功, 才认为其他一个合理的cpl
     */
    private boolean verifyCplSign = false;

    /**
     * false, 不验证pkl签名
     * true, pkl签名验证成功, 才认为其他一个合理的pkl
     */
    private boolean verifyPklSign = false;

    /**
     * false, 默认情况下目录有ASSETMAP CPL PKL则就会认为其为一个dcp
     * true, 只有当目录包含了ASSETMAP中定义的所有文件, 才认为其为一个dcp
     */
    private boolean containAssetMapAllFile = false;

    /**
     * false 不验证size
     * true 会验证size（和PKL中的size进行比较）
     */
    private boolean verifySize = false;

    /**
     * false 不验证hash
     * true 会验证hash（和PKL中的hash进行比较）
     */
    private boolean verifyHash = false;

    // ========== 错误信息 ==========
    private String error;

    private DcpParseConfig() {}

    public DcpParseConfig(String path) {
        this.path = path;
    }
}
