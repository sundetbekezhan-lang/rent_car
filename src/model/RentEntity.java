package model;

public abstract class RentEntity {

    protected int id;

    public RentEntity(int id) {
        setId(id);
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract String getInfo();
}
