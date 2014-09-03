package com.mysql.fooditem1.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import com.mysql.fooditem1.dao.FoodItemDAO;
import com.mysql.fooditem1.po.HFoodItem;
import com.mysql.fooditem1.vo.FoodItem;
import com.sophia.pagination.Page;

public class FoodItemRule {

	public FoodItemRule() {
	}

	private static HFoodItem getPO(FoodItem vo) throws Exception {
		HFoodItem po = null;
		if (vo != null) {
			po = new HFoodItem();
			BeanUtils.copyProperties(po, vo);
		}
		return po;
	}

	private static FoodItem getVO(HFoodItem po) throws Exception {
		FoodItem vo = null;
		if (po != null) {
			vo = new FoodItem();
			BeanUtils.copyProperties(vo, po);
		}
		return vo;
	}

	public static FoodItem getFoodItem(int id) throws Exception {
		return getFoodItem(new Long(id), null);
	}

	public static FoodItem getFoodItem(Long id) throws Exception {
		return getFoodItem(id, null);
	}

	public static FoodItem getFoodItem(Long id, Session session)
			throws Exception {
		FoodItemDAO dao = new FoodItemDAO();
		FoodItem vo = null;
		try {
			HFoodItem po = dao.getFoodItem(id, session);
			vo = getVO(po);
		} catch (Exception e) {
			throw e;
		}
		return vo;
	}

	public static void addFoodItem(FoodItem vo) throws Exception {
		addFoodItem(vo, null);
	}

	public static void addFoodItem(FoodItem vo, Session session)
			throws Exception {
		FoodItemDAO dao = new FoodItemDAO();
		try {
			HFoodItem po = getPO(vo);
			dao.addFoodItem(po, session);
			vo.setId(po.getId());
		} catch (Exception e) {
			throw e;
		}
	}

	public static void updateFoodItem(FoodItem vo) throws Exception {
		updateFoodItem(vo, null);
	}

	public static void updateFoodItem(FoodItem vo, Session session)
			throws Exception {
		FoodItemDAO dao = new FoodItemDAO();
		try {
			HFoodItem po = getPO(vo);
			dao.updateFoodItem(po, session);
		} catch (Exception e) {
			throw e;
		}
	}

	public static void deleteFoodItem(int id) throws Exception {
		deleteFoodItem(new Long(id));
	}

	public static void deleteFoodItem(Long id) throws Exception {
		FoodItem vo = new FoodItem();
		vo.setId(id);
		deleteFoodItem(vo, null);
	}

	public static void deleteFoodItem(FoodItem vo) throws Exception {
		deleteFoodItem(vo, null);
	}

	public static void deleteFoodItem(FoodItem vo, Session session)
			throws Exception {
		FoodItemDAO dao = new FoodItemDAO();
		try {
			HFoodItem po = getPO(vo);
			dao.deleteFoodItem(po, session);
		} catch (Exception e) {
			throw e;
		}
	}

	public static void saveFoodItem(FoodItem vo) throws Exception {
		saveFoodItem(vo, null);
	}

	public static void saveFoodItem(FoodItem vo, Session session)
			throws Exception {
		try {
			if (vo.getId() > 0L)
				updateFoodItem(vo, session);
			else
				addFoodItem(vo, session);
		} catch (Exception e) {
			throw e;
		}
	}

	public static List getFoodItemList(FoodItem vo) throws Exception {
		return getFoodItemList(vo, null, null);
	}

	public static List getFoodItemList(FoodItem vo, Session session)
			throws Exception {
		return getFoodItemList(vo, null, session);
	}

	public static List getFoodItemList(FoodItem vo, Page page, Session session)
			throws Exception {
		List poList = null;
		List voList = new ArrayList();
		try {
			HFoodItem po = getPO(vo);
			FoodItemDAO dao = new FoodItemDAO();
			poList = dao.getFoodItemList(po, page, session);
			for (Iterator it = poList.iterator(); it.hasNext(); ) {
				po = (HFoodItem) it.next();
				vo = getVO(po);
				voList.add(vo);
			}
			poList.clear();
			poList = null;
		} catch (Exception e) {
			throw e;
		}
		return voList;
	}

	public static void main(String[] args) throws Exception {
		List foodItems = FoodItemRule.getFoodItemList(null);
		System.out.println("Total " + foodItems.size() + " food items.");
	}
} 