import dao.ClientDao;
import database.Database;
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
                    "\nNascimento: " + simpleDateFormat.format(client.getDateOfBorn())
            );
            out.println("---------------------------------");
        }
    }
    public static void readPropsOfClient(Scanner scanner, Client client) throws ParseException{
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        out.println("Informe o primeiro nome Cliente: ");
            client.setFullName(scanner.next());
        out.println("Informe a data de nascimento no formato [dd/mm/aaaa]: ");
            client.setDateOfBorn(date.parse(scanner.next()));
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
        ClientDao clientDao = new ClientDao(Database.createConnection());

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
                    readPropsOfClient(scanner, client);
                    clientDao.create(client);
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
                    clientToUpdate.setId(readIdOfClient(scanner));
                    readPropsOfClient(scanner, clientToUpdate);
                    clientDao.update(clientToUpdate);
                    option = menu(scanner);
                    break;
            }
        }
    }
}
