package com.xujie.miaosha.vo;

import com.xujie.miaosha.domain.Goods;

import java.util.Date;

public class GoodsVo extends Goods {

    private Date startDate;

    private Date endDate;

    private Integer stockCount;

    private double miaoshaPrice;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate( Date startDate ) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate( Date endDate ) {
        this.endDate = endDate;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount( Integer stockCount ) {
        this.stockCount = stockCount;
    }

    public double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice( double miaoshaPrice ) {
        this.miaoshaPrice = miaoshaPrice;
    }
}
