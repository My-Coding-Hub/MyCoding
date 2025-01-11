package application.bean;

import application.service.EmailService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.regex.Pattern;

@ManagedBean
@ViewScoped
public class ConsultoriaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private EmailService emailService = new EmailService();

    private String nomeModal;
    private String emailModal;
    private String tipoProjetoModal;
    private String empresaModal;
    private String telefoneModal;
    private String descricaoModal;


    // Método de envio de consulta
    public void enviar() {
        if (!validarCamposConsultoria()) {
            // Adicionando mensagem ao contexto
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro",
                            "Todos os campos devem ser validados."));
            return;
        }

        if (!validarFormatoEmail(emailModal)) {
            // Adiciona mensagem de erro específica para email inválido
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro",
                            "E-mail fornecido é inválido."));
            return;
        }

        if (!validarNome(nomeModal)) {
            // Adiciona mensagem de erro específica para nome inválido
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro",
                            "Nome inválido."));
            return;
        }

        System.out.println("Enviando e-mail...");

        String destinatario = "mycodingcontato@gmail.com";
        String assunto = "Nova consulta de: " + nomeModal;

        // Criando uma mensagem HTML com formatação
        String mensagemTexto = "<h1>CONSULTORIA</h1>" +
                "<p><strong><span style='color: green;'>Nome:</span></strong> " + nomeModal + "</p>" +
                "<p><strong><span style='color: green;'>E-mail:</span></strong> " + emailModal + "</p>" +
                "<p><strong><span style='color: green;'>Telefone:</span></strong> " + telefoneModal + "</p>" +
                "<p><strong><span style='color: green;'>Tipo de Projeto:</span></strong> " + tipoProjetoModal + "</p>" +
                "<p><strong><span style='color: blue;'>Mensagem:</span></strong><br/><br/>" + descricaoModal + "</p>";

        emailService.enviarEmail(destinatario, assunto, mensagemTexto);

        resetarCampos();

        // Mensagem de sucesso
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sucesso",
                        "Mensagem enviada."));
    }

    public void resetarCampos() {
        nomeModal = null;
        emailModal = null;
        tipoProjetoModal = null;
        empresaModal = null;
        telefoneModal = null;
        descricaoModal = null;
    }

    private boolean validarCamposConsultoria() {
        return nomeModal != null && !nomeModal.isEmpty() &&
                emailModal != null && !emailModal.isEmpty() &&
                telefoneModal != null && !telefoneModal.isEmpty() &&
                tipoProjetoModal != null && !tipoProjetoModal.isEmpty() &&
                descricaoModal != null && !descricaoModal.isEmpty();
    }

    private boolean validarFormatoEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Regex atualizada para evitar dois pontos consecutivos
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.compile(emailRegex).matcher(email).matches()
                && !email.contains("..");
    }

    // Validação do nome (somente letras e espaços)
    private boolean validarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return false;
        }
        // Regex que permite apenas letras e espaços
        String nomeRegex = "^[A-Za-zÀ-ÿ\\s]+$";
        return Pattern.compile(nomeRegex).matcher(nome).matches();
    }

    // Getters e Setters
    public String getNomeModal() {
        return nomeModal;
    }

    public void setNomeModal(String nomeModal) {
        this.nomeModal = nomeModal;
    }

    public String getEmailModal() {
        return emailModal;
    }

    public void setEmailModal(String emailModal) {
        this.emailModal = emailModal;
    }

    public String getTipoProjetoModal() {
        return tipoProjetoModal;
    }

    public void setTipoProjetoModal(String tipoProjetoModal) {
        this.tipoProjetoModal = tipoProjetoModal;
    }

    public String getEmpresaModal() {
        return empresaModal;
    }

    public void setEmpresaModal(String empresaModal) {
        this.empresaModal = empresaModal;
    }

    public String getTelefoneModal() {
        return telefoneModal;
    }

    public void setTelefoneModal(String telefoneModal) {
        this.telefoneModal = telefoneModal;
    }

    public String getDescricaoModal() {
        return descricaoModal;
    }

    public void setDescricaoModal(String descricaoModal) {
        this.descricaoModal = descricaoModal;
    }
}
