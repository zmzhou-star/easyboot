package com.github.zmzhou.easyboot.api.system.service;

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

import com.github.zmzhou.easyboot.api.system.dao.SysConfigDao;
import com.github.zmzhou.easyboot.api.system.entity.SysConfig;
import com.github.zmzhou.easyboot.api.system.excel.SysConfigExcel;
import com.github.zmzhou.easyboot.api.system.vo.SysConfigParams;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 参数配置Service接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 21:51:23
 */
@Service
@CacheConfig(cacheNames = {"system:SysConfig"})
@Transactional(rollbackFor = Exception.class)
public class SysConfigService extends BaseService<SysConfigParams> {
    @Resource
    private SysConfigDao dao;

    /**
     * 分页查询参数配置数据
     * @param params 查询参数
     * @param pageable 分页
     * @return Page<SysConfig>
     */
    public Page<SysConfig> findAll(SysConfigParams params, Pageable pageable) {
	    // 构造分页排序条件
	    Pageable page = pageable;
	    if (pageable.getSort().equals(Sort.unsorted())) {
		    Sort sort = Sort.by(Sort.Order.asc(Constants.CREATE_TIME));
		    page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	    }
	    // 构造查询条件
	    Specification<SysConfig> spec = new SimpleSpecificationBuilder<SysConfig>()
			    .and("configName", Operator.LIKE, params.getConfigName())
			    .and("configKey", Operator.LIKE, params.getConfigKey())
			    .and("configType", Operator.EQUAL, params.getConfigType())
				.between(Constants.CREATE_TIME, params.getBeginTime(), params.getEndTime())
			    .build();
	    return dao.findAll(spec, page);
    }

    /**
     * 根据id查询参数配置
     * 
     * @param id 参数配置id
     * @return SysConfig对象
     */
    public SysConfig findById(Long id) {
	    if (null == id) {
		    return new SysConfig();
	    }
	    return dao.findById(id).orElse(new SysConfig());
    }

    /**
     * 新增参数配置
     * 
     * @param entity 参数配置
     * @return 结果
     */
    public SysConfig save(SysConfig entity) {
	    entity.setCreateTime(new Date());
	    entity.setCreateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 修改参数配置
     * 
     * @param entity 参数配置
     * @return 结果
     */
    public SysConfig update(SysConfig entity) {
	    entity.setUpdateTime(new Date());
	    entity.setUpdateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 批量删除参数配置
     * 
     * @param ids 需要删除的参数配置ID
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
	 * @date 2020/9/3 22:59
	 */
	@Override
	public String export(SysConfigParams params) throws InterruptedException {
		Page<SysConfig> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		while (list.hasNext()) {
			dataConversion(list, excelList, SysConfigExcel.class);
			list = findAll(params, list.nextPageable());
		}
		// 把最后一页数据加入
		dataConversion(list, excelList, SysConfigExcel.class);
		return excelUtils.download(excelList, SysConfigExcel.class, params.getExcelName());
	}
}
