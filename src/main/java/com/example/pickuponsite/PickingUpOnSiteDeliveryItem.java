package com.example.pickuponsite;

public class PickingUpOnSiteDeliveryItem {
    Long goodsId;
    String goodsCode;
    String barcode;
    String offlineBarcode;
    String imageUrl;
    Long qty;

    private PickingUpOnSiteDeliveryItem(final Long goodsId, final String goodsCode, final String barcode, final String offlineBarcode, final String imageUrl, final Long qty) {
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.barcode = barcode;
        this.offlineBarcode = offlineBarcode;
        this.imageUrl = imageUrl;
        this.qty = qty;
    }
}
