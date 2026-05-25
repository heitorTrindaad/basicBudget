package Repository;

import Model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientRepositoryMemory implements ClientRepository {
    private final List<Client> clients;
    private final AtomicInteger idCounter;

    public ClientRepositoryMemory() {
        this.clients = new ArrayList<>(ClientJsonStorage.loadFromFile());
        int maxId = clients.stream().mapToInt(Client::getId).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId + 1);
    }

    @Override
    public void save(Client client) {
        client.setId(idCounter.getAndIncrement());
        clients.add(client);
        ClientJsonStorage.saveToFile(clients);
    }

    @Override
    public void update(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == client.getId()) {
                clients.set(i, client);
                ClientJsonStorage.saveToFile(clients);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        clients.removeIf(c -> c.getId() == id);
        ClientJsonStorage.saveToFile(clients);
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clients);
    }

    @Override
    public Client findById(int id) {
        return clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}