
package com.codegnan.app.ecommercebackendmain.dto;

public record CustomerResponse(
        int id,
        String name,
        String email,
        String token
) {
}
