package com.personal.util;

public class Snippet
{
    private int id;
    private String title;
    private String code;

    public Snippet(String title, String code)
    {
        id = -1;
        this.title = title;
        this.code = code;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setCode(String code)
    {
        this.code = code;
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
