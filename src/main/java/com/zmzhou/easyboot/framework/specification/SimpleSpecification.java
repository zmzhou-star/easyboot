package com.zmzhou.easyboot.framework.specification;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.zmzhou.easyboot.common.Constants;
import com.zmzhou.easyboot.common.utils.DateUtils;

import lombok.ToString;

/**
 * @author zmzhou
 * @title SimpleSpecification
 * @Description 封装Specification工具类
 * @Date 2020/07/07 18:29
 */
@ToString
public class SimpleSpecification<T> implements Specification<T> {
	/** serialVersionUID */
	private static final long serialVersionUID = -1012947070278956018L;
	/**
	 * 查询的条件列表，是一组列表
	 */
	private final List<SpecificationOperator> operators;
	
	/**
	 * 构造器
	 *
	 * @param operators
	 * @author zmzhou
	 * @date 2020/07/07 18:30
	 */
	public SimpleSpecification(List<SpecificationOperator> operators) {
		this.operators = operators;
	}
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		//通过resultPre来组合多个条件
		Predicate resultPre = criteriaBuilder.conjunction();
		for (SpecificationOperator op : operators) {
			Predicate pre = generatePredicate(root, criteriaBuilder, op);
			if (pre == null) {
				continue;
			}
			if ("and".equalsIgnoreCase(op.getJoin())) {
				resultPre = criteriaBuilder.and(resultPre, pre);
			} else if ("or".equalsIgnoreCase(op.getJoin())) {
				resultPre = criteriaBuilder.or(resultPre, pre);
			}
		}
		return criteriaQuery.where(resultPre).getRestriction();
	}
	
	/**
	 * 根据不同的操作符返回特定的查询
	 *
	 * @param root Root<T>
	 * @param criteriaBuilder CriteriaBuilder
	 * @param op SpecificationOperator
	 * @return Predicate
	 * @author zmzhou
	 * @date 2020/07/07 18:31
	 */
	private Predicate generatePredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SpecificationOperator op) {
		switch (op.getOperator()) {
			case EQUAL:
				return criteriaBuilder.equal(root.get(op.getKey()), op.getValue());
			case NOTEQUAL:
				return criteriaBuilder.notEqual(root.get(op.getKey()), op.getValue());
			case GE:
				return criteriaBuilder.ge(root.get(op.getKey()), (Number) op.getValue());
			case LE:
				return criteriaBuilder.le(root.get(op.getKey()), (Number) op.getValue());
			case GT:
				return criteriaBuilder.gt(root.get(op.getKey()), (Number) op.getValue());
			case LT:
				return criteriaBuilder.lt(root.get(op.getKey()), (Number) op.getValue());
			case LIKE:
				return criteriaBuilder.like(root.get(op.getKey()),
						Constants.PERCENT_SIGN + op.getValue() + Constants.PERCENT_SIGN);
			case R_LIKE:
				return criteriaBuilder.like(root.get(op.getKey()),
						op.getValue() + Constants.PERCENT_SIGN);
			case L_LIKE:
				return criteriaBuilder.like(root.get(op.getKey()),
						Constants.PERCENT_SIGN + op.getValue());
			case ISNULL:
				return criteriaBuilder.isNull(root.get(op.getKey()));
			case IS_NOTNULL:
				return criteriaBuilder.isNotNull(root.get(op.getKey()));
			case BETWEEN:
				String[] arr = op.getValue().toString().split(Constants.COMMA);
				Date beginTime = DateUtils.parseDate(arr[0]);
				Date endTime = DateUtils.parseDate(arr[1]);
				return criteriaBuilder.between(root.get(op.getKey()), beginTime, endTime);
			default:
				break;
		}
		return null;
	}
	
}
