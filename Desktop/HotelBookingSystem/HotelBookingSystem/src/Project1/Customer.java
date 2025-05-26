package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

public class Customer {
    private String name;
    private String email;
    private String phoneNumber;

    //Customer constructor to initialize a new customer with fields above
    public Customer(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    //Getters and setters methods for a Customer object
    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerInfo(){
        return "Name: " + name + "\nEmail: " + email + "\nPhone: " + phoneNumber;
    }

    public String toFileString(){
        return name + ", " + email + ", " + phoneNumber;
    }

    @Override
    public String toString(){
        return getCustomerInfo();
    }
}