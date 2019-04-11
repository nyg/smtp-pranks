package ch.heig.res.smtp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Mail {

    @Getter
    @Setter
    private String expeditor;

    @Getter
    @Setter
    private List<String> destinators;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String message;

    @Override
    public String toString() {
        return String.format("%s to %d recipients.", expeditor, destinators.size());
    }
}
