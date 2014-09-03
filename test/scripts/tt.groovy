package com.agloco.report.util;

import java.util.ArrayList;
import java.util.List;

public class ResultProcess implements ResultProcessInterface
{

	public AGReportResultList processResult(List lists)
	{
		// TODO Auto-generated method stub
		AGReportResultList rl = new AGReportResultList();
		List tmpList1 = (List)lists.get(0);
		List tmpList2 = (List)lists.get(1);
		List result = new ArrayList();
		for(tmpData in tmpList1)
		{
			Object[] tmp = new Object[4];
			tmp[0] = tmpData[0];
			tmp[1] = tmpData[1];
			
			for(tmpData2 in tmpList2)
			{
				if(tmpData2[0].equals(tmpData[0]) || tmpData2[0]==(tmpData[0]))
				{
					tmp[2] = tmpData2[1];

					tmpList2.remove(tmpData2);
					break;
				}
			}
			if(tmp[2] != null && Double.parseDouble(tmp[2].toString()) > 0)
				tmp[3] = (tmp[1] - tmp[2])/tmp[2];
			else
			{
				tmp[2] = 0;
				tmp[3] = new Double(Double.NaN);
			}	
			result.add(tmp);
		}
		for(tmpData in tmpList2)
		{
			Object[] tmp = new Object[4];
			tmp[0] = tmpData[0];
			tmp[1] = new Double(0);
			tmp[2] = tmpData[1];
			tmp[3] = (tmp[1] - tmp[2])/tmp[2];
			result.add(tmp);
		}
		rl.setList(result);

		return rl;
	}
}
