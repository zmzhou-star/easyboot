package com.github.zmzhou.easyboot.api.monitor.service;

import java.util.ArrayList;
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

import com.github.zmzhou.easyboot.api.monitor.dao.SysTaskLogDao;
import com.github.zmzhou.easyboot.api.monitor.entity.SysTaskLog;
import com.github.zmzhou.easyboot.api.monitor.excel.SysTaskLogExcel;
import com.github.zmzhou.easyboot.api.monitor.vo.SysTaskLogParams;
import com.github.zmzhou.easyboot.api.system.service.BaseService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 定时任务日志Service接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-12-17 19:40:43
 */
@Service
@CacheConfig(cacheNames = {"monitor:SysTaskLog"})
@Transactional(rollbackFor = Exception.class)
public class SysTaskLogService extends BaseService<SysTaskLogParams> {
    @Resource
    private SysTaskLogDao dao;

    /**
     * 分页查询定时任务日志数据
     * @param params 查询参数
     * @param pageable 分页
     * @return Page<SysTaskLog>
     */
    public Page<SysTaskLog> findAll(SysTaskLogParams params, Pageable pageable) {
	    // 构造分页排序条件
	    Pageable page = pageable;
	    if (pageable.getSort().equals(Sort.unsorted())) {
		    Sort sort = Sort.by(Sort.Order.desc(Constants.STATUS));
		    page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	    }
	    // 构造查询条件
	    Specification<SysTaskLog> spec = new SimpleSpecificationBuilder<SysTaskLog>()
			    .and(Constants.STATUS, Operator.EQUAL, params.getStatus())
				.and("jobGroup", Operator.EQUAL, params.getJobGroup())
				.and("jobName", Operator.LIKE, params.getJobName())
				.between(Constants.CREATE_TIME, params.getBeginTime(), params.getEndTime())
			    .build();
	    return dao.findAll(spec, page);
    }

    /**
     * 根据id查询定时任务日志
     * 
     * @param id 定时任务日志id
     * @return SysTaskLog对象
     * @author zmzhou
     * date 2020-12-17 19:40:43
     */
    public SysTaskLog findById(Long id) {
	    if (null == id) {
		    return new SysTaskLog();
	    }
	    return dao.findById(id).orElse(new SysTaskLog());
    }

    /**
     * 新增定时任务日志
     * 
     * @param entity 定时任务日志
     * @return SysTaskLog 新增结果
     * @author zmzhou
     * date 2020-12-17 19:40:43
     */
    public SysTaskLog save(SysTaskLog entity) {
	    return dao.saveAndFlush(entity);
    }

    /**
     * 批量删除定时任务日志
     * 
     * @param ids 需要删除的定时任务日志ID
     * @author zmzhou
     * date 2020-12-17 19:40:43
     */
    public void deleteByIds(Long[] ids) {
	    for (Long id: ids) {
		    // 根据用户id删除数据
		    dao.deleteById(id);
	    }
    }

	/**
	 * 清空定时任务日志 
	 * @author zmzhou
	 * @date 2020/12/18 20:09
	 */
	public void clean() {
		dao.deleteAllInBatch();
	}

    /**
     * 导出excel
     *
     * @param params 查询参数
     * @return excel文件路径名
     * @throws InterruptedException 异常信息
     * @author zmzhou
     * date 2020-12-17 19:40:43
     */
    @Override
    public String export(SysTaskLogParams params) throws InterruptedException {
		Page<SysTaskLog> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		// 判断是字典类型还是字典数据导出
		Class<? extends BaseExcel> clazz = SysTaskLogExcel.class;
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
