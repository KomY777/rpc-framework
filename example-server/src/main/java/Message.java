import lombok.Data;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getContent() {
        return message;
    }

    public void setContent(String message) {
        this.message = message;
    }
}
