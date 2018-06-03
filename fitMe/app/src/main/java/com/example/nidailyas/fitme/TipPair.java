package com.example.nidailyas.fitme;

class TipPair {
    private String Title;
    private String Text;
    private Integer Image;

    //accessors


    public TipPair(String title, String text, Integer image) {
        Title = title;
        Text = text;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }
}
