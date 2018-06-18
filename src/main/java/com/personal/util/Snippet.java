package com.personal.util;

public class Snippet
{
    private String title;
    private String code;

    public Snippet(String newTitle, String newCode)
    {
        title = newTitle;
        code = newCode;
    }

    public void setTitle(String newTitle)
    {
        title = newTitle;
    }

    public String getTitle()
    {
        return title;
    }

    public void setCode(String newCode)
    {
        code = newCode;
    }

    public String getCode()
    {
        return code;
    }
}

