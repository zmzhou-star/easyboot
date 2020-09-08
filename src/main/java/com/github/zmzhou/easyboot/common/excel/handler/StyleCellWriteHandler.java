package com.github.zmzhou.easyboot.common.excel.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.github.zmzhou.easyboot.common.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * The type Style cell write handler.
 *
 * @author zmzhou
 * @version 1.0
 * @title StyleCellWriteHandler
 * @description 自定义拦截器 。Excel样式个性化设置
 * @date 2020 /8/30 21:57
 */
@Slf4j
public class StyleCellWriteHandler implements CellWriteHandler {
    /** 序号 */
    private Long rowNo = 1L;
    /** 表头标题 */
    private final String excelName;

    /**
     * Instantiates a new Style cell write handler.
     *
     * @param excelName the excel name
     */
    public StyleCellWriteHandler(String excelName) {
        this.excelName = excelName;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                 Head head, Integer integer, Integer integer1, Boolean aBoolean) {
        log.debug("beforeCellCreate");
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                Cell cell, Head head, Integer integer, Boolean aBoolean) {
        log.debug("afterCellCreate");
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                               CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        log.debug("afterCellDataConverted");
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                 List<CellData> list, Cell cell, Head head, Integer integer, Boolean isHead) {
        // 这里可以对cell进行任何操作
        Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置 水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置 垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (Boolean.FALSE.equals(isHead)) {
            // 设置边框线颜色
            short borderColor = IndexedColors.BLACK.getIndex();
            cellStyle.setLeftBorderColor(borderColor);
            cellStyle.setRightBorderColor(borderColor);
            cellStyle.setTopBorderColor(borderColor);
            cellStyle.setBottomBorderColor(borderColor);
            // 设置边框线样式
            BorderStyle borderStyle = BorderStyle.THIN;
            cellStyle.setBorderLeft(borderStyle);
            cellStyle.setBorderRight(borderStyle);
            cellStyle.setBorderTop(borderStyle);
            cellStyle.setBorderBottom(borderStyle);
            // 设置斑马线
            if (0 == cell.getRowIndex() % NumberUtils.INTEGER_TWO) {
                // 背景色
                cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                // 要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.
                // 表头默认了 FillPatternType所以可以不指定
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
            cell.setCellStyle(cellStyle);
            // 设置序号
            if (NumberUtils.INTEGER_TWO < cell.getRowIndex() && 0 == cell.getColumnIndex()
                    && StringUtils.isBlank(cell.toString())) {
                cell.setCellValue(rowNo);
                rowNo += 1;
            }
        } else if (1 == cell.getRowIndex() && 0 == cell.getColumnIndex()) {
            // 设置打印时间样式
            String date = DateFormatUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS);
            cell.setCellValue(String.format("打印时间：%s", date));
            // 设置 水平居右
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
            // 背景色
            cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headWriteFont = workbook.createFont();
            headWriteFont.setFontHeightInPoints((short) 14);
            headWriteFont.setFontName("宋体");
            headWriteFont.setBold(true);
            cellStyle.setFont(headWriteFont);
            cell.setCellStyle(cellStyle);
        } else if (0 == cell.getRowIndex() && 0 == cell.getColumnIndex()) {
            // 设置表头标题
            cell.setCellValue(excelName);
        }
    }
}
