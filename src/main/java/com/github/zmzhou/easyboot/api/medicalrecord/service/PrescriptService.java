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

import com.github.zmzhou.easyboot.api.medicalrecord.dao.PrescriptDao;
import com.github.zmzhou.easyboot.api.medicalrecord.entity.Prescript;
import com.github.zmzhou.easyboot.api.medicalrecord.excel.PrescriptExcel;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PrescriptParams;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.service.BaseService;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 药方Service接口
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-19 21:15:54
 */
@Service
@CacheConfig(cacheNames = {"medicalrecord:Prescript"})
@Transactional(rollbackFor = Exception.class)
public class PrescriptService extends BaseService<PrescriptParams> {
    @Resource
    private PrescriptDao dao;

    /**
     * 分页查询药方数据
     *
     * @param params 查询参数
     * @param pageable 分页
     * @return Page<Prescript>
     */
    public Page<Prescript> findAll(PrescriptParams params, Pageable pageable) {
	    // 构造分页排序条件
	    Pageable page = pageable;
	    if (pageable.getSort().equals(Sort.unsorted())) {
		    Sort sort = Sort.unsorted();
		    page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	    }
	    // 构造查询条件
	    Specification<Prescript> spec = new SimpleSpecificationBuilder<Prescript>()
			    .and("prescriptName", Operator.LIKE, params.getPrescriptName())
			    .and("purpose", Operator.LIKE, params.getPurpose())
			    .and("medicines", Operator.LIKE, params.getMedicines())
			    .build();
	    return dao.findAll(spec, page);
    }

    /**
     * 根据id查询药方
     *
     * @param id 药方id
     * @return Prescript对象
     * @author zmzhou
     * date 2023-10-19 21:15:54
     */
    public Prescript findById(Long id) {
	    if (null == id) {
		    return new Prescript();
	    }
	    return dao.findById(id).orElse(new Prescript());
    }

    /**
     * 查询所有药方.
     *
     * @return the list
     */
    public List<Prescript> findAllPrescript() {
        Specification<Prescript> spec = new SimpleSpecificationBuilder<Prescript>().build();
        return dao.findAll(spec);
    }

    /**
     * 新增药方
     *
     * @param entity 药方
     * @return Prescript 新增结果
     * @author zmzhou
     * date 2023-10-19 21:15:54
     */
    public Prescript save(Prescript entity) {
	    entity.setCreateTime(new Date());
	    entity.setCreateBy(SecurityUtils.getUsername());
	    entity.setUpdateTime(new Date());
	    entity.setUpdateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 修改药方
     *
     * @param entity 药方
     * @return Prescript 修改结果
     * @author zmzhou
     * date 2023-10-19 21:15:54
     */
    public Prescript update(Prescript entity) {
	    entity.setUpdateTime(new Date());
	    entity.setUpdateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 批量删除药方
     *
     * @param ids 需要删除的药方ID
     * @author zmzhou
     * date 2023-10-19 21:15:54
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
     * date 2023-10-19 21:15:54
     */
    @Override
    public String export(PrescriptParams params) throws InterruptedException {
		Page<Prescript> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		// 判断是字典类型还是字典数据导出
		Class<? extends BaseExcel> clazz = PrescriptExcel.class;
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
