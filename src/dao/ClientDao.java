package dao;

import dto.ClientDtoMetadata;
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

    public void create(Client client) {
        sql = "INSERT INTO %s VALUES (null, ?, ?)";
        sql = String.format(sql, ClientDtoMetadata.tableName);

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, new Date(client.getDateOfBorn().getTime()));

            int result = preparedStatement.executeUpdate();
            System.out.printf("[LOG] Insert Client in database. Result: %s", result);

        } catch (SQLException e) {
            System.out.printf("[ERROR] Don't insert Client in database. Message:\n%s", e.getMessage());
        }
    }

    public List<Client> findAll() {
        sql = "SELECT * FROM %s";
        sql = String.format(sql, ClientDtoMetadata.tableName);

        try {
            Statement statement = this.connection.createStatement();
            var result = statement.executeQuery(sql);
            List<Client> clients = new ArrayList<>();

            while (result.next()) {
                Client client = new Client();
                client.setId(result.getLong(ClientDtoMetadata.id));
                client.setFullName(result.getString(ClientDtoMetadata.fullName));
                client.setDateOfBorn(result.getDate(ClientDtoMetadata.dateOfBorn));
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
                ClientDtoMetadata.tableName,
                ClientDtoMetadata.id
        );

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, clientId);
            var result = preparedStatement.executeQuery();
            List<Client> clients = new ArrayList<>();

            while (result.next()) {
                Client client = new Client();
                client.setId(result.getLong(ClientDtoMetadata.id));
                client.setFullName(result.getString(ClientDtoMetadata.fullName));
                client.setDateOfBorn(result.getDate(ClientDtoMetadata.dateOfBorn));
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
                ClientDtoMetadata.tableName,
                ClientDtoMetadata.id
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
        sql = "UPDATE tab_clients " +
                "SET %s = ?, %s = ? " +
                "WHERE %s = ?";
        sql = String.format(
                sql,
                ClientDtoMetadata.fullName,
                ClientDtoMetadata.dateOfBorn,
                ClientDtoMetadata.id
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
