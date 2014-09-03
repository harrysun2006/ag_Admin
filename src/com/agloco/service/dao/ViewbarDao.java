package com.agloco.service.dao;

import java.util.List;

import com.agloco.model.VBTimeTotal;
/**
 * 
 * @author terry_zhao
 * @date May 8, 2007
 */
public interface ViewbarDao {

	public VBTimeTotal getTimeTotal(Long memberId);
	public void updateTimeTotal(VBTimeTotal timeTotal);
	public List getSubtotalTableNames(String baseName) throws Exception;

}
