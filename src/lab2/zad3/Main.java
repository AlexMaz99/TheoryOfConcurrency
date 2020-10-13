package lab2.zad3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CountingSemaphore countingSemaphore = new CountingSemaphore(4);
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 10; i ++){
            Client client = new Client(countingSemaphore);
            clients.add(client);
            client.start();
        }

        for (Client client: clients)
            client.join();
    }
}
