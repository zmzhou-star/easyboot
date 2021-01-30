package com.github.zmzhou.easyboot.api.monitor.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.monitor.dao.SysMailDao;
import com.github.zmzhou.easyboot.api.monitor.entity.SysMail;
import com.github.zmzhou.easyboot.api.monitor.excel.SysMailExcel;
import com.github.zmzhou.easyboot.api.monitor.vo.SysMailParams;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.framework.service.BaseService;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 系统邮件记录Service接口
 *
 * @author zmzhou
 * @version 1.0
 * date 2021-01-09 20:27:00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMailService extends BaseService<SysMailParams> {
    @Resource
    private SysMailDao dao;

    /**
     * 分页查询系统邮件记录数据
     * @param params 查询参数
     * @param pageable 分页
     * @return Page<SysMail>
     */
    public Page<SysMail> findAll(SysMailParams params, Pageable pageable) {
	    // 构造分页排序条件
	    Pageable page = pageable;
	    if (pageable.getSort().equals(Sort.unsorted())) {
		    Sort sort = Sort.by(Sort.Order.desc(Constants.STATUS));
		    page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	    }
	    // 构造查询条件
	    Specification<SysMail> spec = new SimpleSpecificationBuilder<SysMail>()
			    .and(Constants.STATUS, Operator.EQUAL, params.getStatus())
			    .and("subject", Operator.LIKE, params.getSubject())
			    .and("to", Operator.LIKE, params.getTo())
				.between("sendDate", params.getBeginTime(), params.getEndTime())
			    .build();
	    return dao.findAll(spec, page);
    }

    /**
     * 根据id查询系统邮件记录
     *
     * @param id 系统邮件记录id
     * @return SysMail对象
     * @author zmzhou
     * date 2021-01-09 20:27:00
     */
    public SysMail findById(Long id) {
	    if (null == id) {
		    return new SysMail();
	    }
	    return dao.findById(id).orElse(new SysMail());
    }

    /**
     * 新增系统邮件记录
     *
     * @param entity 系统邮件记录
     * @return SysMail 新增结果
     * @author zmzhou
     * date 2021-01-09 20:27:00
     */
    public SysMail save(SysMail entity) {
	    return dao.saveAndFlush(entity);
    }

    /**
     * 批量删除系统邮件记录
     *
     * @param ids 需要删除的系统邮件记录ID
     * @author zmzhou
     * date 2021-01-09 20:27:00
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
     * date 2021-01-09 20:27:00
     */
    @Override
    public String export(SysMailParams params) throws InterruptedException {
		Page<SysMail> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		// 判断是字典类型还是字典数据导出
		Class<? extends BaseExcel> clazz = SysMailExcel.class;
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
