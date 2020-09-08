package com.github.zmzhou.easyboot.common.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * The type Gender converter.
 *
 * @author zmzhou
 * @version 1.0
 * @title GenderConverter
 * @description 性别转换器
 * @date 2020 /9/3 22:39
 */
public class GenderConverter implements Converter<String> {

    /**
     * Instantiates a new Gender converter.
     */
    public GenderConverter() {
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
        if ("女".equals(cellData.getStringValue())) {
            return "0";
        } else if ("男".equals(cellData.getStringValue())) {
            return "1";
        }
        return "2";
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
        if ("1".equals(value)) {
            return new CellData<>("男");
        } else if ("2".equals(value)) {
            return new CellData<>("未知");
        }
        return new CellData<>("女");
    }
}
