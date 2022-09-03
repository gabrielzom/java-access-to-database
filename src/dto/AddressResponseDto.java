package dto;

import model.Address;

public class AddressResponseDto {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Address toAddressModel() {
        Address address = new Address();
        address.setZipCode(this.cep);
        address.setPublicPlace(this.logradouro);
        address.setDistrict(this.bairro);
        address.setCity(this.localidade);
        address.setState(this.uf);

        return address;
    }

    @Override
    public String toString() {
        return "AddressResponseDto{" +
                "logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
