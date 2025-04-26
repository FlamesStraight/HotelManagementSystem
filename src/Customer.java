public class Customer {
    private int customerID;
    private String name;
    private String email;
    private int phoneNumber;

    public Customer(int customerID, String name, String email, int phoneNumber){
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerID(){
        return customerID;
    }

    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerInfo(){
        return "Customer ID: " + customerID + "\nName: " + name + "\nEmail: " + email + "\nPhone: " + phoneNumber;
    }

    public String toFileString(){
        return customerID + "\nName: " + name + "\nEmail: " + email + "\nPhone: " + phoneNumber;
    }

    public String toString(){
        return getCustomerInfo();
    }
}
