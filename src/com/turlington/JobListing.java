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

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getEmailText() {
        String text = "Title: "+getTitle();
        if (getUrl().isPresent()) {
            text += "\nLink: " +getUrl().get();
        }
        for (Map.Entry<String, String> entry : getProperties().entrySet()) {
            text+="\n"+entry.getKey()+": "+entry.getValue();
        }
        return text;
    }
}
