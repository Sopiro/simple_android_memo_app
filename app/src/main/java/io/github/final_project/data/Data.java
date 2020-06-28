package io.github.final_project.data;

import java.util.ArrayList;
import java.util.List;

import io.github.final_project.fragment.ListItem;

public class Data
{
    private static List<ListItem> items = new ArrayList<>();

    public static List<ListItem> getData()
    {
        return items;
    }
}
