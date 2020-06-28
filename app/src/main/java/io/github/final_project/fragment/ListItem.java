package io.github.final_project.fragment;

// 리사이클러뷰의 아이템으로 사용되는 클래스
public class ListItem
{
    private String title;
    private String lastDate;
    private String creationDate;

    ListItem(String title, String creationDate, String lastDate)
    {
        this.title = title;
        this.creationDate = creationDate;
        this.lastDate = lastDate;
    }

    String getTitle()
    {
        return title;
    }

    String getLastDate()
    {
        return lastDate;
    }

    public String getCreationDate()
    {
        return creationDate;
    }
}
