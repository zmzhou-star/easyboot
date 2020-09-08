package com.github.zmzhou.easyboot.framework.specification;

import java.io.Serializable;

import lombok.Data;

/**
 *  @title SpecificationOperator
 *  @Description 操作符类，这个类中存储了键值对和操作符号，连接的条件类型是and还是or
 * 创建时通过 id>=7,其中id就是key,>=就是operator操作符，7就是value
 * 特殊的自定义几个操作符(:表示like %v%，b:表示v%,:b表示%v)
 *  @author zmzhou
 *  @Date 2020/07/07 18:26
 */
@Data
public class SpecificationOperator implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 837938919256998640L;
    /**
     * 属性，如查询的name,id之类
     */
    private String key;
    /**
     * 具体的查询条件
     */
    private Object value;
    /**
     * 操作符，自己定义的一组操作符，用来方便查询
     */
    private Operator operator;
    /**
     * 连接的方式：and或者or
     */
    private String join;
}
