package com.kii.bas.commons.store.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class QueryPager {
	
	private static Pattern pagerPat = Pattern.compile("^((\\d+)[\\/\\_]?)?(\\d+)$");
	
	
	private int start = 0;
	
	private int size = 0;
	
	
	public static QueryPager getInstance(String sign) {
		
		if (StringUtils.isBlank(sign)) {
			return null;
		}
		
		Matcher matcher = pagerPat.matcher(sign);
		
		if (matcher.find()) {
			
			QueryPager pager = new QueryPager();
			String a = matcher.group(2);
			String b = matcher.group(3);
			
			pager.size = Integer.parseInt(b);
			
			if (StringUtils.isNotBlank(a)) {
				pager.start = Integer.parseInt(a);
			}
			
			return pager;
		} else {
			return null;
		}
	}
	
	public int getStart() {
		return start;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getSum() {
		return start + size;
	}
}
