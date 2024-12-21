package com.example.pickuponsite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 커머스에서 결제를 완료한 고객을 대상으로, 현장수령을 등록합니다.
 *
 * @checklist
 * - [v] 배송번호 바코드를 입력하여 현장수령을 등록한다
 * - [v] 주문 이력이 없다면 현장수령을 등록할 수 없다
 * - [ ] 이미 현장수령이 등록된 주문은 현장수령을 등록할 수 없다
 */
public class RegisterPickUpOnsiteTest {
    private final MemoryPickUpOnsiteRepository pickUpOnsiteRepository = new MemoryPickUpOnsiteRepository();
    private final CreatePickUpOnsiteController sut = new CreatePickUpOnsiteController(new MemoryCommerceOrderRepository(), pickUpOnsiteRepository);

    @Test
    void 고객의_배송번호_바코드를_찍어서_현장수령을_등록한다() {
        final String deliverySequence = "delivery20241225";

        sut.register(deliverySequence);

        assertDelivery(deliverySequence);
    }

    @Test
    void 주문이력이_없다면_현장수령을_등록할_수_없다() {
        final String deliverySequence = "illegalDeliverySequence";

        assertThrows(AssertionError.class, () -> sut.register(deliverySequence));
    }

    @Test
    void 이미_현장수령이_등록된_주문은_현장수령을_등록할_수_없다() {
        final String deliverySequence = "delivery20241225";

        sut.register(deliverySequence);

        assertThrows(AssertionError.class, () -> sut.register(deliverySequence));
    }

    private void assertDelivery(final String deliverySequence) {
        final PickingUpOnSiteDelivery delivery = pickUpOnsiteRepository.getBy(deliverySequence);
        JsonApprovals.verifyJson(toString(delivery));
    }

    private String toString(final PickingUpOnSiteDelivery delivery) {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(delivery);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
