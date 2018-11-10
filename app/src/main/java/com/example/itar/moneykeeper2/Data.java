package com.example.itar.moneykeeper2;


public class Data{
    private String mText1;
    private String mText2;
    private int mIcon;

    public Data(String mText1, String mText2){
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public String getmText1(){
        return mText1;
    }

    public void setmText1(String mText1){
        this.mText1 = mText1;
    }

    public String getmText2(){
        return mText2;
    }

    public void setmText2(String mText2){
        this.mText2 = mText2;
    }

    public int getmIcon(){
        return mIcon;
    }

    public void setmIcon(int mIcon){
        this.mIcon = mIcon;
    }
}