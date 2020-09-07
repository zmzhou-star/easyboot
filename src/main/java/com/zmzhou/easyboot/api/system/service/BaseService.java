package com.zmzhou.easyboot.api.system.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zmzhou.easyboot.api.system.entity.SysUser;
import com.zmzhou.easyboot.common.excel.BaseExcel;
import com.zmzhou.easyboot.common.excel.ExcelUtils;
import com.zmzhou.easyboot.common.exception.BaseException;
import com.zmzhou.easyboot.framework.entity.BaseEntity;
import com.zmzhou.easyboot.framework.entity.Params;

import lombok.extern.slf4j.Slf4j;

/**
 * @title BaseService
 * @description 
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/3 22:53
 */
@Slf4j
@Service
public abstract class BaseService {

   /**
    * The Excel utils.
    */
   @Autowired
   protected ExcelUtils excelUtils;

   /**
    * @description 导出excel
    * @param  params 查询参数
    * @return excel文件路径名
    * @author zmzhou
    * @date 2020/9/3 22:59
    */
   public abstract String export(Params params);
   
   /**
    * @description 下载excel导入模板
    * @param excelClass  excel模板类
    * @param excelName  excel模板文件名
    * @return  excel文件路径名
    * @author zmzhou
    * @date 2020/9/5 21:44
    */
   public String excelTemplate(Class<? extends BaseExcel> excelClass, String excelName){
        return excelUtils.download(Collections.emptyList(), excelClass, excelName);
   }

   /**
    * 组装分页参数
    * @param params 查询参数
    * @return Pageable
    * @author zmzhou
    * @date 2020/07/09 11:31
    */
   protected Pageable getPageable(Params params) {
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
    * @description  组装excel查询分页参数（默认每次查询2000条数据）
    * @param params 查询参数
    * @return Pageable
    * @author zmzhou
    * @date 2020/9/3 23:03
    */
   protected Pageable getExcelPageable(Params params) {
      Pageable pageable = getPageable(params);
      return PageRequest.of(pageable.getPageNumber(), 1000, pageable.getSort());
   }

   /**
    * @description 实体类数据转化为excel导出数据
    * @param   page 实体类page数据
    * @param   excelList excel数据集合
    * @param   clazz excel类型
    * @author zmzhou
    * @date 2020/9/3 23:34
    */
   public void dataConversion(Page<? extends BaseEntity> page, List<BaseExcel> excelList,
                              Class<? extends BaseExcel> clazz){
      log.info("{}数据导出，总页数：{}，当前页：{}", clazz.getSimpleName(), page.getTotalPages(), page.getNumber() + 1);
      // 数据类型转换
      page.getContent().forEach(entity -> {
         BaseExcel excel;
         try {
            // 获取excel类实例
            excel = clazz.newInstance();
         } catch (InstantiationException | IllegalAccessException e) {
            log.error("", e);
            throw new BaseException(500, "class newInstance error");
         }
         // copy数据
         BeanUtils.copyProperties(entity, excel);
         excelList.add(excel);
      });
   }

}
