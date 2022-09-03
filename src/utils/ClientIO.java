package utils;

import model.Address;
import model.Client;
import services.HttpServices;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class ClientIO {
    public void printClients(List<Client> clients) throws ParseException {
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

    public Address readPropsOfClient(Scanner scanner, Client client) throws ParseException, IOException, InterruptedException {
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Address address = new Address();
        out.println("Informe o primeiro nome Cliente: ");
        client.setFullName(scanner.next());
        out.println("Informe a data de nascimento no formato [dd/mm/aaaa]: ");
        client.setDateOfBorn(date.parse(scanner.next()));
        out.println("---- ENDEREÇO -----");
        out.println("Informe o CEP do Cliente:");
        address.setZipCode(scanner.next());
        address = new HttpServices(address.getZipCode()).getAddress().toAddressModel();
        out.println(address.full());
        out.println("Informe o Número do imóvel do Cliente:");
        address.setHomeNumber(scanner.next());

        return address;
    }

    public long readIdOfClient(Scanner scanner) {
        out.println("Informe o ID do Cliente: ");
        return scanner.nextLong();
    }

    public int menu(Scanner scanner) {
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
}
