package br.com.feltex.bot.telegram.controller;

import br.com.feltex.bot.telegram.domain.EventoCompromisso;
import br.com.feltex.bot.telegram.domain.EventoPagamento;
import br.com.feltex.bot.telegram.domain.dto.EventoCompromissoDTO;
import br.com.feltex.bot.telegram.domain.dto.EventoPagamentoDTO;
import br.com.feltex.bot.telegram.service.EnviaMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enviar")
public class BotController {

    @Autowired
    private EventoPagamento eventoPagamento;

    @Autowired
    private EventoCompromisso eventoCompromisso;

    @Autowired
    private EnviaMsg enviaMsg;

    @PostMapping("/msg/pagamento")
    public ResponseEntity<?> recebeMsg(@RequestBody EventoPagamentoDTO eventoPagamentoDTO) {

        enviaMsg.enviarMsg(enviaMsg.formataMsg(eventoPagamento.toEventoPagamento(eventoPagamentoDTO)));

        return ResponseEntity.ok(eventoPagamento.toEventoPagamento(eventoPagamentoDTO));
    }

    @PostMapping("/msg/compromisso")
    public ResponseEntity<?> recebeMsgCompromisso(@RequestBody EventoCompromissoDTO eventoCompromissoDTO) {

        enviaMsg.enviarMsgCompromisso(enviaMsg.formataMsgCompromisso(eventoCompromisso.toEventoCompromisso(eventoCompromissoDTO)));

        return ResponseEntity.ok(eventoCompromisso.toEventoCompromisso(eventoCompromissoDTO));
    }

}
