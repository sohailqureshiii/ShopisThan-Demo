package com.example.shopisthan_bolte.Model;

public class ShopModel
{
  private   String SName, SDescription;

  private ShopModel()
  {

  }

    public ShopModel(String SName, String SDescription) {
        this.SName = SName;
        this.SDescription = SDescription;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getSDescription() {
        return SDescription;
    }

    public void setSDescription(String SDescription) {
        this.SDescription = SDescription;
    }
}
