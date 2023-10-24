/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.medicalrecord.dao.PurposeRecordDao;
import com.github.zmzhou.easyboot.api.medicalrecord.entity.PurposeRecord;
import com.github.zmzhou.easyboot.api.medicalrecord.excel.PurposeRecordExcel;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PurposeRecordParams;
import com.github.zmzhou.easyboot.framework.service.BaseService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 看诊记录Service接口
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-24 22:22:23
 */
@Service
@CacheConfig(cacheNames = {"medicalrecord:PurposeRecord"})
@Transactional(rollbackFor = Exception.class)
public class PurposeRecordService extends BaseService<PurposeRecordParams> {
    @Resource
    private PurposeRecordDao dao;

    /**
     * 分页查询看诊记录数据
     *
     * @param params 查询参数
     * @param pageable 分页
     * @return Page<PurposeRecord>
     */
    public Page<PurposeRecord> findAll(PurposeRecordParams params, Pageable pageable) {
	    // 构造分页排序条件
	    Pageable page = pageable;
	    if (pageable.getSort().equals(Sort.unsorted())) {
		    Sort sort = Sort.by(Sort.Order.desc(Constants.CREATE_TIME));
		    page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	    }
	    // 构造查询条件
	    Specification<PurposeRecord> spec = new SimpleSpecificationBuilder<PurposeRecord>()
			    .and("chiefComplaint", Operator.LIKE, params.getChiefComplaint())
			    .and("medicines", Operator.LIKE, params.getMedicines())
			    .build();
	    return dao.findAll(spec, page);
    }

    /**
     * 根据id查询看诊记录
     *
     * @param id 看诊记录id
     * @return PurposeRecord对象
     * @author zmzhou
     * date 2023-10-24 22:22:23
     */
    public PurposeRecord findById(Long id) {
	    if (null == id) {
		    return new PurposeRecord();
	    }
	    return dao.findById(id).orElse(new PurposeRecord());
    }

    /**
     * 新增看诊记录
     *
     * @param entity 看诊记录
     * @return PurposeRecord 新增结果
     * @author zmzhou
     * date 2023-10-24 22:22:23
     */
    public PurposeRecord save(PurposeRecord entity) {
	    entity.setCreateTime(new Date());
	    entity.setCreateBy(SecurityUtils.getUsername());
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 修改看诊记录
     *
     * @param entity 看诊记录
     * @return PurposeRecord 修改结果
     * @author zmzhou
     * date 2023-10-24 22:22:23
     */
    public PurposeRecord update(PurposeRecord entity) {
	    entity.setUpdateTime(new Date());
	    entity.setUpdateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 批量删除看诊记录
     *
     * @param ids 需要删除的看诊记录ID
     * @author zmzhou
     * date 2023-10-24 22:22:23
     */
    public void deleteByIds(Long[] ids) {
	    for (Long id: ids) {
		    // 根据用户id删除数据
		    dao.deleteById(id);
	    }
    }

    /**
     * 导出excel
     *
     * @param params 查询参数
     * @return excel文件路径名
     * @throws InterruptedException 异常信息
     * @author zmzhou
     * date 2023-10-24 22:22:23
     */
    @Override
    public String export(PurposeRecordParams params) throws InterruptedException {
		Page<PurposeRecord> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		// 判断是字典类型还是字典数据导出
		Class<? extends BaseExcel> clazz = PurposeRecordExcel.class;
		// 判断是否还有下一页数据
		while (list.hasNext()) {
			dataConversion(list, excelList, clazz);
			list = findAll(params, list.nextPageable());
		}
		// 把最后一页数据加入
		dataConversion(list, excelList, clazz);
		return excelUtils.download(excelList, clazz, params.getExcelName());
	}
}
