package Model;

public class clientesModel {
    public class Cliente {

        private int id;                 // ID auto gerado no banco
        private String nome;            // Nome do cliente
        private String telefone;        // Telefone para contato
        private String email;           // E-mail do cliente
        private String endereco;        // Endereço completo
        private String documento;       // CPF ou CNPJ
        private String observacoes;     // Observações gerais

        public Cliente() {
        }

        public Cliente(String nome, String telefone, String email, String endereco, String documento, String observacoes) {
            this.nome = nome;
            this.telefone = telefone;
            this.email = email;
            this.endereco = endereco;
            this.documento = documento;
            this.observacoes = observacoes;
        }

        // GETTERS e SETTERS
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEndereco() {
            return endereco;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getObservacoes() {
            return observacoes;
        }

        public void setObservacoes(String observacoes) {
            this.observacoes = observacoes;
        }
    }

}
