package com.example.pickuponsite;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
class MemoryCommerceOrderRepository implements CommerceOrderRepository {
    CommerceOrder getBy(final String deliverySequence) {
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
