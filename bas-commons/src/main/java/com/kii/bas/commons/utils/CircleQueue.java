package com.kii.bas.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CircleQueue<T> {
	
	private final List<List<T>> queueList;
	
	private final AtomicInteger count = new AtomicInteger(0);
	
	private final int SIZE;
	
	public CircleQueue(int size) {
		SIZE = size;
		
		queueList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++) {
			queueList.add(i, new ArrayList<T>());
		}
	}
	
	public void addObject(T entity) {
		queueList.get(count.get()).add(entity);
	}
	
	
	public List<T> getDataList() {
		
		int oldIndex = count.getAndAccumulate(1, (left, right) -> (left + right) % SIZE);
		
		List<T> dataList = queueList.get(oldIndex);
		
		List<T> newList = new ArrayList<>(dataList);
		
		dataList.clear();
		
		return newList;
	}
}
