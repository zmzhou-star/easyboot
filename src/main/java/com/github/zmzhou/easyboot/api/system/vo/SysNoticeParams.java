package com.github.zmzhou.easyboot.api.system.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通知公告信息请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-19 14:15:47
 */
@Data
@ApiModel(description = "通知公告信息请求参数vo类")
public class SysNoticeParams extends PageParams {
	/** serialVersionUID */
	private static final long serialVersionUID = -3508369242421497313L;

	/** 公告标题 */
	@ApiModelProperty(value = "公告标题")
	private String noticeTitle;
	/** 公告类型 */
	@ApiModelProperty(value = "公告类型")
	private String noticeType;
	/** 操作人员 */
	@ApiModelProperty(value = "操作人员")
	private String createBy;
}
