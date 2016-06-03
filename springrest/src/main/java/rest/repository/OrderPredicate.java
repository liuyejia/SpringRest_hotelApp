package rest.repository;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import rest.common.SearchCriteria;
import rest.domain.Horder;
import rest.domain.RoomType;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;

@Data
public class OrderPredicate {

	private OrderPredicate() {
	}

	/*
	 * 使用querydsl进行条件查询
	 */
	public static BooleanExpression getPredicate(SearchCriteria criteria) {
		if (criteria == null) {
			return null;
		}
		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
		PathBuilder<Horder> entityPath = new PathBuilder<Horder>(Horder.class,
				"horder");

		if (criteria.getPretime() != null && criteria.getPosttime() != null) {
			//第一个参数是horder的属性名，第二个参数是类型
			NumberPath<Long> path = entityPath.getNumber("ordertime", Long.class);
			predicates.add(path.between(criteria.getPretime(),
					criteria.getPosttime()));
		} else if (criteria.getStatus() != null) {
			predicates.add(entityPath.getNumber("status", Integer.class).eq(
					criteria.getStatus()));
		} else if (criteria.getRoomtype() != null) {
			predicates.add(entityPath.getSimple("roomtype", RoomType.class).eq(
					criteria.getRoomtype()));
		} else
			return null;

		BooleanExpression result = predicates.get(0);
		for (int i = 1; i < predicates.size(); i++) {
			result = result.and(predicates.get(i));
		}
		return result;
	}

}
