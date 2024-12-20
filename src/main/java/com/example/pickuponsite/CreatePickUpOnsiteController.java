package com.example.pickuponsite;

class CreatePickUpOnsiteController {

    private final MemoryCommerceOrderRepository commerceOrderRepository;
    private final MemoryPickUpOnsiteRepository pickUpOnsiteRepository;

    public CreatePickUpOnsiteController(final MemoryCommerceOrderRepository commerceOrderRepository, final MemoryPickUpOnsiteRepository pickUpOnsiteRepository) {
        this.commerceOrderRepository = commerceOrderRepository;
        this.pickUpOnsiteRepository = pickUpOnsiteRepository;
    }

    void register(final String deliverySequence) {
        final CommerceOrder commerceOrder = commerceOrderRepository.getBy(deliverySequence);

        final PickingUpOnSiteDelivery pickUpOnsiteDelivery = commerceOrder.toPickUpOnsite();

        pickUpOnsiteRepository.save(pickUpOnsiteDelivery);
    }
}
