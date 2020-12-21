package com.github.zmzhou.easyboot.api.system.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.system.dao.SysNoticeDao;
import com.github.zmzhou.easyboot.api.system.entity.SysNotice;
import com.github.zmzhou.easyboot.api.system.vo.SysNoticeParams;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 通知公告信息Service接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-11-19 14:15:47
 */
@Service
@CacheConfig(cacheNames = {"system:SysNotice"})
@Transactional(rollbackFor = Exception.class)
public class SysNoticeService {
    @Resource
    private SysNoticeDao dao;

    /**
     * 分页查询通知公告信息数据
     * @param params 查询参数
     * @param pageable 分页
     * @return Page<SysNotice>
     */
    public Page<SysNotice> findAll(SysNoticeParams params, Pageable pageable) {
	    // 构造分页排序条件
	    Pageable page = pageable;
	    if (pageable.getSort().equals(Sort.unsorted())) {
		    Sort sort = Sort.by(Sort.Order.desc(Constants.STATUS));
		    page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	    }
	    // 构造查询条件
	    Specification<SysNotice> spec = new SimpleSpecificationBuilder<SysNotice>()
			    .and("noticeTitle", Operator.LIKE, params.getNoticeTitle())
			    .and("createBy", Operator.LIKE, params.getCreateBy())
			    .and("noticeType", Operator.EQUAL, params.getNoticeType())
				.and( Constants.STATUS, Operator.EQUAL, params.getStatus())
			    .build();
	    return dao.findAll(spec, page);
    }

    /**
     * 根据id查询通知公告信息
     * 
     * @param id 通知公告信息id
     * @return SysNotice对象
     */
    public SysNotice findById(Long id) {
	    if (null == id) {
		    return new SysNotice();
	    }
	    return dao.findById(id).orElse(new SysNotice());
    }

    /**
     * 新增通知公告信息
     * 
     * @param entity 通知公告信息
     * @return 结果
     */
    public SysNotice save(SysNotice entity) {
	    entity.setCreateTime(new Date());
	    entity.setCreateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 修改通知公告信息
     * 
     * @param entity 通知公告信息
     * @return 结果
     */
    public SysNotice update(SysNotice entity) {
	    entity.setUpdateTime(new Date());
	    entity.setUpdateBy(SecurityUtils.getUsername());
	    return dao.saveAndFlush(entity);
    }

    /**
     * 批量删除通知公告信息
     * 
     * @param ids 需要删除的通知公告信息ID
     */
    public void deleteByIds(Long[] ids) {
	    for (Long id: ids) {
		    // 根据用户id删除数据
		    dao.deleteById(id);
	    }
    }

	/**
	 * 统计用户数量 
	 * @param noticeType 公告类型
	 * @return 用户数量
	 * @author zmzhou
	 * @date 2020/12/21 10:59
	 */
	public long count(String noticeType){
		// 构造查询条件
		Specification<SysNotice> spec = new SimpleSpecificationBuilder<SysNotice>()
				.and("noticeType", Operator.EQUAL, noticeType)
				.and( Constants.STATUS, Operator.EQUAL, Constants.ONE)
				.build();
		return dao.count(spec);
	}
}
