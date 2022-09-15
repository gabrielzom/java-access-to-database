package dao;

import dto.AddressMetadataDto;
import dto.ClientMetadataDto;
import model.Address;
import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    private static String sql;
    private final Connection connection;
    public ClientDao(Connection connection) {
        this.connection = connection;
    }

    public void create(Client client, long addressId) {
        sql = "INSERT INTO %s VALUES (null, ?, ?, ?)";
        sql = String.format(sql, ClientMetadataDto.tableName);

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, new Date(client.getDateOfBorn().getTime()));
            preparedStatement.setLong(3, addressId);

            int result = preparedStatement.executeUpdate();
            System.out.printf("[LOG] Insert Client in database. Result: %s", result);

        } catch (SQLException e) {
            System.out.printf("[ERROR] Don't insert Client in database. Message:\n%s", e.getMessage());
        }
    }

    public List<Client> findAll() {
        sql = "SELECT client.id, full_name, date_of_born, " +
                "address.zip_code, address.public_place, address.home_number, " +
                "address.district, address.city, address.state " +
                "FROM tab_clients client " +
                "INNER JOIN tab_addresses address " +
                "ON client.address_id = address.id";

        try {
            Statement statement = this.connection.createStatement();
            var result = statement.executeQuery(sql);
            List<Client> clients = new ArrayList<>();

            while (result.next()) {
                Client client = new Client();
                client.address  = new Address();

                client.setId(result.getLong(ClientMetadataDto.id));
                client.setFullName(result.getString(ClientMetadataDto.fullName));
                client.setDateOfBorn(result.getDate(ClientMetadataDto.dateOfBorn));
                client.address.setZipCode(result.getString(AddressMetadataDto.zipCode));
                client.address.setPublicPlace(result.getString(AddressMetadataDto.publicPlace));
                client.address.setHomeNumber(result.getString(AddressMetadataDto.homeNumber));
                client.address.setDistrict(result.getString(AddressMetadataDto.district));
                client.address.setCity(result.getString(AddressMetadataDto.city));
                client.address.setState(result.getString(AddressMetadataDto.state));


                clients.add(client);
            }
            System.out.println("[LOG] Query all Clients in database.");
            return clients;

        } catch (SQLException e) {
            System.out.printf("[ERROR] Cant query all Clients in database. Message:\n%s", e.getMessage());
            return null;
        }
    }

    public List<Client> findByPk(long clientId) {
        sql = "SELECT client.id, full_name, date_of_born, " +
                "address.zip_code, address.public_place, address.home_number, " +
                "address.district, address.city, address.state " +
                "FROM tab_clients client " +
                "INNER JOIN tab_addresses address " +
                "ON client.address_id = address.id " +
                "WHERE client.id = ?";


        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, clientId);
            var result = preparedStatement.executeQuery();
            List<Client> clients = new ArrayList<>();

            if (result.next()) {
                Client client = new Client();
                client.address = new Address();

                client.setId(result.getLong(ClientMetadataDto.id));
                client.setFullName(result.getString(ClientMetadataDto.fullName));
                client.setDateOfBorn(result.getDate(ClientMetadataDto.dateOfBorn));
                client.address.setZipCode(result.getString(AddressMetadataDto.zipCode));
                client.address.setPublicPlace(result.getString(AddressMetadataDto.publicPlace));
                client.address.setHomeNumber(result.getString(AddressMetadataDto.homeNumber));
                client.address.setDistrict(result.getString(AddressMetadataDto.district));
                client.address.setCity(result.getString(AddressMetadataDto.city));
                client.address.setState(result.getString(AddressMetadataDto.state));
                clients.add(client);
            }

            System.out.println("[LOG] Query one Client in database.");
            return clients;

        } catch (SQLException e) {
            System.out.printf("[ERROR] Cant query one Client in database. Message:\n%s", e.getMessage());
            return null;
        }
    }

    public void delete(long clientId) {
        sql = "DELETE FROM %s WHERE %s = ?";
        sql = String.format(
                sql,
                ClientMetadataDto.tableName,
                ClientMetadataDto.id
        );

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
            System.out.println("[LOG] Successfully delete Client in database.");
        } catch (SQLException e) {
            System.out.printf("[ERROR] Cant delete Client in database. Message:\n%s", e.getMessage());
        }
    }

    public void update(Client client) {
        sql = "UPDATE %s " +
                "SET %s = ?, %s = ? " +
                "WHERE %s = ?";
        sql = String.format(
                sql,
                ClientMetadataDto.tableName,
                ClientMetadataDto.fullName,
                ClientMetadataDto.dateOfBorn,
                ClientMetadataDto.id
        );

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, new Date(client.getDateOfBorn().getTime()));
            preparedStatement.setLong(3, client.getId());
            preparedStatement.executeUpdate();
            System.out.println("[LOG] Successfully update Client in database.");
        } catch (SQLException e) {
            System.out.printf("[ERROR] Cant update Client in database. Message:\n%s", e.getMessage());
        }
    }
}
