package com.RentBikApp.RentBik.Model;

public class SuccessResponse {
    private String message;
    private boolean success;

    public SuccessResponse(String message) {
        this.message = message;
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
