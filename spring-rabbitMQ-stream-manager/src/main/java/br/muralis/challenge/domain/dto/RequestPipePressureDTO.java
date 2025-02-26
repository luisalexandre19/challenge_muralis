package br.muralis.challenge.domain.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestPipePressureDTO(
        String id,
        BigDecimal value) {
}
