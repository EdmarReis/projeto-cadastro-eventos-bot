package br.com.feltex.bot.telegram.domain.dto;

import java.time.LocalTime;

public record EventoCompromissoDTO(

        String observacao,
        String descricao,
        String data,
        LocalTime horario,
        String controleEvento,
        Long idEvento

) {
}
