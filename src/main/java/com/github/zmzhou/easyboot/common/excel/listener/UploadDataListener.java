package com.github.zmzhou.easyboot.common.excel.listener;

import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * UploadDataListener
 * 模板的读取类
 * <p>
 * 有个很重要的点 UploadDataListener不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 * </>
 * @param <B> the type parameter
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/6 18:11
 */
@Slf4j
public class UploadDataListener<B> extends AnalysisEventListener<B> {
    /**
     * 每隔n条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private List<B> list;

    /**
     * Gets list.
     *
     * @return the list
     */
    public List<B> getList() {
        return list;
    }

    /**
     * Sets list.
     *
     * @param list the list
     */
    public void setList(List<B> list) {
        this.list = list;
    }

    /**
     * 无参构造
     */
    public UploadDataListener() {
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param list 服务端接口URL参数
     */
    public UploadDataListener(List<B> list) {
        this.list = list;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context 上下文
     */
    @Override
    public void invoke(B data, AnalysisContext context) {
        log.debug("解析一条数据：{}", data);
        list.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        log.info("所有数据解析完成！");
    }
}
