package com.github.zmzhou.easyboot.common.excel.converters;

import org.apache.commons.lang3.math.NumberUtils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * The type Status converter.
 *
 * @author zmzhou
 * @version 1.0
 * @title StatusConverter
 * @description 状态转换器
 * @date 2020 /9/3 22:38
 */
public class StatusConverter implements Converter<String> {

    /**
     * Instantiates a new Status converter.
     */
    public StatusConverter() {
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
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if ("禁用".equals(cellData.getStringValue())){
            return "0";
        }
        return "1";
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
    public CellData<String> convertToExcelData(String value,
    ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (String.valueOf(NumberUtils.INTEGER_ONE).equals(value)) {
            return new CellData<>("启用");
        }
        return new CellData<>("禁用");
    }
}
