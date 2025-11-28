package Repository;

import Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class clientRepository {
    private final List<Client> clients = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);;

    public void save(Client client) {
        if(client.getId() == 0){
            client.setId(idCounter.getAndIncrement());
        }
        this.clients.add(client);
    }

    public Client findById(int id){
        for(Client budget : clients){
            if(budget.getId() == id){
                return clients.get(id);
            }
        }
        return null;
    }

    public void update(int id, Client client){
        Client existstingClient = findById(id);
        if(existstingClient != null){
            existstingClient.setName(client.getName());
            existstingClient.setEmail(client.getEmail());
        }
    }

    public void remove(int id){
        Client client = findById(id);
        if(client != null){
            clients.remove(client);
        }

    }

}
