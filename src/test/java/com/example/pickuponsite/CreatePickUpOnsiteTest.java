package com.example.pickuponsite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 커머스에서 결제를 완료한 고객을 대상으로, 현장수령을 등록합니다.
 *
 * @checklist
 * - [v] 배송번호 바코드를 입력하여 현장수령을 등록한다
 * - [v] 주문 이력이 없다면 현장수령을 등록할 수 없다
 * - [ ] 이미 현장수령이 등록된 주문은 현장수령을 등록할 수 없다
 */
public class CreatePickUpOnsiteTest {
    private final PickUpOnsiteRepository pickUpOnsiteRepository = new PickUpOnsiteRepository();
    private final CreatePickUpOnsiteController sut = new CreatePickUpOnsiteController(new CommerceOrderRepository(), pickUpOnsiteRepository);

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

    private class CreatePickUpOnsiteController {

        private final CommerceOrderRepository commerceOrderRepository;
        private final PickUpOnsiteRepository pickUpOnsiteRepository;

        public CreatePickUpOnsiteController(final CommerceOrderRepository commerceOrderRepository, final PickUpOnsiteRepository pickUpOnsiteRepository) {
            this.commerceOrderRepository = commerceOrderRepository;
            this.pickUpOnsiteRepository = pickUpOnsiteRepository;
        }

        void register(final String deliverySequence) {
            final CommerceOrder commerceOrder = commerceOrderRepository.getBy(deliverySequence);

            final PickingUpOnSiteDelivery pickUpOnsiteDelivery = commerceOrder.toPickUpOnsite();

            pickUpOnsiteRepository.save(pickUpOnsiteDelivery);
        }
    }

    private record CommerceOrder(
            Long id,
            String deliverySequence,
            String status,
            String zone,
            LocalDate requestedPickingDate, // 요청수령일
            LocalDate receivedDate, // 수령완료일
            String userId,
            String userName,
            String userPhone,
            List<OrderItem> items
    ) {

        public PickingUpOnSiteDelivery toPickUpOnsite() {
            final PickingUpOnSiteDelivery pickUpOnsiteDelivery = new PickingUpOnSiteDelivery(
                    id,
                    deliverySequence,
                    status,
                    zone,
                    requestedPickingDate,
                    receivedDate,
                    userId,
                    userName,
                    userPhone
            );
            pickUpOnsiteDelivery.assignItems(items.stream().map(OrderItem::toPickUpOnsiteItem).toList());
            return pickUpOnsiteDelivery;
        }

        private record OrderItem(
                Long goodsId,
                String goodsCode,
                String barcode,
                String offlineBarcode,
                String imageUrl,
                Long qty
        ) {
            private PickingUpOnSiteDeliveryItem toPickUpOnsiteItem() {
                return new PickingUpOnSiteDeliveryItem(goodsId, goodsCode, barcode, offlineBarcode, imageUrl, qty);
            }
        }
    }

    private class PickUpOnsiteRepository {
        private final Map<String, PickingUpOnSiteDelivery> storage = new HashMap<>();

        public PickingUpOnSiteDelivery getBy(final String deliverySequence) {
            assert storage.containsKey(deliverySequence) : "No delivery sequence";
            return storage.get(deliverySequence);
        }

        public void save(final PickingUpOnSiteDelivery pickUpOnsiteDelivery) {
            assert !storage.containsKey(pickUpOnsiteDelivery.getDeliverySequence()) : "Already exists";
            storage.put(pickUpOnsiteDelivery.getDeliverySequence(), pickUpOnsiteDelivery);
        }
    }

    private class CommerceOrderRepository {
        public CommerceOrder getBy(final String deliverySequence) {
            assert !Objects.equals(deliverySequence, "illegalDeliverySequence") : "No delivery sequence";
            return new CommerceOrder(
                    1L,
                    deliverySequence,
                    "ORDERED",
                    "ZONE_A",
                    LocalDate.of(2024, 12, 25),
                    null,
                    "user1",
                    "홍길동",
                    "01012345678",
                    List.of(new CommerceOrder.OrderItem(1L, "goods1", "barcode1", "offlineBarcode1", "imageUrl1", 1L))
            );
        }
    }
}
