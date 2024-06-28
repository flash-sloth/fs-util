/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.fsfsfs.codegen.generator.impl;

import cn.hutool.core.util.StrUtil;
import top.fsfsfs.codegen.config.DtoConfig;
import top.fsfsfs.codegen.config.GlobalConfig;
import top.fsfsfs.codegen.config.PackageConfig;
import top.fsfsfs.codegen.constant.GenTypeEnum;
import top.fsfsfs.codegen.entity.Table;
import top.fsfsfs.codegen.generator.IGenerator;
import com.mybatisflex.core.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import top.fsfsfs.basic.utils.StrPool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO（写入方法入参） 生成器。
 *
 * @author tangyh
 * @since 2024年06月18日15:48:18
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class DtoGenerator implements IGenerator {

    private String templateContent;
    private GenTypeEnum genType;

    public DtoGenerator() {
        this(GenTypeEnum.DTO);
    }

    public DtoGenerator(GenTypeEnum genType) {
        this.genType = genType;
    }

    @Override
    public String getFilePath(Table table, GlobalConfig globalConfig, boolean absolute) {
        PackageConfig packageConfig = globalConfig.getPackageConfig();
        DtoConfig config = globalConfig.getDtoConfig();
        String layerPackage = packageConfig.getDtoPackage();
        String sourceDir = config.getSourceDir();


        String path = "";
        if (absolute) {
            path = StringUtil.isNotBlank(sourceDir) ? sourceDir : packageConfig.getSourceDir();
            if (!path.endsWith(File.separator)) {
                path += File.separator;
            }
        }

        path += StrPool.SRC_MAIN_JAVA + File.separator;
        path += layerPackage.replace(StrPool.DOT, StrPool.SLASH) + File.separator;
        path += table.buildDtoClassName() + StrPool.DOT_JAVA;
        return path;
    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        if (!globalConfig.isDtoGenerateEnable()) {
            return;
        }

        PackageConfig packageConfig = globalConfig.getPackageConfig();
        DtoConfig dtoConfig = globalConfig.getDtoConfig();

        String packagePath = getFilePath(table, globalConfig, true);
        File javaFile = new File(packagePath);

        if (javaFile.exists() && !dtoConfig.getOverwriteEnable()) {
            return;
        }

        Map<String, Object> params = buildParam(table, globalConfig, packageConfig, dtoConfig);

        log.info("Dto ---> {}", javaFile);
        if (StrUtil.isNotEmpty(templateContent)) {

            globalConfig.getTemplateConfig().getTemplate().generate(params, templateContent, javaFile);
        } else {

            globalConfig.getTemplateConfig().getTemplate().generate(params, genType.getTemplate(), javaFile);
        }
    }

    private static Map<String, Object> buildParam(Table table, GlobalConfig globalConfig, PackageConfig packageConfig, DtoConfig dtoConfig) {
        Map<String, Object> params = new HashMap<>(7);
        params.put("table", table);
        params.put("dtoPackageName", packageConfig.getDtoPackage());
        params.put("dtoConfig", dtoConfig);
        params.put("dtoClassName", table.buildDtoClassName());
        params.put("javadocConfig", globalConfig.getJavadocConfig());
        params.put("packageConfig", packageConfig);
        params.put("globalConfig", globalConfig);
        return params;
    }

    @Override
    public String preview(Table table, GlobalConfig globalConfig) {
        PackageConfig packageConfig = globalConfig.getPackageConfig();
        DtoConfig dtoConfig = globalConfig.getDtoConfig();

        Map<String, Object> params = buildParam(table, globalConfig, packageConfig, dtoConfig);

        if (StrUtil.isNotEmpty(templateContent)) {
            return globalConfig.getTemplateConfig().getTemplate().previewByContent(params, templateContent);
        }
        return globalConfig.getTemplateConfig().getTemplate().previewByFile(params, this.genType.getTemplate());
    }
}