package com.example.pickuponsite;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class CreatePickUpOnsiteController {

    private final MemoryCommerceOrderRepository commerceOrderRepository;
    private final MemoryPickUpOnsiteRepository pickUpOnsiteRepository;

    @PostMapping("/pick-up-onsite")
    void register(final String deliverySequence) {
        final CommerceOrder commerceOrder = commerceOrderRepository.getBy(deliverySequence);

        final PickingUpOnSiteDelivery pickUpOnsiteDelivery = commerceOrder.toPickUpOnsite();

        pickUpOnsiteRepository.save(pickUpOnsiteDelivery);
    }
}
