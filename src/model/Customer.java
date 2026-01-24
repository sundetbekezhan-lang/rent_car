package model;

public class Customer extends RentEntity {

    private String name;
    private String phone;

    public Customer(int id, String name, String phone) {
        super(id);
        setName(name);
        setPhone(phone);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be empty");
        }
        this.phone = phone;
    }

    @Override
    public String getInfo() {
        return "Customer{id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
