package com.github.zmzhou.easyboot.common.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.github.zmzhou.easyboot.common.enums.Gender;

/**
 * 性别转换器
 * The type Gender converter.
 *
 * @author zmzhou
 * @version 1.0
 * @date 2020 /9/3 22:39
 */
public class GenderConverter implements Converter<String> {

    /**
     * 构造器
     * Instantiates a new Gender converter.
     */
    public GenderConverter() {
        // 构造器
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
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return Gender.getCode(cellData.getStringValue());
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
    public WriteCellData<String> convertToExcelData(String value, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(Gender.getZh(value));
    }
}
