package com.split.reader.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


@Entity(tableName = "chapters")
public class Surahs implements Parcelable, Cloneable {

    @PrimaryKey
    @ColumnInfo(name = "sura")
    Integer sura = 0;
    @ColumnInfo(name = "ayas_count")
    Integer ayasCount = 0;
    @ColumnInfo(name = "first_aya_id")
    Integer firstAyaId = 0;
    @ColumnInfo(name = "name_arabic")
    String nameArabic = "";
    @ColumnInfo(name = "name_transliteration")
    String nameTransliteration = "";
    @ColumnInfo(name = "type")
    String type = "";
    @ColumnInfo(name = "revelation_order")
    Integer revelationOrder = 0;
    @ColumnInfo(name = "rukus")
    Integer rukus = 0;

    public Surahs(Integer sura, Integer ayasCount, Integer firstAyaId, String nameArabic, String nameTransliteration, String type, Integer revelationOrder, Integer rukus) {
        this.sura = sura;
        this.ayasCount = ayasCount;
        this.firstAyaId = firstAyaId;
        this.nameArabic = nameArabic;
        this.nameTransliteration = nameTransliteration;
        this.type = type;
        this.revelationOrder = revelationOrder;
        this.rukus = rukus;
    }

    @Override
    public Surahs clone() {
        Surahs clone = null;

        try {
            clone = (Surahs) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // won't happen
        }

        return clone;
    }

    protected Surahs(Parcel in) {

        sura = in.readInt();
        ayasCount = in.readInt();
        firstAyaId = in.readInt();
        nameArabic = in.readString();
        nameTransliteration = in.readString();
        type = in.readString();
        revelationOrder = in.readInt();
        rukus = in.readInt();

    }

    public static final Creator<Surahs> CREATOR = new Creator<Surahs>() {
        @Override
        public Surahs createFromParcel(Parcel in) {
            return new Surahs(in);
        }

        @Override
        public Surahs[] newArray(int size) {
            return new Surahs[size];
        }
    };

    @NonNull
    public Integer getSura() {
        return sura;
    }


    public String getNameTransliteration() {
        return nameTransliteration;
    }


    public String getType() {
        return type;
    }

    public Integer getAyasCount() {
        return ayasCount;
    }

    public Integer getRevelationOrder() {
        return revelationOrder;
    }

    public Integer getRukus() {
        return rukus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sura);
        dest.writeInt(ayasCount);
        dest.writeInt(firstAyaId);
        dest.writeString(nameArabic);
        dest.writeString(nameTransliteration);
        dest.writeString(type);
        dest.writeInt(revelationOrder);
        dest.writeInt(rukus);
    }
}
