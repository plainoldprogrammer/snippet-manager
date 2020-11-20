package com.personal.util;

import java.util.List;
import java.util.ArrayList;

public class Category
{
    private String title;
    private List<Snippet> listOfSnippets;

    public Category()
    {
        title = "";
        listOfSnippets = new ArrayList<>();
    }

    public Category(String title)
    {
        this.title = title;
        listOfSnippets = new ArrayList<>();
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setListOfSnippets(List<Snippet> newListOfSnippets)
    {
        listOfSnippets = newListOfSnippets;
    }

    public List getListOfSnippets()
    {
        return listOfSnippets;
    }

    @Override
    public String toString()
    {
        return title;
    }

    public void addSnippet(Snippet snippet)
    {
        listOfSnippets.add(snippet);
    }
}
