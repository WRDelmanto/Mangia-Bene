package com.example.mangiabene.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private final String idCategory;
    private final String strCategory;
    private final String strCategoryThumb;
    private final String strCategoryDescription;

    public Category(String idCategory,
                    String strCategory,
                    String strCategoryThumb,
                    String strCategoryDescription) {
        this.idCategory = idCategory;
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
        this.strCategoryDescription = strCategoryDescription;
    }

    protected Category(Parcel in) {
        idCategory = in.readString();
        strCategory = in.readString();
        strCategoryThumb = in.readString();
        strCategoryDescription = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCategory);
        dest.writeString(strCategory);
        dest.writeString(strCategoryThumb);
        dest.writeString(strCategoryDescription);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }
}
