package br.muralis.challenge.domain.dto;

import java.math.BigDecimal;

public record RequestHydrantReadingDTO(
        String id,
        String idClient,
        BigDecimal value) {
}
