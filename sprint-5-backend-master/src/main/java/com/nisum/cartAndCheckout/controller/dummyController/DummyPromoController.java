package com.nisum.cartAndCheckout.controller.dummyController;

import com.nisum.cartAndCheckout.dto.response.PromoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dummy/cart")
public class DummyPromoController {

    @PostMapping("/promos")
    public List<PromoResponse> getDummyPromos(@RequestBody List<Integer> productIds) {
        List<PromoResponse> promoList = new ArrayList<>();

        for (Integer productId : productIds) {
            PromoResponse promo = new PromoResponse();
            promo.setPromoCode("PROMO" + productId);
            promo.setPromoType("FLAT_DISCOUNT");
            promo.setDescription("Flat discount on product " + productId);
            promo.setAmount(100); // Fixed discount amount for testing
            promoList.add(promo);
        }

        return promoList;
    }
}
