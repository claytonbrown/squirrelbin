package co.edu.usbcali.gusrodli.squirrelbin.list.dto;

public class Request
{
    public Item item;

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return "Request{" +
            "item=" + item +
            '}';
    }
}
