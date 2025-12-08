package com.osir.cartservice.mapper;

import com.osir.takeoutpojo.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {


    /**
     * 添加购物车
     * @param shoppingCart
     */
    void insert(@Param("shoppingCart") ShoppingCart shoppingCart);

    /**
     * 查询购物车是否存在当前菜品(&口味)或套餐
     * @param shoppingCart
     * @return
     */
    ShoppingCart count(@Param("shoppingCart") ShoppingCart shoppingCart);

    /**
     * 修改购物车菜品数量--加一
     * @param shoppingCart
     */
    void updateAddOne(@Param("shoppingCart") ShoppingCart shoppingCart);

    /**
     * 减少购物车菜品数量--减一
     * @param shoppingCart
     */
    void updateSubOne(@Param("shoppingCart") ShoppingCart shoppingCart);

    /**
     * 查询当前用户的购物车数据
     * @return
     */
    @Select("select * from shopping_cart where user_id=#{currentId}")
    List<ShoppingCart> list(@Param("currentId") Long currentId);

    /**
     * 清空购物车
     * @param shoppingCart
     */
    void delete(@Param("shoppingCart") ShoppingCart shoppingCart);

    /**
     * 清空购物车
     * @param currentId
     */
    @Delete("delete from shopping_cart where user_id=#{currentId}")
    void cleanByUserId(@Param("currentId") Long currentId);

    /**
     * 批量插入购物车数据
     * @param shoppingCartList
     */
    void insertBatch(List<ShoppingCart> shoppingCartList);

}
