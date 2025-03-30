package br.com.feltex.bot.telegram.service;

import br.com.feltex.bot.telegram.domain.EventoCompromisso;
import br.com.feltex.bot.telegram.domain.EventoPagamento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class EnviaMsg {

    private final EchoBot bot;

    private final String chatId = "";

    public EnviaMsg(EchoBot bot) {
        this.bot = bot;
    }

    public void enviarMsg(String mensagem) {

        log.info("[Bot-eventos] Método de envio de mensagem para o telegram chamado");

        SendMessage msg = SendMessage.builder()
                .chatId(chatId)
                .text(mensagem)
                .build();

        try {
            bot.execute(msg);
            log.info("[Bot-eventos] Evento enviado! "+ mensagem);
        } catch (TelegramApiException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
            log.error("[Bot-eventos] Falha ao enviar a mensagem! " + e);
        }
    }

    public String formataMsg(EventoPagamento eventoPagamento){
        return eventoPagamento.getObservacao() + "\n" + "\n"
                + "Descrição: " + eventoPagamento.getDescricao() + "\n"
                + "Data: " + eventoPagamento.getData() + "\n"
                + "Valor: " + eventoPagamento.getValor() + "\n"
                + "Ocorrencia: " + eventoPagamento.getControleEvento() + "\n"
                + "ID evento "+ eventoPagamento.getIdEvento();
    }

    public void enviarMsgCompromisso(String mensagem) {
        log.info("[Bot-eventos] Método de envio de compromisso para o telegram chamado");

        SendMessage msg = SendMessage.builder()
                .chatId(chatId)
                .text(mensagem)
                .build();

        try {
            bot.execute(msg);
            log.info("[Bot-eventos] Compromisso enviado! "+ mensagem);
        } catch (TelegramApiException e) {
            System.err.println("Erro ao enviar compromisso: " + e.getMessage());
            log.error("[Bot-eventos] Falha ao enviar o compromisso! " + e);
        }
    }

    public String formataMsgCompromisso(EventoCompromisso eventoCompromisso) {
        return eventoCompromisso.getObservacao() + "\n" + "\n"
                + "Descrição: " + eventoCompromisso.getDescricao() + "\n"
                + "Data: " + eventoCompromisso.getData() + "\n"
                + "Horario: " + eventoCompromisso.getHorario() + "\n"
                + "Ocorrencia: " + eventoCompromisso.getControleEvento() + "\n"
                + "ID evento "+ eventoCompromisso.getIdEvento();
    }
}

