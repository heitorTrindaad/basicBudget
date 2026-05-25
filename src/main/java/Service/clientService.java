package Service;

import Model.Client;
import Repository.ClientRepositoryMemory;
import java.util.List;

public class clientService {

    private static clientService instance;
    private final ClientRepositoryMemory repository;

    private clientService() {
        this.repository = new ClientRepositoryMemory();
    }

    public static synchronized clientService getInstance() {
        if (instance == null) {
            instance = new clientService();
        }
        return instance;
    }

    public void save(Client client) {
        repository.save(client);
    }

    public List<Client> findAll() {
        return repository.findAll();
    }
}