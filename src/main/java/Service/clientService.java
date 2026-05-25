package Service;

import Model.Client;
import Repository.ClientRepositoryMemory; // Garanta que o pacote do seu repositório está correto
import java.util.List;

public class clientService {

    // 1. Atributo estático que guardará a ÚNICA instância desta classe na aplicação
    private static clientService instance;

    // 2. O repositório agora é instanciado APENAS UMA VEZ aqui dentro
    private final ClientRepositoryMemory repository;

    // 3. Construtor PRIVADO: impede que outras classes usem "new clientService()"
    private clientService() {
        this.repository = new ClientRepositoryMemory();
    }

    // 4. Método global para obter a instância única
    public static synchronized clientService getInstance() {
        if (instance == null) {
            instance = new clientService();
        }
        return instance;
    }

    // Métodos de negócio que apenas repassam a ordem para o repositório único
    public void save(Client client) {
        repository.save(client);
    }

    public List<Client> findAll() {
        return repository.findAll();
    }
}