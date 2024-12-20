package com.example.pickuponsite;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryPickUpOnsiteRepository implements PickUpOnsiteRepository {
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
