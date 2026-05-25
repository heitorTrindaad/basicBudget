package Repository;

import Model.Client;
import java.util.List;

public interface ClientRepository {
        void save(Client client);

        void update(Client client);

        void delete(int id);

        List<Client> findAll();

        Client findById(int id);
}