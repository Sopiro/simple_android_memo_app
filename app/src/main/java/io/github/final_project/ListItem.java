package io.github.final_project;

public class ListItem
{
    private String title;
    private String lastDate;
    private String creationDate;

    public ListItem(String title, String creationDate, String lastDate)
    {
        this.title = title;
        this.creationDate = creationDate;
        this.lastDate = lastDate;
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
