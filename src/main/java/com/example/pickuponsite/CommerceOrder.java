package com.example.pickuponsite;

import java.time.LocalDate;
import java.util.List;

public record CommerceOrder(
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

    PickingUpOnSiteDelivery toPickUpOnsite() {
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

    record OrderItem(
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
