package com.example.pickuponsite;

class CreatePickUpOnsiteController {

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
