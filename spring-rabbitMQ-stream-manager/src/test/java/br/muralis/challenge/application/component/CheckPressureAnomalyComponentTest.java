package br.muralis.challenge.application.component;

import br.muralis.challenge.domain.dto.RequestPipePressureDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;



public class CheckPressureAnomalyComponentTest {

    private CheckPressureAnomalyComponent checkPressureAnomalyComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checkPressureAnomalyComponent = new CheckPressureAnomalyComponent(BigDecimal.TEN, BigDecimal.valueOf(35L));
    }

    @Test
    void check_with_empty_request() {

        //Prepare
        var request = RequestPipePressureDTO.builder().build();

        //ACT
        var notification = checkPressureAnomalyComponent.check(request);

        //Check
        assertNull(notification);
    }

    @Test
    void check_with_empty_value_request() {

        //Prepare
        var request = RequestPipePressureDTO.builder().id("id").build();

        //ACT
        var notification = checkPressureAnomalyComponent.check(request);

        //Check
        assertNull(notification);
    }

    @Test
    void check_without_anomaly_request() {

        //Prepare
        var request = RequestPipePressureDTO.builder().id("id").value(BigDecimal.TEN).build();

        //ACT
        var notification = checkPressureAnomalyComponent.check(request);

        //Check
        assertNull(notification);
    }

    @Test
    void check_with_anomaly_request() {

        //Prepare
        var request = RequestPipePressureDTO.builder().id("id").value(BigDecimal.valueOf(9.99)).build();

        //ACT
        var notification = checkPressureAnomalyComponent.check(request);

        //Check
        assertNotNull(notification);
        assertEquals("Alerta de possível sinal de vazamento!", notification.getMessage());
    }

    @Test
    void check_with_anomaly_request_2() {

        //Prepare
        var request = RequestPipePressureDTO.builder().id("id").value(BigDecimal.valueOf(35.1)).build();

        //ACT
        var notification = checkPressureAnomalyComponent.check(request);

        //Check
        assertNotNull(notification);
        assertEquals("Alerta de pressão acima do permitido!", notification.getMessage());
    }

}
