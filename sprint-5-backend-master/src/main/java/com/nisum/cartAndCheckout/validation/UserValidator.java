package com.nisum.cartAndCheckout.validation;

import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.exception.UserNotFoundException;
import static com.nisum.cartAndCheckout.constants.AppConstants.USER_NOT_LOGGED_IN;
import jakarta.servlet.http.HttpSession;
import static com.nisum.cartAndCheckout.constants.AppConstants.USER_ADDRESS_NOT_FOUND;
import java.util.Optional;

public class UserValidator {

    public static UserAddress validateUserAddress(Optional<UserAddress> optionalAddress) {
        return optionalAddress.orElseThrow(() -> new UserNotFoundException(USER_ADDRESS_NOT_FOUND));
    }

    public static Integer getValidatedUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");

        if (userId == null) {
            //userId =1;
            throw new UserNotFoundException(USER_NOT_LOGGED_IN);
        }

        return (Integer) userId;
    }
}