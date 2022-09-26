package edu.usc.marshall.centralis22.util;

public class RequestResponseEntity {
    long actionId;
    int statusCode;
    String message;

    public RequestResponseEntity setActionId(long actionId) {
        this.actionId = actionId;
        return this;
    }

    public RequestResponseEntity setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public RequestResponseEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public RequestResponseEntity(long actionId, int statusCode, String message) {
        this.actionId = actionId;
        this.statusCode = statusCode;
        this.message = message;
    }

    public RequestResponseEntity() {
    }

    @Override
    public String toString() {
        // TODO: Return according to API
        return "ActionResponseEntity{" +
                "actionId=" + actionId +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
