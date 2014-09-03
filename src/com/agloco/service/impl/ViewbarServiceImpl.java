package com.agloco.service.impl;

import java.util.List;

import com.agloco.model.VBTimeTotal;
import com.agloco.service.ViewbarService;
import com.agloco.service.dao.util.ViewbarDaoUtil;
/**
 * 
 * @author terry_zhao
 * @date May 8, 2007
 */
public class ViewbarServiceImpl implements ViewbarService {

	public VBTimeTotal getTimeTotal(Long memberId) {
		return ViewbarDaoUtil.getTimeTotal(memberId);
	}

	public void updateTimeTotal(VBTimeTotal timeTotal) {
		ViewbarDaoUtil.updateTimeTotal(timeTotal);
	}

	public List getSubtotalTableNames(String baseName) throws Exception {
		return ViewbarDaoUtil.getSubtotalTableNames(baseName);
	}

}
