package com.osir.cartservice.service.impl;

import com.osir.cartservice.feign.CatalogServiceFeignClient;
import com.osir.cartservice.mapper.ShoppingCartMapper;
import com.osir.cartservice.service.ShoppingCartService;
import com.osir.commonservice.utils.LoginUserContext;
import com.osir.takeoutpojo.dto.ShoppingCartDTO;
import com.osir.takeoutpojo.entity.Setmeal;
import com.osir.takeoutpojo.entity.ShoppingCart;
import com.osir.takeoutpojo.vo.DishVO;
import com.osir.takeoutpojo.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;
    private final CatalogServiceFeignClient catalogServiceFeignClient;

    /**
     * 添加菜品或套餐到购物车
     * @param shoppingCartDTO
     */
    // TODO 添加到购物车的商品都要检查是否还在售卖
    //    用户通过再来一单，可能可以把停售的商品添加到购物车
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();

        if(shoppingCartDTO.getDishId()!=null){
            shoppingCart = ShoppingCart.builder()
                    .userId(LoginUserContext.getUserId())
                    .dishId(shoppingCartDTO.getDishId())
                    .dishFlavor(shoppingCartDTO.getDishFlavor())
                    .build();
            // 首先要判断当前用户的购物车中是否有该菜品或套餐
            ShoppingCart result = shoppingCartMapper.count(shoppingCart);
            // 如果有，则数量加一即可(update)
            if(result!=null){
                shoppingCartMapper.updateAddOne(shoppingCart);
            }
            // 如果没有，则添加(insert)
            else{
                DishVO dishVO = catalogServiceFeignClient.getByDishId(shoppingCartDTO.getDishId());
                BeanUtils.copyProperties(dishVO,shoppingCart);
                shoppingCart.setNumber(1);
                shoppingCart.setAmount(dishVO.getPrice());
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCartMapper.insert(shoppingCart);
            }
        }



        if(shoppingCartDTO.getSetmealId()!=null){
            shoppingCart = ShoppingCart.builder()
                    .userId(LoginUserContext.getUserId())
                    .setmealId(shoppingCartDTO.getSetmealId())
                    .dishFlavor(shoppingCartDTO.getDishFlavor())
                    .build();
            // 首先要判断当前用户的购物车中是否有该菜品或套餐
            ShoppingCart result = shoppingCartMapper.count(shoppingCart);
            // 如果有，则数量加一即可(update)
            if(result!=null){
                shoppingCartMapper.updateAddOne(shoppingCart);
            }
            // 如果没有，则添加(insert)
            else{
                SetmealVO setmealVO = catalogServiceFeignClient.getBySetmealId(shoppingCartDTO.getSetmealId());
                Setmeal setmeal = new Setmeal();
                BeanUtils.copyProperties(setmealVO,setmeal);
                BeanUtils.copyProperties(setmeal,shoppingCart);
                shoppingCart.setNumber(1);
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCartMapper.insert(shoppingCart);
            }
        }
    }

    /**
     * 批量插入购物车数据
     * @param shoppingCartList
     */
    @Override
    public void insertBatch(List<ShoppingCart> shoppingCartList) {
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    /**
     * 获取当前用户的购物车
     * @return
     */
    public List<ShoppingCart> list() {
        return shoppingCartMapper.list(LoginUserContext.getUserId());
    }

    /**
     * 减少菜品或套餐的购物车
     * @param shoppingCartDTO
     */
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(LoginUserContext.getUserId())
                .setmealId(shoppingCartDTO.getSetmealId())
                .dishId(shoppingCartDTO.getDishId())
                .dishFlavor(shoppingCartDTO.getDishFlavor())
                .build();

        ShoppingCart result = shoppingCartMapper.count(shoppingCart);
        if(result!=null && result.getNumber()>1){
            shoppingCartMapper.updateSubOne(shoppingCart);
        }
        else{
            shoppingCartMapper.delete(shoppingCart);
        }
    }

    /**
     * 清空购物车
     */
    public void clean() {
        shoppingCartMapper.cleanByUserId(LoginUserContext.getUserId());
    }

}
