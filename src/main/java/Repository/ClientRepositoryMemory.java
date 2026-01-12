package Repository;

import Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientRepositoryMemory implements ClientRepository {

    private final List<Client> clients = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Client save(Client client) {
        if(client.getId() == 0){
            client.setId(idCounter.getAndIncrement());
        }
        clients.add(client);
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
    }
}
