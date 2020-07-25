package com.example.team_cat_iot2020.retrofit;

import java.io.Serializable;

public class Resultdata implements Serializable {

    private int idx;
    private String name; //제품명
    private String image; //이미지
    private String category; //카테고리
    private String color; //색
    private String texture ; //재질
    private int width; //가로
    private int length; //세로
    private int height; //높이
    private int price; //가격

    //    private String info;      //상세설명
    private String explanation; //상세설명

    public Resultdata(int index, String name, String image, String category, String color, String texture, int width, int length, int height, int price, String info) {
        this.idx = index;
        this.name = name;
        this.image = image;
        this.category = category;
        this.color = color;
        this.texture = texture;
        this.width = width;
        this.length = length;
        this.height = height;
        this.price = price;
//        this.info = info;
        this.explanation = info;
    }

    //테스트
    public Resultdata(String name, String image, String category, String color) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.color = color;
    }

    public int getIndex() {
        return idx;
    }

    public void setIndex(int index) {
        this.idx = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInfo() {
//        return info;
        return explanation;
    }

    public void setInfo(String info) {
//        this.info = info;
        this.explanation =info;
    }


}

