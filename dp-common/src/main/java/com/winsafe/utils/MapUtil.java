package com.winsafe.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapUtil {
	private MapUtil() {}
	
	public static Map<String, Object> toLowerCaseKey(Map<String, Object> orgMap){
		Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty())
        {
            return resultMap;
        }

        Set<String> keySet = orgMap.keySet();
        for (String key : keySet)
        {
            resultMap.put(key.toLowerCase(), orgMap.get(key));
        }

        return resultMap;
	}
	
	public static List<Map<String, Object>> toLowerCaseKey(List<Map<String, Object>> orgList){
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (orgList == null || orgList.isEmpty())
		{
			return resultList;
		}
		
		Map<String, Object> resultMap = null;
		for(Map<String, Object> map: orgList){
			resultMap = new HashMap<String, Object>();
			for (String key : map.keySet())
			{
				resultMap.put(key.toLowerCase(), map.get(key));
			}
			resultList.add(resultMap);
			resultMap = null;
		}
		return resultList;
	}
}
