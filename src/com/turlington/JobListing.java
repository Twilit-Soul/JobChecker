package com.turlington;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Information about a job.
 * Created by Mitchell on 7/18/2016.
 */
public class JobListing {
    private final String title;
    private String url = null;
    private Map<String, String> jobProperties = new HashMap<>();
    private String emailSubject;

    JobListing(String title) {
        this.title = title;
    }

    void addProperty(String name, String value) {
        jobProperties.put(name, value);
    }

    private Map<String, String> getProperties() {
        return jobProperties;
    }

    private Optional<String> getUrl() {
        if (url != null) {
            return Optional.of(url);
        }
        return Optional.empty();
    }

    void setUrl(String url) {
        this.url = url;
    }

    String getTitle() {
        return title;
    }

    String getEmailText() {
        StringBuilder text = new StringBuilder("Title: " + getTitle());
        if (getUrl().isPresent()) {
            text.append("\nLink: ").append(getUrl().get());
        }
        for (Map.Entry<String, String> entry : getProperties().entrySet()) {
            text.append("\n").append(entry.getKey()).append(": ").append(entry.getValue());
        }
        return text.toString();
    }

    void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    String getEmailSubject() {
        return emailSubject;
    }
}
