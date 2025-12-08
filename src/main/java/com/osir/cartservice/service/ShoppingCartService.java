package com.osir.cartservice.service;

import com.osir.takeoutpojo.dto.ShoppingCartDTO;
import com.osir.takeoutpojo.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加菜品或套餐到购物车
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> list();

    /**
     * 删除购物车中一个商品
     * @param shoppingCartDTO
     */
    void sub(ShoppingCartDTO shoppingCartDTO);

    /**
     * 清空购物车
     */
    void clean();


    /**
     * 批量插入购物车数据
     * @param shoppingCartList
     */
    void insertBatch(List<ShoppingCart> shoppingCartList);
}
