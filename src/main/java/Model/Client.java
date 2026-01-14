package Model;

public class Client {
    private int id;
    private String name;
    private String email;
    private String cnpj;
    private String telefone;

    public Client() {
    }

    public Client(String name, String email, String cnpj, String telefone) {
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return name;
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
}
