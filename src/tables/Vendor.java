package tables;
public class Vendor
{
	String id,name,mob,address;
	public Vendor() {}
	public Vendor (String name, String id, String mob, String address)
    { 
        this.id = id; 
        this.name = name; 
        this.mob=mob;
        this.address=address;
    }
    public void setId(String s) { id=s; }
	public void setName(String s){ name = s; }
	public void setMob(String s){ mob=s; }
	public void setAdd(String s){ address=s; }
	public String getId() { return id; }
	public String getName() { return name; }
	public String getMob() { return mob; }
	public String getAdd() { return address; }
	public void print(){ System.out.println("Vendor ID : " + id + "Name : " + name + "Mobile No. : " + mob + "Address : " + address);}
};
