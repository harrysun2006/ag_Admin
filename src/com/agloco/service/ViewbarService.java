package com.agloco.service;

import java.util.List;

import com.agloco.model.VBTimeTotal;
/**
 * 
 * @author terry_zhao
 * @date May 8, 2007
 */
public interface ViewbarService extends BaseService {

	public VBTimeTotal getTimeTotal(Long memberId);
	public void updateTimeTotal(VBTimeTotal timeTotal);
	public List getSubtotalTableNames(String baseName) throws Exception;

}

