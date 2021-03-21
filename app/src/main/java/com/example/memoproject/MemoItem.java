package com.example.memoproject;

import java.io.Serializable;

public class MemoItem implements Serializable {
    String content;
    String time;

    public MemoItem(String content, String time) {
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
