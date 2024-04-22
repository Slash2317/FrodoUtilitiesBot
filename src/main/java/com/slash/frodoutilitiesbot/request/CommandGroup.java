package com.slash.frodoutilitiesbot.request;

public enum CommandGroup {

    MODERATION(":tools: **MODERATION & UTILITY**"),
    SERVER(":adult: **SERVER & MEMBERS**"),
    FUN(":smile: **FUN**"),
    MISC(":question: **MISC**");

    private final String title;

    CommandGroup(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
