package Repository;

import Model.Client;

import java.util.List;

public interface ClientRepository {
        Client save(Client client);
        Client findById(int id);
        List<Client> findAll();
        void delete(int id);
}
