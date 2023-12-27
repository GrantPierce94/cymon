package test.connect.myapplication.model;

public class Person {

    private int id;

    private String firstName;
    private String lastName;


    public int getId() { return id; }
    public void setId(int it) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String printable() {
        return "\nFIRSTNAME: " + getFirstName()
                +"\nLASTNAME: " + getLastName()+"\n";
    }

    public String Index() {
        return "Hello, World!";
    }

}
