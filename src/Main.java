import dao.AddressDao;
import dao.ClientDao;
import database.Database;
import model.Address;
import model.Client;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

public class Main {

    public static void printClients(List<Client> clients) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        out.println("---------------------------------");
        for (var client : clients) {
            out.println(
                    "Id: " + client.getId() +
                    "\nNome: " + client.getFullName() +
                    "\nNascimento: " + simpleDateFormat.format(client.getDateOfBorn()) +
                    "\nEndereço: " + client.getAddress().full()
            );
            out.println("---------------------------------");
        }
    }
    public static void readPropsOfClient(Scanner scanner, Client client, Address address) throws ParseException{
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        out.println("Informe o primeiro nome Cliente: ");
            client.setFullName(scanner.next());
        out.println("Informe a data de nascimento no formato [dd/mm/aaaa]: ");
            client.setDateOfBorn(date.parse(scanner.next()));
        out.println("---- ENDEREÇO -----");
        out.println("Informe o CEP do Cliente:");
            address.setZipCode(scanner.next());
        out.println("Informe o Logradouro do Cliente:");
            address.setPublicPlace(scanner.next());
        out.println("Informe o Número do imóvel do Cliente:");
            address.setHomeNumber(scanner.next());
        out.println("Informe o Bairro do Cliente:");
            address.setDistrict(scanner.next());
        out.println("Informe a Cidade do Cliente:");
            address.setCity(scanner.next());
        out.println("Informe o Estado do Cliente no formato [UF]: ");
            address.setState(scanner.next());
    }

    public static long readIdOfClient(Scanner scanner) {
        out.println("Informe o ID do Cliente: ");
            return scanner.nextLong();
    }

    public static int menu(Scanner scanner) {
        out.println(
                "OPÇÕES: \n" +
                        "[1] - Consultar Todos os Registros\n" +
                        "[2] - Inserir Registro\n" +
                        "[3] - Consultar Registro por ID\n" +
                        "[4] - Deletar Registro por ID\n" +
                        "[5] - Atualizar registro por ID\n" +
                        "[0] - Encerrar Programa"
        );
        return scanner.nextInt();
    }

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(in);
        var connection = Database.createConnection();
        AddressDao addressDao = new AddressDao(connection);
        ClientDao clientDao = new ClientDao(connection, addressDao);


        out.println("------ ACESSO AO BANCO DE DADOS ------");
        var option = menu(scanner);

        while (option > 0 && option < 6) {
            switch (option) {
                case 1:
                    printClients(clientDao.findAll());
                    option = menu(scanner);
                    break;
                case 2:
                    Client client = new Client();
                    Address address = new Address();
                    readPropsOfClient(scanner, client, address);
                    long addressId = addressDao.create(address);
                    clientDao.create(client, addressId);
                    option = menu(scanner);
                    break;
                case 3:
                    printClients(clientDao.findByPk(readIdOfClient(scanner)));
                    option = menu(scanner);
                    break;
                case 4:
                    clientDao.delete(readIdOfClient(scanner));
                    option = menu(scanner);
                    break;
                case 5:
                    Client clientToUpdate = new Client();
                    Address addressToUpdate = new Address();
                    clientToUpdate.setId(readIdOfClient(scanner));
                    readPropsOfClient(scanner, clientToUpdate, addressToUpdate);
                    clientDao.update(clientToUpdate);
                    option = menu(scanner);
                    break;
            }
        }
    }
}
