package com.github.zmzhou.easyboot.api.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.service.BaseService;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 参数配置Service接口
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 21:51:23
 */
@Slf4j
@Service
@CacheConfig(cacheNames = {"system:SysConfig"})
@Transactional(rollbackFor = Exception.class)
public class SysConfigService extends BaseService<SysConfigParams> {
    @Resource
    private SysConfigDao dao;
    @Resource
    private RedisUtils redisUtils;

	/**
	 * 项目启动时，加载所有初始化参数到缓存 
	 * @author zmzhou
	 * @date 2020/11/17 11:50
	 */
	@PostConstruct
	public void initConfig() {
		List<SysConfig> configList = dao.findAll();
		configList.forEach(config -> redisUtils.set(getCacheKey(config.getConfigKey()), config.getConfigValue()));
		log.info("完成加载初始化参数：{}条到缓存", configList.size());
	}
    
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
	 * 根据参数键名查询参数值 
	 * @param configKey 参数键名
	 * @return SysConfig
	 * @author zmzhou
	 * @date 2020/11/17 11:16
	 */
	public SysConfig findByConfigKey(String configKey) {
		// 构造查询条件
		Specification<SysConfig> spec = new SimpleSpecificationBuilder<SysConfig>()
				.and("configKey", Operator.EQUAL, configKey).build();
		return dao.findOne(spec).orElse(null);
	}
	
	/**
	 * 根据参数键名查询参数值 
	 * @param configKey 参数键名
	 * @return ApiResult<SysConfig>
	 * @author zmzhou
	 * @date 2020/11/17 11:16
	 */
	public String findByKey(String configKey) {
		// 先从redis中获取
		String configValue = redisUtils.get(getCacheKey(configKey));
		if (StringUtils.isNotBlank(configValue)){
			return configValue;
		}
		SysConfig sysConfig = findByConfigKey(configKey);
		// 存在数据，则保存到redis缓存
		if (Optional.ofNullable(sysConfig).isPresent()){
			redisUtils.set(getCacheKey(configKey), sysConfig.getConfigValue());
			return sysConfig.getConfigValue();
		}
		return "";
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
	    entity = dao.saveAndFlush(entity);
	    // 更新缓存
		redisUtils.set(getCacheKey(entity.getConfigKey()), entity.getConfigValue());
	    return entity;
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
		entity = dao.saveAndFlush(entity);
		// 更新缓存
		redisUtils.set(getCacheKey(entity.getConfigKey()), entity.getConfigValue());
		return entity;
    }
    
    /**
	 * 校验参数键名是否唯一
	 * @param config 参数信息
	 * @return 结果
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	public boolean checkConfigKeyUnique(SysConfig config) {
		long configId = null == config.getId() ? -1L : config.getId();
		// 根据参数键名获取参数信息
		SysConfig sysConfig = findByConfigKey(config.getConfigKey());
		return null != sysConfig && sysConfig.getId() != configId;
	}

    /**
     * 批量删除参数配置
     * 
     * @param ids 需要删除的参数配置ID
     */
    public void deleteByIds(Long[] ids) {
	    for (Long id: ids) {
	    	// 判断是否系统内置参数
	    	SysConfig config = findById(id);
	    	if (Constants.YES.equals(config.getConfigType())){
	    		throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), "系统内置参数[0]不能删除", config.getConfigType());
			}
		    // 根据用户id删除数据
		    dao.deleteById(id);
			// 清空缓存数据
			redisUtils.delete(getCacheKey(config.getConfigKey()));
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
	
	/**
	 * 设置cache key
	 * @param configKey 参数键
	 * @return 缓存键key
	 * @author zmzhou
	 * @date 2020/11/17 11:24
	 */
	private String getCacheKey(String configKey) {
		return Constants.SYS_CONFIG_KEY + configKey;
	}
}
