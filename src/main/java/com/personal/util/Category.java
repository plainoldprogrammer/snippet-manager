package com.personal.util;

import java.util.List;
import java.util.ArrayList;

public class Category
{
    private String name;
    private int id;
    private List<Snippet> listOfSnippets;

    public Category()
    {
        name = "";
        listOfSnippets = new ArrayList<>();
    }

    public Category(String name)
    {
        this.name = name;
        listOfSnippets = new ArrayList<>();
    }

    public Category(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
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
        return name;
    }

    public String log()
    {
        return "[Category] id=" + this.id + " name=" + name;
    }

    public void addSnippet(Snippet snippet)
    {
        listOfSnippets.add(snippet);
    }
}
