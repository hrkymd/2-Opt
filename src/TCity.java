/**
 * Created by Yamada on 2016/07/07.
 */
public class TCity {
    private double fx;
    private double fy;

    TCity(){
        fx = Double.NaN;
        fy = Double.NaN;
    }

    TCity(TCity city){
        this.fx = city.fx;
        this.fy = city.fy;
    }

    public TCity clone(){
        return new TCity(this);
    }

    public void copyFrom(TCity city){
        this.fx = city.fx;
        this.fy = city.fy;
    }

    public double getFx() {
        return fx;
    }

    public void setFx(double fx) {
        this.fx = fx;
    }

    public double getFy() {
        return fy;
    }

    public void setFy(double fy) {
        this.fy = fy;
    }

    @Override
    public String toString() {
        return  "" + fx + " " + fy;
    }

    public void init(){
        fx = Double.NaN;
        fy = Double.NaN;
    }
}
