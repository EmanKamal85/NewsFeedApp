package com.example.newsfeedapp;

public class Story {
    private String sectionName;
    private String webTitle;
    private String webPublicationDate;
    private String authorName;
    private String webUrl;

    Story(String sectionName, String webTitle, String webUrl) {
        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
    }

    Story(String sectionName, String webTitle, String webUrl, String webPublicationDate) {
        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.webPublicationDate = webPublicationDate;
    }

    Story(String sectionName, String webTitle, String webUrl, String webPublicationDate, String authorName) {
        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.webPublicationDate = webPublicationDate;
        this.authorName = authorName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
