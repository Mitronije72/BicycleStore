package com.store.store.Model.Entities.dto;

import java.math.BigDecimal;

public class BicycleDto {
    private String name;
    private BigDecimal price;
    private String categoryName;
    private String brandName;

    public BicycleDto( String name, BigDecimal price, String categoryName, String brandName) {

        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.brandName = brandName;
    }

    public BicycleDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
