import dao.AddressDao;
import dao.ClientDao;
import database.Database;
import model.Address;
import model.Client;
import utils.ClientIO;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import static java.lang.System.*;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        Scanner scanner = new Scanner(in);
        var connection = Database.createConnection();
        AddressDao addressDao = new AddressDao(connection);
        ClientDao clientDao = new ClientDao(connection);
        ClientIO clientIO = new ClientIO();


        out.println("------ ACESSO AO BANCO DE DADOS ------");
        var option = clientIO.menu(scanner);

        while (option > 0 && option < 6) {
            switch (option) {
                case 1:
                    clientIO.printClients(clientDao.findAll());
                    option = clientIO.menu(scanner);
                    break;
                case 2:
                    Client client = new Client();
                    Address address = clientIO.readPropsOfClient(scanner, client);
                    long addressId = addressDao.create(address);
                    clientDao.create(client, addressId);
                    option = clientIO.menu(scanner);
                    break;
                case 3:
                    clientIO.printClients(clientDao.findByPk(clientIO.readIdOfClient(scanner)));
                    option = clientIO.menu(scanner);
                    break;
                case 4:
                    clientDao.delete(clientIO.readIdOfClient(scanner));
                    option = clientIO.menu(scanner);
                    break;
                case 5:
                    Client clientToUpdate = new Client();
                    clientToUpdate.setId(clientIO.readIdOfClient(scanner));
                    Address addressToUpdate = clientIO.readPropsOfClient(scanner, clientToUpdate);
                    clientDao.update(clientToUpdate);
                    option = clientIO.menu(scanner);
                    break;
            }
        }
    }
}
