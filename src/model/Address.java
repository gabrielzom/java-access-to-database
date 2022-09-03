package model;

public class Address {
    private long id;
    private String zipCode;
    private String publicPlace;
    private String homeNumber;
    private String district;
    private String city;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String full() {
        return String.format(
                "%s, %s - %s, %s - %s - CEP: %s",
                this.publicPlace,
                this.homeNumber,
                this.district,
                this.city,
                this.state,
                this.zipCode
        );
    }
    @Override
    public String toString() {
        return "Address{" +
                "zipCode='" + zipCode + '\'' +
                ", publicPlace='" + publicPlace + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
