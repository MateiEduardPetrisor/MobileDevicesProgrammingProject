package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Places implements Parcelable {
    public static final Parcelable.Creator<Places> CREATOR
            = new Parcelable.Creator<Places>() {
        public Places createFromParcel(Parcel in) {
            return new Places(in);
        }

        public Places[] newArray(int size) {
            return new Places[size];
        }
    };
    private String Type;
    private String Name;
    private double Lat;
    private double Long;

    public Places(String type, String name, double lat, double aLong) {
        Type = type;
        Name = name;
        Lat = lat;
        Long = aLong;
    }

    public Places() {

    }

    private Places(Parcel in) {
        this.setType(in.readString());
        this.setName(in.readString());
        this.setLat(in.readDouble());
        this.setLong(in.readDouble());
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(Double aLong) {
        Long = aLong;
    }

    @Override
    public String toString() {
        return getType() + "," + getName() + "," + getLat() + "," + getLong() + ";";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getType());
        parcel.writeString(this.getName());
        parcel.writeDouble(this.getLat());
        parcel.writeDouble(this.getLong());
    }
}
