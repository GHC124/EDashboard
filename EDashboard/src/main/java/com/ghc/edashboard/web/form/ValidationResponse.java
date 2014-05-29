package com.ghc.edashboard.web.form;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponse {
	public static final String FAIL = "FAIL";
	public static final String SUCCESS = "SUCCESS";
	
	private String status = FAIL;
	private List<ErrorMessage> result = new ArrayList<>();
	private String extraData;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ErrorMessage> getResult() {
		return result;
	}

	public void setResult(List<ErrorMessage> result) {
		this.result = result;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
	
	public void addErrorMessage(ErrorMessage errorMessage) {
		result.add(errorMessage);
	}
}
