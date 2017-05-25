package com.kii.bas.service.schema.exception;

import java.util.ArrayList;
import java.util.List;

public class VerifyException extends RuntimeException {
	
	private final List<FailReason> reasonList = new ArrayList<>();
	
	
	public VerifyException(FailReason reason) {
		reasonList.add(reason);
	}
	
	public VerifyException(List<FailReason> reasons) {
		reasonList.addAll(reasons);
	}
	
	public void addReason(FailReason reason) {
		reasonList.add(reason);
	}
	
	public List<FailReason> getReasonList() {
		return reasonList;
	}
}
