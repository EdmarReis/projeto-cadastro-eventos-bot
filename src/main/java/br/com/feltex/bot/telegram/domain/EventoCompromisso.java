package br.com.feltex.bot.telegram.domain;

import br.com.feltex.bot.telegram.domain.dto.EventoCompromissoDTO;
import br.com.feltex.bot.telegram.domain.dto.EventoPagamentoDTO;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class EventoCompromisso {

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

    private LocalTime horario;

    private String controleEvento;

    private Long idEvento;


    public EventoCompromisso toEventoCompromisso(EventoCompromissoDTO eventoCompromissoDTO){
        return EventoCompromisso.builder()
                .observacao(eventoCompromissoDTO.observacao())
                .descricao(eventoCompromissoDTO.descricao())
                .data(eventoCompromissoDTO.data())
                .horario(eventoCompromissoDTO.horario())
                .controleEvento(eventoCompromissoDTO.controleEvento())
                .idEvento(eventoCompromissoDTO.idEvento())
                .build();
    }

}
