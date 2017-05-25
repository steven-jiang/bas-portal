package com.kii.bas.commons.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeTools {
	
	
	public static <T> Class<T> getGenericTypeClass(Class cls) {
		
		Type type = cls.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		} else {
			Class subCls = (Class) type;
			return getGenericTypeClass(subCls);
		}
	}
	
}
