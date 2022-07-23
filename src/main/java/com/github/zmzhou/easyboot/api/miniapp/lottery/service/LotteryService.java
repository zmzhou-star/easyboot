/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.miniapp.lottery.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.util.UriEncoder;

import com.github.zmzhou.easyboot.api.miniapp.lottery.dao.LotteryHistoryDao;
import com.github.zmzhou.easyboot.api.miniapp.lottery.entity.LotteryHistory;
import com.github.zmzhou.easyboot.api.miniapp.lottery.excel.LotteryHistoryExcel;
import com.github.zmzhou.easyboot.api.miniapp.lottery.vo.LotteryHistoryParams;
import com.github.zmzhou.easyboot.api.system.entity.SysConfig;
import com.github.zmzhou.easyboot.api.system.service.SysConfigService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.utils.DateUtils;
import com.github.zmzhou.easyboot.framework.service.BaseService;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 彩票
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022/7/2 18:09
 */
@Slf4j
@Service
@CacheConfig(cacheNames = {"miniapp.lottery:LotteryHistory"})
@Transactional(rollbackFor = Exception.class)
public class LotteryService extends BaseService<LotteryHistoryParams> {
    @Resource
    private SysConfigService configService;

    @Resource
    private LotteryHistoryDao dao;

    /**
     * 采集彩票大乐透历史开奖数据
     *
     * @return boolean
     * @author zmzhou
     * @since 2022/7/2 18:10
     */
    @SneakyThrows
    public boolean collectDltHistoryData() {
        SysConfig collectRangeConfig = configService.findByConfigKey("lottery.dlt.collect.range");
        String[] collectRange = collectRangeConfig.getConfigValue().split(Constants.MINUS);
        SysConfig dltUrlConfig = configService.findByConfigKey("lottery.dlt.url");
        for (int year = Integer.parseInt(collectRange[0]); year <= Integer.parseInt(collectRange[1]); year++) {
            Document document = Jsoup.connect(UriEncoder.encode(dltUrlConfig.getConfigValue()))
                .data("action", "years")
                .data("year", String.valueOf(year)).post();
            Elements elements = document.getElementsByTag("tr");
            log.info("采集{}年的数据共{}条", year, elements.size());
            for (int i = 2; i < elements.size(); i++) {
                Element element = elements.get(i);
                Elements tds = element.getElementsByTag("td");
                LotteryHistory history = new LotteryHistory();
                history.setLotteryType("dlt");
                history.setLotteryId(Long.parseLong(tds.get(0).text()));
                history.setLotteryDate(DateUtils.parseDate(tds.get(1).text()));
                history.setSalesAmount(tds.get(2).text());
                String[] balls = tds.get(3).text().split("\\s+");
                history.setRed1(balls[0]);
                history.setRed2(balls[1]);
                history.setRed3(balls[2]);
                history.setRed4(balls[3]);
                history.setRed5(balls[4]);
                history.setBlue1(balls[5]);
                history.setBlue2(balls[6]);
                history.setPrizeNum1(tds.get(4).text());
                history.setPrizeBonus1(tds.get(5).text());
                history.setPrizeNum2(tds.get(6).text());
                history.setPrizeBonus2(tds.get(7).text());
                history.setPrizeNum3(tds.get(8).text());
                history.setPrizeBonus3(tds.get(9).text());
                this.save(history);
            }
        }
        return true;
    }

    /**
     * 分页查询彩票历史数据数据
     *
     * @param params 查询参数
     * @param pageable 分页
     * @return Page<LotteryHistory>
     */
    public Page<LotteryHistory> findAll(LotteryHistoryParams params, Pageable pageable) {
        // 构造分页排序条件
        Pageable page = pageable;
        if (pageable.getSort().equals(Sort.unsorted())) {
            Sort sort = Sort.by(Sort.Order.desc("lotteryId"));
            page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        // 构造查询条件
        Specification<LotteryHistory> spec = new SimpleSpecificationBuilder<LotteryHistory>()
            .and("lotteryId", Operator.EQUAL, params.getLotteryId())
            .and("lotteryType", Operator.EQUAL, params.getLotteryType())
            .between("and", "lotteryDate", params.getBeginTime(), params.getEndTime())
            .build();
        return dao.findAll(spec, page);
    }

    /**
     * 根据id查询彩票历史数据
     *
     * @param id 彩票历史数据id
     * @return LotteryHistory对象
     * @author zmzhou
     * date 2022-07-03 21:09:35
     */
    public LotteryHistory findById(Long id) {
        if (null == id) {
            return new LotteryHistory();
        }
        return dao.findById(id).orElse(new LotteryHistory());
    }

    /**
     * 新增彩票历史数据
     *
     * @param entity 彩票历史数据
     * @return LotteryHistory 新增结果
     * @author zmzhou
     * date 2022-07-03 21:09:35
     */
    public LotteryHistory save(LotteryHistory entity) {
        entity.setCreateTime(new Date());
        LotteryHistory history = dao.selectByLotteryId(entity.getLotteryId());
        if (history != null) {
            return history;
        }
        return dao.saveAndFlush(entity);
    }

    /**
     * 导出excel
     *
     * @param params 查询参数
     * @return excel文件路径名
     * @throws InterruptedException 异常信息
     * @author zmzhou
     * date 2022-07-03 21:09:35
     */
    @Override
    public String export(LotteryHistoryParams params) throws InterruptedException {
        Page<LotteryHistory> list = findAll(params, getExcelPageable(params));
        List<BaseExcel> excelList = new ArrayList<>();
        // 判断是字典类型还是字典数据导出
        Class<? extends BaseExcel> clazz = LotteryHistoryExcel.class;
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
