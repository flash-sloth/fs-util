/*
 *  Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package top.fsfsfs.codegen.config;

import top.fsfsfs.codegen.constant.GenTypeEnum;
import top.fsfsfs.codegen.generator.GeneratorFactory;
import top.fsfsfs.codegen.template.ITemplate;
import top.fsfsfs.codegen.template.impl.EnjoyTemplate;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 模板配置。
 *
 * @author 王帅
 * @since 2023-05-17
 */
@Data
@Accessors(chain = true)
public class TemplateConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 6700855804948021101L;
    /**
     * 生成代码的模板引擎。
     */
    private ITemplate template = new EnjoyTemplate();

    /**
     * 获取生成 Entity 模板文件的位置。
     */
    public String getEntity() {
        return GeneratorFactory.getGenerator(GenTypeEnum.ENTITY).getGenType().getTemplate();
    }


    /**
     * 设置生成 Entity 模板文件的位置。
     */
    public TemplateConfig setEntityContent(String entity) {
        GeneratorFactory.getGenerator(GenTypeEnum.ENTITY).setTemplateContent(entity);
        return this;
    }

    /**
     * 获取生成 Mapper 模板文件的位置。
     */
    public String getMapper() {
        return GeneratorFactory.getGenerator(GenTypeEnum.MAPPER).getGenType().getTemplate();
    }


    /**
     * 设置生成 Mapper 模板文件的位置。
     */
    public TemplateConfig setMapperContent(String mapper) {
        GeneratorFactory.getGenerator(GenTypeEnum.MAPPER).setTemplateContent(mapper);
        return this;
    }

    /**
     * 获取生成 Service 模板文件的位置。
     */
    public String getService() {
        return GeneratorFactory.getGenerator(GenTypeEnum.SERVICE).getGenType().getTemplate();
    }


    /**
     * 设置生成 Service 模板文件的位置。
     */
    public TemplateConfig setServiceContent(String service) {
        GeneratorFactory.getGenerator(GenTypeEnum.SERVICE).setTemplateContent(service);
        return this;
    }

    /**
     * 获取生成 ServiceImpl 模板文件的位置。
     */
    public String getServiceImpl() {
        return GeneratorFactory.getGenerator(GenTypeEnum.SERVICE_IMPL).getGenType().getTemplate();
    }


    /**
     * 设置生成 ServiceImpl 模板文件的位置。
     */
    public TemplateConfig setServiceImplContent(String serviceImpl) {
        GeneratorFactory.getGenerator(GenTypeEnum.SERVICE_IMPL).setTemplateContent(serviceImpl);
        return this;
    }

    /**
     * 获取生成 Controller 模板文件的位置。
     */
    public String getController() {
        return GeneratorFactory.getGenerator(GenTypeEnum.CONTROLLER).getGenType().getTemplate();
    }

    /**
     * 设置生成 Controller 模板文件的位置。
     */
    public TemplateConfig setControllerContent(String controller) {
        GeneratorFactory.getGenerator(GenTypeEnum.CONTROLLER).setTemplateContent(controller);
        return this;
    }

    /**
     * 获取生成 TableDef 模板文件的位置。
     */
    public String getTableDef() {
        return GeneratorFactory.getGenerator(GenTypeEnum.TABLE_DEF).getGenType().getTemplate();
    }


    /**
     * 获取生成 MapperXml 模板文件的位置。
     */
    public String getMapperXml() {
        return GeneratorFactory.getGenerator(GenTypeEnum.MAPPER_XML).getGenType().getTemplate();
    }

    /**
     * 设置生成 MapperXml 模板文件的位置。
     */
    public TemplateConfig setMapperXmlContent(String mapperXml) {
        GeneratorFactory.getGenerator(GenTypeEnum.MAPPER_XML).setTemplateContent(mapperXml);
        return this;
    }

}
