package com.personal.util;

public class Snippet
{
    private int id;
    private String title;
    private String code;

    public Snippet(String newTitle, String newCode)
    {
        id = -1;
        title = newTitle;
        code = newCode;
    }

    public void setId(int newId)
    {
        id = newId;
    }

    public int getId()
    {
        return id;
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

    @Override
    public String toString()
    {
        return title;
    }
}

