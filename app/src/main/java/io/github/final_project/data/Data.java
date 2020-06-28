package io.github.final_project.data;

import java.util.ArrayList;
import java.util.List;

import io.github.final_project.fragment.ListItem;

public class Data
{
    // 3개의 프래그먼트에서 사용할 리사이클러 뷰 속 List
    // Static 으로 다룬다
    private static List<ListItem> items = new ArrayList<>();

    public static List<ListItem> getData()
    {
        return items;
    }
}
