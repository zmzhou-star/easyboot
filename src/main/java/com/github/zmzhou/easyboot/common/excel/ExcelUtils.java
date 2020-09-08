package com.github.zmzhou.easyboot.common.excel;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.handler.StyleCellWriteHandler;
import com.github.zmzhou.easyboot.common.excel.listener.UploadDataListener;
import com.github.zmzhou.easyboot.common.utils.DateUtils;
import com.github.zmzhou.easyboot.common.utils.FileUtil;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

import static com.alibaba.excel.EasyExcelFactory.write;

/**
 * @title ExcelUtils
 * @description excel导入导出工具类
 * @author zmzhou
 * @version 1.0
 * @date 2020/8/30 21:53
 */
@Slf4j
@Component
public final class ExcelUtils {
    /** 魔法值：param */
    private static final String KEY_PARAM = "param";
    /** 魔法值：data */
    private static final String KEY_DATA = "data";
    /** 魔法值：excelClass */
    private static final String KEY_EXCEL_CLASS = "excelClass";
    /**
     * 文件下载路径
     */
    @Value("${server.file-path-download}")
    private String downloadPath;
    /**
     * excel文件下载
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link BaseExcel}
     * <p>
     * 2. 直接写即可
     * <p>
     * 3. 调用/common/download接口下载excel
     * @param list excel数据
     * @param excelClass excel类名
     * @param excelName excel文件名,表头名
     */
    public String download(List<? extends BaseExcel> list, Class<? extends BaseExcel> excelClass, String excelName) {
        // 文件名加上时间戳，避免重名
        String fileName = SecurityUtils.getUsername() + Constants.SEPARATOR + excelName + Constants.UNDERLINE
                + DateFormatUtils.format(new Date(), DateUtils.FILE_NAME_PATTERN) + ExcelTypeEnum.XLSX.getValue();
        String filePath = downloadPath + fileName;
        FileUtil.existsAndMkdirs(downloadPath + SecurityUtils.getUsername());
        log.info("filePath:{}", filePath);
        // 表头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 修改背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, new WriteCellStyle());
        // 把生成的Excel文件路径设置返回对象
        write(filePath, excelClass).registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new StyleCellWriteHandler(excelName)).sheet(excelName).doWrite(list);
        return fileName;
    }

    /**
     * 读取excel数据
     * @param is 输入流
     * @param excelClass excel导入的模板类
     * @param list 保存数据的集合
     * @author zmzhou
     * @date 2020/9/6 18:03
     */
    public void simpleRead(InputStream is, Class<? extends BaseExcel> excelClass,
                           List<? extends BaseExcel> list) {
        UploadDataListener<BaseExcel> listener = new UploadDataListener<>((List<BaseExcel>) list);
        EasyExcelFactory.read(is, excelClass, listener).sheet().doRead();
    }
}
