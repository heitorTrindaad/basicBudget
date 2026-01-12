package Service;

import Model.Client;
import Repository.ClientRepositoryMemory;

public class clientService {
    private final ClientRepositoryMemory clientRepository;

    public clientService(ClientRepositoryMemory clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client createClient(String name, String email){
        if (name==null || name.isBlank()){
            throw new IllegalArgumentException("Client name wasnt filled.");
        } //hi
        if (email==null || email.isBlank()) {
            throw new IllegalArgumentException("Invalid Email.");
        }
        Client client = new Client(name, email);
        client.setName(name.trim());
        client.setEmail(email.trim());

        return clientRepository.save(client);
    }

    public Client updateClient(int id, String name, String email){
        Client client = clientRepository.findById(id);

        if (client == null) {
            throw new IllegalArgumentException("Client not found.");
        }

        if (name ==null || name.isBlank()){
            throw new IllegalArgumentException("Client name wasnt filled.");
        }

        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("Email is invalid.");
        }

        client.setName(name.trim());
        client.setEmail(email.trim());

        return clientRepository.save(client);
    }

    public void deleteClient(int id){
        clientRepository.delete(id);
    }

}
