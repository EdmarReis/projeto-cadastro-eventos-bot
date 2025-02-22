package br.com.feltex.bot.telegram.service;

import br.com.feltex.bot.telegram.constants.DadosBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EchoBot extends TelegramLongPollingBot {

    private RestTemplate restTemplate;

    @Override
    public String getBotUsername() {
        return DadosBot.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return DadosBot.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var mensagem = responder(update);
            try {
                execute(mensagem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage responder(Update update) {
        var textoMensagem = update.getMessage().getText().toLowerCase();
        var chatId = update.getMessage().getChatId().toString();

        log.info("[Bot-eventos] Recebi uma mensagem com texto {} e id {}",
                update.getMessage().getText(), update.getMessage().getMessageId());

        var resposta = switch (textoMensagem) {
            case "data" -> getData();
            case "hora" -> getHora();
            case "/compromissos" -> getCompromissos();
            case "/pagamentos" -> getPagamentos();
            case "status" -> "Estou operacional";
            case "ola", "olá", "oi" -> "\uD83E\uDD16 Olá, vejo que você entende muito sobre BOTS!";
            case "quem é você", "quem e voce" -> "\uD83E\uDD16 Eu sou um bot";
            case "/help" -> "Utilize um dos comandos:\nolá\ndata\nhora\nquem é você?\nfinalizar id {número}";
            default -> {
                if (textoMensagem.startsWith("finalizar id ")) {
                    String id = textoMensagem.replace("finalizar id ", "").trim();
                    yield finalizarEvento(id);
                } else {
                    yield "Não entendi!\nDigite /help para ver os comandos disponíveis.";
                }
            }
        };

        log.info("[Bot-eventos] Respondi a mensagem: " + resposta);

        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();
    }

    private String getCompromissos() {

        try {

            String url = "http://localhost:8085/envio/compromissos";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> entity = new HttpEntity<>(null, headers);
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.postForObject(url, entity, String.class);
        } catch (NumberFormatException e) {
            return "Erro para enviar compromissos";
        } catch (Exception e) {
            return "Erro para enviar compromissos: " + e.getMessage();
        }
    }

    private String getPagamentos() {

        try {

            String url = "http://localhost:8085/envio/pagamentos";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> entity = new HttpEntity<>(null, headers);
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.postForObject(url, entity, String.class);
        } catch (NumberFormatException e) {
            return "Erro para enviar pagamentos";
        } catch (Exception e) {
            return "Erro para enviar pagamentos: " + e.getMessage();
        }

    }

    private String getData() {
        return "A data atual é: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    private String getHora() {
        return "A hora atual é: " + new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    private String finalizarEvento(String id) {
        try {

            String url = "http://localhost:8085/finalizar/" + id;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // Pode ser omitido se não houver JSON no corpo

            HttpEntity<Void> entity = new HttpEntity<>(null, headers); // Corpo vazio
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.postForObject(url, entity, String.class);
        } catch (NumberFormatException e) {
            return "Erro: O ID precisa ser um número válido.";
        } catch (Exception e) {
            return "Erro ao finalizar evento: " + e.getMessage();
        }
    }

}

