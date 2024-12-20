package com.example.pickuponsite;

import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 커머스에서 결제를 완료한 고객을 대상으로, 현장수령을 등록합니다.
 *
 * @checklist - [ ] 배송번호 바코드를 입력하여 현장수령을 등록한다
 * - [ ] 주문 이력이 없다면 현장수령을 등록할 수 없다
 * - [ ] 이미 현장수령이 등록된 주문은 현장수령을 등록할 수 없다
 */
public class CreatePickUpOnsiteTest {
    private final CreatePickUpOnsiteController sut = new CreatePickUpOnsiteController();
    private final PickUpOnsiteRepository pickUpOnsiteRepository = new PickUpOnsiteRepository();

    @Test
    void 고객의_배송번호_바코드를_찍어서_현장수령을_등록한다() {
        final String deliverySequence = "delivery20241225";

        sut.register(deliverySequence);

        JsonApprovals.verifyAsJson(pickUpOnsiteRepository.getBy(deliverySequence));
    }

    private class CreatePickUpOnsiteController {
        public void register(final String deliverySequence) {
            throw new UnsupportedOperationException("Unsupported register");
        }
    }

    private class PickUpOnsiteRepository {
        private final Map<String, PickingUpOnSiteDelivery> storage = new HashMap<>();

        public PickingUpOnSiteDelivery getBy(final String deliverySequence) {
            assert storage.containsKey(deliverySequence) : "No delivery sequence";
            return storage.get(deliverySequence);
        }
    }
}
