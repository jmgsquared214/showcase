package com.smartgwt.sample.showcase.client;


public class SourceEntity {

    private String title;
    private String type;
    private String url;
    private boolean server;

    public SourceEntity(String title, String type, String url, boolean server) {
        this.title = title;
        this.type = type;
        this.url = url;
        this.server = server;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isServer() {
        return server;
    }
}
