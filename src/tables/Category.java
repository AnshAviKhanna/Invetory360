package tables;

public class Category
{
	String id,name;
	public Category() {}
	public Category (String name, String id)
    { 
        this.id = id; 
        this.name = name; 
    }
    public void setId(String s) { id=s; }
	public void setName(String s){ name = s; }
	public String getId() { return id; }
	public String getName() { return name; }
};

