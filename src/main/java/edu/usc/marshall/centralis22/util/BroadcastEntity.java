package edu.usc.marshall.centralis22.util;

public class BroadcastEntity {
    public String type = "broadcast";
    public String broadcast;
    public Object content;

    public BroadcastEntity setBroadcast(String broadcast) {
        this.broadcast = broadcast;
        return this;
    }

    public BroadcastEntity setContent(Object content) {
        this.content = content;
        return this;
    }

    public BroadcastEntity(
            String broadcast,
            Object content) {
        this.broadcast = broadcast;
        this.content = content;
    }

    public BroadcastEntity() {
    }

    @Override
    public String toString() {
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
