package br.com.feltex.bot.telegram.domain;

import br.com.feltex.bot.telegram.domain.dto.EventoCompromissoDTO;
import br.com.feltex.bot.telegram.domain.dto.EventoPagamentoDTO;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class EventoPagamento {

    @NotBlank
    @NotEmpty
    @NotNull
    private String observacao;

    @NotBlank
    @NotEmpty
    @NotNull
    private String descricao;

    @NotBlank
    @NotEmpty
    @NotNull
    private String data;

    private BigDecimal valor;

    private String controleEvento;

    private Long idEvento;


    public EventoPagamento toEventoPagamento(EventoPagamentoDTO eventoPagamentoDTO){
        return EventoPagamento.builder()
                .observacao(eventoPagamentoDTO.observacao())
                .descricao(eventoPagamentoDTO.descricao())
                .data(eventoPagamentoDTO.data())
                .valor(eventoPagamentoDTO.valor())
                .controleEvento(eventoPagamentoDTO.controleEvento())
                .idEvento(eventoPagamentoDTO.idEvento())
                .build();
    }

}
