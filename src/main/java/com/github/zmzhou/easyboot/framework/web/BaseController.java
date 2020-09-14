package com.github.zmzhou.easyboot.framework.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.github.zmzhou.easyboot.common.utils.DateUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.vo.PageParams;

/**
 * @description web层通用数据处理
 * @author zmzhou
 * @date 2020/07/02 16:59
 */
public class BaseController {

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }
    /**
     * 组装分页参数
     * @param params 查询参数
     * @return Pageable
     * @author zmzhou
     * @date 2020/07/09 11:31
     */
    protected Pageable getPageable(PageParams params) {
        if (null == params) {
            return PageRequest.of(0, 10);
        }
        Sort sort = Sort.unsorted();
        if (StringUtils.isNotBlank(params.getProp())) {
            Sort.Direction direct = Sort.Direction.ASC;
            if (StringUtils.isNotBlank(params.getOrder())) {
                direct = Sort.Direction.fromString(params.getOrder().replace("ending", ""));
            }
            sort = Sort.by(direct, params.getProp());
        }
        return PageRequest.of(params.getPageNum() - 1, params.getPageSize(), sort);
    }
    
    /**
     * 组装返回结果
     * @param data 返回数据
     * @return ApiResult<T>
     * @author zmzhou
     * @date 2020/07/07 14:06
     */
    protected <T> ApiResult<T> ok(Object data) {
        ApiResult<T> result = new ApiResult<>();
        result.setData((T) data);
        if (data instanceof Page) {
            TableDataInfo dataInfo = new TableDataInfo();
            dataInfo.setRows(((Page)data).getContent());
            dataInfo.setTotal(((Page)data).getTotalElements());
            result.setData((T) dataInfo);
        }
        return result;
    }
}
