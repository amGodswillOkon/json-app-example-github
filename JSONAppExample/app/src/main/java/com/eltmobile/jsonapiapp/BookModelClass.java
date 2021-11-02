package com.eltmobile.jsonapiapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;
//A simple model class
public class BookModelClass implements Parcelable {
    public String id;
    public String title;
    public String subTitle;
    public String authors;
    public String publisher;
    public String publishedDate;
    public String description;
    public String thumbnail;

    public BookModelClass(String id, String title, String subTitle, String[] authors, String publisher,
                          String publishedDate, String description, String thumbnail) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.authors = TextUtils.join(", ", authors);
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    protected BookModelClass(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        authors = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<BookModelClass> CREATOR = new Creator<BookModelClass>() {
        @Override
        public BookModelClass createFromParcel(Parcel in) {
            return new BookModelClass(in);
        }

        @Override
        public BookModelClass[] newArray(int size) {
            return new BookModelClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(subTitle);
        parcel.writeString(authors);
        parcel.writeString(publisher);
        parcel.writeString(publishedDate);
        parcel.writeString(description);
        parcel.writeString(thumbnail);
    }
    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl){
            String myTrim = imageUrl.replace("http:", "https:");
        Picasso.with(view.getContext())
                .load(myTrim)
                .placeholder(R.drawable.book_icon)
                .into(view);
    }
}


