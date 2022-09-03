package dao;

import dto.ClientDtoMetadata;
import model.Address;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDao {
    private static String sql;
    private final Connection connection;

    public AddressDao(Connection connection) {
        this.connection = connection;
    }

    public long create(Address address) {
        sql = "INSERT INTO %s VALUES (null, ?, ?, ?, ?, ?, ?)";
        sql = String.format(sql, "tab_addresses");

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, address.getZipCode());
            preparedStatement.setString(2, address.getPublicPlace());
            preparedStatement.setString(3, address.getHomeNumber());
            preparedStatement.setString(4, address.getDistrict());
            preparedStatement.setString(5, address.getCity());
            preparedStatement.setString(6, address.getState());

            preparedStatement.executeUpdate();
            var result = preparedStatement.getGeneratedKeys();

            long addressId = result.next() ? result.getLong(1) : -1L;
            System.out.println("[LOG] Insert Address in database.");
            return  addressId;

        } catch (SQLException e) {
            System.out.printf("[ERROR] Don't insert Address in database. Message:\n%s", e.getMessage());
            return -1L;
        }
    }

    public Address findByPk(long addressId) {
        sql = "SELECT * FROM %s WHERE %s = ?";
        sql = String.format(
                sql,
                "tab_addresses",
                "id"
        );

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, addressId);
            var result = preparedStatement.executeQuery();
            System.out.println("[LOG] Query one Client in database.");

            Address address = new Address();

            if (result.next()) {
                address.setId(result.getLong("id"));
                address.setZipCode(result.getString("zip_code"));
                address.setPublicPlace(result.getString("public_place"));
                address.setHomeNumber(result.getString("home_number"));
                address.setDistrict(result.getString("district"));
                address.setCity(result.getString("city"));
                address.setState(result.getString("state"));
            }
            return address;

        } catch (SQLException e) {
            System.out.printf("[ERROR] Cant query one Client in database. Message:\n%s", e.getMessage());
            return null;
        }
    }
}
