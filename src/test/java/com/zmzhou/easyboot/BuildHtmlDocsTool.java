package com.zmzhou.easyboot;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * @title BuildHtmlDocsTool
 * @description 生成API文档
 * @author zmzhou
 * @version 1.0
 * @date 2020/8/30 12:44
 */
public final class BuildHtmlDocsTool {
    /**
     * @description
     * @param args 参数
     * @author zmzhou
     * @date 2020/8/30 12:53
     */
    public static void main(String[] args) {
        buildHtmlDocs();
    }

    /**
     * @description 生成API文档
     * @author zmzhou
     * @date 2020/8/30 12:59
     */
    public static void buildHtmlDocs(){
        DocsConfig config = new DocsConfig();
        // 项目根目录
        config.setProjectPath("D:\\develop\\easyboot");
        config.setProjectName("EasyBoot");
        config.setApiVersion("V1.0");
        // 生成API 文档所在目录
        config.setDocsPath("docs");
        // 配置自动生成
        config.setAutoGenerate(Boolean.TRUE);
        // 执行生成文档
        Docs.buildHtmlDocs(config);
    }
}
