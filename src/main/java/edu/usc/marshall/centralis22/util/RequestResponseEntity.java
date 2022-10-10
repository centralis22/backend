package edu.usc.marshall.centralis22.util;

/**
 *
 */
public class RequestResponseEntity {
    public String type = "respond";
    public long respond_id = -1;
    public int status_code;
    public String status_message;
    public Object content;

    public RequestResponseEntity setRespondId(long respondId) {
        this.respond_id = respondId;
        return this;
    }

    public RequestResponseEntity setStatusCode(int status_code) {
        this.status_code = status_code;
        return this;
    }

    public RequestResponseEntity setStatusMessage(String status_message) {
        this.status_message = status_message;
        return this;
    }

    public RequestResponseEntity setContent(Object content) {
        this.content = content;
        return this;
    }

    public RequestResponseEntity(
            String type,
            long respond_id,
            int status_code,
            String status_message,
            Object content) {
        this.type = type;
        this.respond_id = respond_id;
        this.status_code = status_code;
        this.status_message = status_message;
        this.content = content;
    }

    public RequestResponseEntity() {
    }

    @Override
    public String toString() {
        // TODO: Return according to API
        try {
            return JacksonUtil.objectMapper().writeValueAsString(this);
        }
        catch(Exception e) {
            // TODO: Do something about it.
            System.out.println(e.getMessage());
        }
        return "";
    }
}
