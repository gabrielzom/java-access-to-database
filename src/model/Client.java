package model;

import java.util.Date;

public class Client {
    private long id;
    private String fullName;
    private Date dateOfBorn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBorn() {
        return dateOfBorn;
    }

    public void setDateOfBorn(Date dateOfBorn) {
        this.dateOfBorn = dateOfBorn;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBorn=" + dateOfBorn +
                '}';
    }
}
