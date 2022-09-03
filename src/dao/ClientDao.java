package dao;

import dto.ClientMetadataDto;
import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    private static String sql;
    private final Connection connection;
    private final AddressDao addressDao;
    public ClientDao(Connection connection, AddressDao addressDao) {
        this.connection = connection;
        this.addressDao = addressDao;
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
        sql = "SELECT * FROM %s";
        sql = String.format(sql, ClientMetadataDto.tableName);

        try {
            Statement statement = this.connection.createStatement();
            var result = statement.executeQuery(sql);
            List<Client> clients = new ArrayList<>();

            while (result.next()) {
                Client client = new Client();
                client.setId(result.getLong(ClientMetadataDto.id));
                client.setFullName(result.getString(ClientMetadataDto.fullName));
                client.setDateOfBorn(result.getDate(ClientMetadataDto.dateOfBorn));
                client.setAddress(addressDao.findByPk(result.getLong("address_id")));
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
        sql = "SELECT * FROM %s WHERE %s = ?";
        sql = String.format(
                sql,
                ClientMetadataDto.tableName,
                ClientMetadataDto.id
        );

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, clientId);
            var result = preparedStatement.executeQuery();
            List<Client> clients = new ArrayList<>();

            while (result.next()) {
                Client client = new Client();
                client.setId(result.getLong(ClientMetadataDto.id));
                client.setFullName(result.getString(ClientMetadataDto.fullName));
                client.setDateOfBorn(result.getDate(ClientMetadataDto.dateOfBorn));
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
