package com.roiim.paysafe.dto;

import org.springframework.http.HttpStatus;

public class PaySafeResponseDTO<T> {
	private HttpStatus status;
    private String message;
    private T data;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

//	public void setData(Customer creatSingleUserCustomerToken) {
//		
//	}
//    
//	public void setData(SingleUseCustomerTokenResponseDTO  creatSingleUserCustomerToken) {
//		
//		//this.data = creatSingleUserCustomerToken;
//		
//    }
//	
//public void setData(MakePaymentRequestDTO  makePayment) {
//		
//		//this.data = makePayment;
//		
//    }
	@Override
	public String toString() {
		return "PaySafeResponseDTO [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
	

//	@Override
//	public String toString() {
//		return "PaySafeResponseDTO [status=" + this.status + ", message=" + this.message + ", data=" + this.data + "]";
//	}
//	

}
