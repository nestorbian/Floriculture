package com.nestor.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * <p>用于记录用户选中的地址，再下次用户确认订单时不再选择地址，如果选择新地址则更新</p>
 * @author bzy
 *
 */
@Entity
@Table(name = "user_address")
@Data
public class UserAddress {

    @Id
    private String openid;
    private String addressId;

}
