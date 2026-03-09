package Repository;

import Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientRepositoryMemory implements ClientRepository {

    private static final List<Client> clients = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public ClientRepositoryMemory() {
        if (clients.isEmpty()) {
            List<Client> loaded = ClientJsonStorage.loadFromFile();
            if (!loaded.isEmpty()) {
                this.setAll(loaded);
            }
        }
    }

    public Client save(Client client) {
        if (client.getId() == 0) {
            client.setId(idCounter.getAndIncrement());
            clients.add(client);
            ClientJsonStorage.saveToFile(clients);
        } else {
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getId() == client.getId()) {
                    clients.set(i, client);
                    return client;
                }
            }
            clients.add(client);
            ClientJsonStorage.saveToFile(clients);
        }
        return client;
    }

    public Client findById(int id){
        return clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Client> findAll(){
        return clients;
    }

    public void setAll(List<Client> list){
        clients.clear();
        clients.addAll(list);

        int maiorId = list.stream()
                .mapToInt(Client::getId)
                .max()
                .orElse(0);

        idCounter.set(maiorId + 1);
    }


    public void delete(int id){
        clients.removeIf(c -> c.getId() == id);
        ClientJsonStorage.saveToFile(clients);
    }
}
