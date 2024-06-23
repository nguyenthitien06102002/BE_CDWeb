package net.javaguides.CD_Web_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.CD_Web_backend.entity.Orders;
import net.javaguides.CD_Web_backend.entity.Products;
import net.javaguides.CD_Web_backend.entity.Users;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Users userId;
    private Products productId;
    private Orders ordersId;
    private long rating;
    private String content;
    private Timestamp createDate;
    private long status;
    private String ip;
}
