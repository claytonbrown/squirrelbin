package co.edu.usbcali.gusrodli.squirrelbin.create.dto;

public class Item
{
    public String name;
    public String author;
    public String id;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Item{" +
            "name='" + name + '\'' +
            ", author='" + author + '\'' +
            ", id='" + id + '\'' +
            '}';
    }
}