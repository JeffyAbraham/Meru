package com.example.meru;

public class UploadImages {



    private  String mName;
    private  String mImageUrl;
    public UploadImages()
    {

    }


    public UploadImages(String name,String mImageUrl)
    {
        if(name.trim().equals(""))
        {
            name="No Name";
        }
        mName=name;
        mImageUrl=mImageUrl;

    }
    public String getName()
    {
        return  mName;
    }
    public void setmName(String name)
    {
        mName=name;
    }
    public String getmImageUrl()
    {
        return mImageUrl;
    }
    public void  setmImageUrl(String imageUrl)
    {
        mImageUrl=imageUrl;
    }
}
