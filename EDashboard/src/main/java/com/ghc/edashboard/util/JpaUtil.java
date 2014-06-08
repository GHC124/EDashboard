package com.ghc.edashboard.util;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.ghc.edashboard.web.form.DataGrid;

/**
 * 
 */
public class JpaUtil {
	
	public static <T> DataGrid<T> getDataGrid(Page<T> dataPage){
		DataGrid<T> dataGrid = new DataGrid<>();
		dataGrid.setCurrentPage(dataPage.getNumber() + 1);
		dataGrid.setTotalPages(dataPage.getTotalPages());
		dataGrid.setTotalRecords(dataPage.getTotalElements());
		dataGrid.setData(dataPage.getContent());
		return dataGrid;
	}
	
	public static PageRequest getPageRequest(Integer page, Integer rows, String sortBy, String order){
		Sort sort = null;
		String orderBy = sortBy;
		if (orderBy != null && order != null) {
			if (order.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, orderBy);
			} else {
				sort = new Sort(Sort.Direction.ASC, orderBy);
			}
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = new PageRequest(page - 1, rows, sort);
		} else {
			pageRequest = new PageRequest(page - 1, rows);
		}
		return pageRequest;
	}
	
	public static String[] getOrderbyAndSort(Pageable pageable){
		String[] data = new String[2];		
		Iterator<Order> i = pageable.getSort().iterator();
		while (i.hasNext()) {
			Order order = i.next();
			data[0] = order.getProperty();
			data[1] = order.getDirection().name();
		}
		return data;
	}
	
	private static <T> T map(Class<T> type, Object[] tuple) {
		List<Class<?>> tupleTypes = new ArrayList<>();
		for (int i = 0; i < tuple.length; i++) {
			if (tuple[i] != null) {
				if (tuple[i] instanceof BigInteger) {
					tuple[i] = new Long(((BigInteger) tuple[i]).longValue());
				}
				if (tuple[i] instanceof Date) {
					tuple[i] = new LocalDateTime(tuple[i]);					
				}
				tupleTypes.add(tuple[i].getClass());
			} else {
				tupleTypes.add(String.class);
			}
		}
		try {
			Constructor<T> ctor = type.getConstructor(tupleTypes
					.toArray(new Class<?>[tuple.length]));
			return ctor.newInstance(tuple);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> List<T> map(Class<T> type, List<Object[]> records) {
		List<T> result = new LinkedList<>();
		for (Object[] record : records) {
			result.add(map(type, record));
		}
		return result;
	}

	public static <T> List<T> getResultList(Query query, Class<T> type) {
		@SuppressWarnings("unchecked")
		List<Object[]> records = query.getResultList();
		return map(type, records);
	}

	public static <T> T getSingleResult(Query query, Class<T> type) {
		Object object = query.getSingleResult();
		if (object instanceof BigInteger) {
			object = ((BigInteger) object).longValue();
		}
		return type.cast(object);
	}
}
