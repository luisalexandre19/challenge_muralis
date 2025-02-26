package br.muralis.challenge.application.component;

import br.muralis.challenge.domain.dto.RequestPipePressureDTO;
import br.muralis.challenge.domain.vo.NotificationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class CheckPressureAnomalyComponent {

    private final BigDecimal minLimit;

    private final BigDecimal maxLimit;

    public CheckPressureAnomalyComponent(@Value("${app.pipe.pressure.limits.min}") BigDecimal minLimit,
                                         @Value("${app.pipe.pressure.limits.max}") BigDecimal maxLimit) {
        super();
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }

    public NotificationVO check(RequestPipePressureDTO requestPipePressure) {

        log.info("Check pressure anomaly from {}", requestPipePressure);

        if (requestPipePressure.value() != null) {

            if (requestPipePressure.value().compareTo(minLimit) < 0) {

                log.info("Notify pressure anomaly from {}", requestPipePressure);

                return NotificationVO.builder().message("Alerta de possível sinal de vazamento!").value(requestPipePressure).build();

            } else if (requestPipePressure.value().compareTo(maxLimit) > 0) {

                log.info("Notify pressure anomaly from {}", requestPipePressure);

                return NotificationVO.builder().message("Alerta de pressão acima do permitido!").value(requestPipePressure).build();
            }
        }

        return null;
    }

}
