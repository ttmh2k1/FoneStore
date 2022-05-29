package hcmute.fonestore.Object;

public class User {
    private String Name, Avatar, Address, UID, Email, DOB;

    public User(String uid, String name, String avatar, String email, String address, String dob) {
        Email = email;
        UID = uid;
        Name = name;
        Avatar = avatar;
        Address = address;
        DOB = dob;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String uid) {
        this.UID = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String dob) {
        DOB = dob;
    }
}
