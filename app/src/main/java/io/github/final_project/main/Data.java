package io.github.final_project.main;

import java.util.ArrayList;
import java.util.List;

public class Data
{
    private static final Data me = new Data();

    public List<ListItem> items;

    private Data()
    {
        items = new ArrayList<>();
    }

    public static Data getInstance()
    {
        return me;
    }
}
