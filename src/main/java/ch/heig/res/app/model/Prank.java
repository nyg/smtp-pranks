package ch.heig.res.app.model;

import lombok.Getter;

public class Prank {

    @Getter
    private String subject;

    private StringBuilder message = new StringBuilder();

    public void add(String line) {

        if (subject == null && !line.trim().isEmpty()) {
            subject = line.trim();
        }
        else {
            message.append(line).append("\r\n");
        }
    }

    public String getMessage() {
        return message.toString().trim();
    }
}
