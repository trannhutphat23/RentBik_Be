package com.RentBikApp.RentBik.Model;

public class ErrorResponse {
    private String message;
    private boolean success;

    public ErrorResponse(String message) {
        this.message = message;
        this.success = false;
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
