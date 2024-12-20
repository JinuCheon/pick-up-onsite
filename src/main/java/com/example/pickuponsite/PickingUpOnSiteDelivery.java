package com.example.pickuponsite;

import java.time.LocalDate;
import java.util.List;

public class PickingUpOnSiteDelivery {
    private final Long id;
    private final String code;
    private final String status;
    private final String zone;
    private final LocalDate requestedPickingDate; // 요청수령일
    private final LocalDate receivedDate; // 수령완료일
    private final String userId;
    private final String userName;
    private final String userPhone;
    private List<PickingUpOnSiteDeliveryItem> items;

    private PickingUpOnSiteDelivery(final Long id, final String code, final String status, final String zone, final LocalDate requestedPickingDate, final LocalDate receivedDate, final String userId, final String userName, final String userPhone) {
        this.id = id;
        this.code = code;
        this.status = status;
        this.zone = zone;
        this.requestedPickingDate = requestedPickingDate;
        this.receivedDate = receivedDate;
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public void assignItems(final List<PickingUpOnSiteDeliveryItem> items) {
        this.items = items;
    }
}