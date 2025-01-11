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
public class DuvidasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private EmailService emailService = new EmailService();

    private String nomeDuvidas;
    private String emailDuvidas;
    private String telefoneDuvidas;
    private String descricaoDuvidas;

    // Método de envio de dúvidas
    public void enviarDuvidas() {
        if (!validarCamposDuvidas()) {
            // Adicionando mensagem ao contexto
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro",
                            "Todos os campos devem ser validados."));
            return;
        }

        if (!validarFormatoEmail(emailDuvidas)) {
            // Adiciona mensagem de erro específica para email inválido
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro",
                            "E-mail fornecido é inválido."));
            return;
        }

        if (!validarNome(nomeDuvidas)) {
            // Adiciona mensagem de erro específica para nome inválido
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro",
                            "Nome inválido."));
            return;
        }

        System.out.println("Enviando e-mail...");

        String destinatario = "mycodingcontato@gmail.com";
        String assunto = "Nova dúvida de: " + nomeDuvidas;

        // Criando uma mensagem HTML com formatação
        String mensagemTexto = "<h1>DÚVIDAS</h1>" +
                "<p><strong><span style='color: green;'>Nome:</span></strong> " + nomeDuvidas + "</p>" +
                "<p><strong><span style='color: green;'>E-mail:</span></strong> " + emailDuvidas + "</p>" +
                "<p><strong><span style='color: green;'>Telefone:</span></strong> " + telefoneDuvidas + "</p>" +
                "<p><strong><span style='color: blue;'>Mensagem:</span></strong><br/><br/>" + descricaoDuvidas + "</p>";

        emailService.enviarEmail(destinatario, assunto, mensagemTexto);

        resetarCamposDuvidas();

        // Mensagem de sucesso
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sucesso",
                        "Mensagem enviada."));
    }

    public void resetarCamposDuvidas() {
        nomeDuvidas = null;
        emailDuvidas = null;
        telefoneDuvidas = null;
        descricaoDuvidas = null;
    }

    private boolean validarCamposDuvidas() {
        return nomeDuvidas != null && !nomeDuvidas.isEmpty() &&
                emailDuvidas != null && !emailDuvidas.isEmpty() &&
                telefoneDuvidas != null && !telefoneDuvidas.isEmpty() &&
                descricaoDuvidas != null && !descricaoDuvidas.isEmpty();
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

    public String getNomeDuvidas() {
        return nomeDuvidas;
    }

    public void setNomeDuvidas(String nomeDuvidas) {
        this.nomeDuvidas = nomeDuvidas;
    }

    public String getEmailDuvidas() {
        return emailDuvidas;
    }

    public void setEmailDuvidas(String emailDuvidas) {
        this.emailDuvidas = emailDuvidas;
    }

    public String getTelefoneDuvidas() {
        return telefoneDuvidas;
    }

    public void setTelefoneDuvidas(String telefoneDuvidas) {
        this.telefoneDuvidas = telefoneDuvidas;
    }

    public String getDescricaoDuvidas() {
        return descricaoDuvidas;
    }

    public void setDescricaoDuvidas(String descricaoDuvidas) {
        this.descricaoDuvidas = descricaoDuvidas;
    }
}
