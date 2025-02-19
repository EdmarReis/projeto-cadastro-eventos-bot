package br.com.feltex.bot.telegram.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public record EventoPagamentoDTO(

        String observacao,
        String descricao,
        String data,
        BigDecimal valor,
        String controleEvento,
        Long idEvento

) {
}
