package com.github.zmzhou.easyboot.common.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.github.zmzhou.easyboot.common.enums.UserStatus;

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
     * 构造器
     * Instantiates a new Status converter.
     */
    public StatusConverter() {
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
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        return UserStatus.getCode(cellData.getStringValue());
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
        return new WriteCellData<>(UserStatus.getDesc(value));
    }
}
