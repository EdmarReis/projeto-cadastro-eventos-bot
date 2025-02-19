package br.com.feltex.bot.telegram;

import br.com.feltex.bot.telegram.service.EchoBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@SpringBootApplication
public class BotApplication {

    private static final String CHAT_ID = "387938080"; // Substitua pelo chatId correto
    private static EchoBot bot;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
        iniciaBot();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("[Bot-eventos] Aplicação está sendo finalizada...");
            paraBot();
        }));
    }

    public static void iniciaBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            bot = new EchoBot();
            telegramBotsApi.registerBot(bot);

            enviarMensagem("🤖 Bot iniciado com sucesso!");
            log.info("[Bot-eventos] Aplicação iniciada.");
        } catch (TelegramApiException e) {
            log.error("[Bot-eventos] Erro ao registrar o bot do Telegram", e);
        } catch (Exception e) {
            log.error("[Bot-eventos] Erro inesperado ao iniciar o bot", e);
        }
    }

    public static void paraBot() {
        enviarMensagem("🤖 O Bot foi encerrado.");
    }

    private static void enviarMensagem(String mensagem) {
        if (bot == null) {
            log.warn("[Bot-eventos] Bot não foi inicializado corretamente. Mensagem não enviada.");
            return;
        }

        try {
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(CHAT_ID)
                    .text(mensagem)
                    .build();

            bot.execute(sendMessage);
            log.info("[Bot-eventos] Mensagem enviada: {}", mensagem);
        } catch (TelegramApiException e) {
            log.error("[Bot-eventos] Erro ao enviar mensagem para o Telegram", e);
        }
    }
}
