package com.github.zmzhou.easyboot.common.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.github.zmzhou.easyboot.common.enums.SuccessStatus;

/**
 * 成功/失败转换器
 * @title SuccessConverter
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/13 23:22
 */
public class SuccessConverter implements Converter<String> {

    /**
     * Instantiates a new Status converter.
     */
    public SuccessConverter() {
    }

    /**
     * Support java type key class.
     *
     * @return the class
     */
    @Override
    public Class<String> supportJavaTypeKey() {
        return String.class;
    }

    /**
     * Support excel type key cell data type enum.
     *
     * @return the cell data type enum
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * Convert to java data string.
     *
     * @param cellData            the cell data
     * @param contentProperty     the content property
     * @param globalConfiguration the global configuration
     * @return the string
     */
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                GlobalConfiguration globalConfiguration) {
        return SuccessStatus.getCode(cellData.getStringValue());
    }

    /**
     * Convert to excel data cell data.
     *
     * @param value               the value
     * @param contentProperty     the content property
     * @param globalConfiguration the global configuration
     * @return the cell data
     */
    @Override
    public CellData<String> convertToExcelData(String value, ExcelContentProperty contentProperty,
               GlobalConfiguration globalConfiguration) {
        return new CellData<>(SuccessStatus.getDesc(value));
    }
}
