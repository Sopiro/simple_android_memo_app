package io.github.final_project.main;

public class ListItem
{
    private int icon;
    private String title;
    private String date;

    public ListItem(int icon, String title, String date)
    {
        this.icon = icon;
        this.title = title;
        this.date = date;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
