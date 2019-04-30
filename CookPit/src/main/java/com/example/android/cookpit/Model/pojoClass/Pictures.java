package com.example.android.cookpit.Model.pojoClass;

public class Pictures {
    private String uri;
    private String file_name;
    private int dish_id;
    private int default_drawable;
    private int drawable_date_time;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public int getDefault_drawable() {
        return default_drawable;
    }

    public void setDefault_drawable(int default_drawable) {
        this.default_drawable = default_drawable;
    }

    public int getDrawable_date_time() {
        return drawable_date_time;
    }

    public void setDrawable_date_time(int drawable_date_time) {
        this.drawable_date_time = drawable_date_time;
    }

    public String getPicture_tags() {
        return picture_tags;
    }

    public void setPicture_tags(String picture_tags) {
        this.picture_tags = picture_tags;
    }

    private String picture_tags;


}
