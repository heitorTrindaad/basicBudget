package Model;

public class Client {
    private int id;
    private String name;
    private String email;
    private String cnpj;
    private String telefone;

    public Client() {
    }

    public Client(int id, String name, String email, String cnpj, String telefone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return name;
    }
}