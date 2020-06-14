package io.github.final_project.main;

import io.github.final_project.Utils;

public class ListItem
{
    private int icon;
    private String title;
    private String lastDate;
    private String creationDate;

    public ListItem(String title, String creationDate, String lastDate)
    {
        this.title = title;
        this.creationDate = creationDate;
        this.lastDate = lastDate;

        updateIcon();
    }

    public void updateIcon()
    {
        icon = Utils.getHashColor(title);
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

    public String getLastDate()
    {
        Utils.log(lastDate);

        return lastDate;
    }

    public void setLastDate(String lastDate)
    {
        this.lastDate = lastDate;
    }

    public String getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(String creationDate)
    {
        this.creationDate = creationDate;
    }
}
