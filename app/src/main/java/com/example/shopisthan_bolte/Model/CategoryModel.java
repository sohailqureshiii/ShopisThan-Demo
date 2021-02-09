package com.example.shopisthan_bolte.Model;

public class CategoryModel {

    private String CategoryIconlink;
    private String categoryName;

    public CategoryModel(String categoryIconlink, String categoryName) {
        CategoryIconlink = categoryIconlink;
        this.categoryName = categoryName;
    }

    public String getCategoryIconlink() {
        return CategoryIconlink;
    }

    public void setCategoryIconlink(String categoryIconlink) {
        CategoryIconlink = categoryIconlink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
