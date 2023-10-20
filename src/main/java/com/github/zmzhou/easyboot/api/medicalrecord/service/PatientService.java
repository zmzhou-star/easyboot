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

import com.github.zmzhou.easyboot.api.medicalrecord.dao.PatientDao;
import com.github.zmzhou.easyboot.api.medicalrecord.entity.Patient;
import com.github.zmzhou.easyboot.api.medicalrecord.excel.PatientExcel;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PatientParams;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.service.BaseService;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 病人信息Service接口
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-20 21:15:29
 */
@Service
@CacheConfig(cacheNames = {"medicalrecord:Patient"})
@Transactional(rollbackFor = Exception.class)
public class PatientService extends BaseService<PatientParams> {
    @Resource
    private PatientDao dao;

    /**
     * 分页查询病人信息数据
     *
     * @param params 查询参数
     * @param pageable 分页
     * @return Page<Patient>
     */
    public Page<Patient> findAll(PatientParams params, Pageable pageable) {
	    // 构造分页排序条件
	    Pageable page = pageable;
	    if (pageable.getSort().equals(Sort.unsorted())) {
		    Sort sort = Sort.unsorted();
		    page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	    }
	    // 构造查询条件
	    Specification<Patient> spec = new SimpleSpecificationBuilder<Patient>()
			    .and("userName", Operator.LIKE, params.getUserName())
			    .and("tel", Operator.LIKE, params.getTel())
			    .build();
	    return dao.findAll(spec, page);
    }

    /**
     * 根据id查询病人信息
     *
     * @param id 病人信息id
     * @return Patient对象
     * @author zmzhou
     * date 2023-10-20 21:15:29
     */
    public Patient findById(Long id) {
	    if (null == id) {
		    return new Patient();
	    }
	    return dao.findById(id).orElse(new Patient());
    }

    /**
     * 新增病人信息
     *
     * @param entity 病人信息
     * @return Patient 新增结果
     * @author zmzhou
     * date 2023-10-20 21:15:29
     */
    public Patient save(Patient entity) {
	    entity.setCreateTime(new Date());
	    entity.setCreateBy(SecurityUtils.getUsername());
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 修改病人信息
     *
     * @param entity 病人信息
     * @return Patient 修改结果
     * @author zmzhou
     * date 2023-10-20 21:15:29
     */
    public Patient update(Patient entity) {
	    entity.setUpdateTime(new Date());
	    entity.setUpdateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 批量删除病人信息
     *
     * @param ids 需要删除的病人信息ID
     * @author zmzhou
     * date 2023-10-20 21:15:29
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
     * date 2023-10-20 21:15:29
     */
    @Override
    public String export(PatientParams params) throws InterruptedException {
		Page<Patient> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		// 判断是字典类型还是字典数据导出
		Class<? extends BaseExcel> clazz = PatientExcel.class;
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
