package lab02.zad3;

import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) throws InterruptedException {
        CountingSemaphore countingSemaphore = new CountingSemaphore(4);
        Counter counter = new Counter(0);
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Client client = new Client(countingSemaphore, counter);
            clients.add(client);
        }
        clients.stream().forEach(Client::start);
        for (Client client : clients)
            client.join();
        System.out.println("stan=" + counter.value());

    }
}
