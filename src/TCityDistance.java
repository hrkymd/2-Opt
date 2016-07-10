/**
 * Created by Yamada on 2016/07/07.
 */
public class TCityDistance {
    private int fIndex;
    private double fDistance;

    public TCityDistance(){
        fIndex = -1;
        fDistance = Double.NaN;
    }

    public TCityDistance(TCityDistance citypackage){
        this.fIndex = citypackage.fIndex;
        this.fDistance = citypackage.fDistance;
    }

    public TCityDistance(int index, double distance) {
        this.fIndex = index;
        this.fDistance = distance;
    }

    public TCityDistance clone(){
        return new TCityDistance(this);
    }

    public void copyFrom(TCityDistance citypackage){
        this.fIndex = citypackage.fIndex;
        this.fDistance = citypackage.fDistance;
    }

    public int getfIndex() {
        return fIndex;
    }

    public void setfIndex(int fIndex) {
        this.fIndex = fIndex;
    }

    public double getfDistance() {
        return fDistance;
    }

    public void setfDistance(double fDistance) {
        this.fDistance = fDistance;
    }


    public void setData(int index, double distance){
        this.fIndex = index;
        this.fDistance = distance;
    }

    @Override
    public String toString(){
        String str = "index = " + fIndex + "\n";
        str += "distance = " + fDistance + "\n";
        return str;
    }

    public void init(){
        fIndex = -1;
        fDistance = Double.NaN;
    }
}
