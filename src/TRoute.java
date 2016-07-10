import java.util.Random;

/**
 * Created by Yamada on 2016/07/07.
 */
public class TRoute {
    private int fCityNum;
    private int[] fRouteList;
    private double fTourLength;
    Random fRandom;
    private TInstance fCityData;

    /**
     * コンストラクタ
     * @param instance
     */
    public TRoute(TInstance instance) {
        fCityNum = instance.getSize();
        fRouteList = new int[fCityNum];
        for(int i = 0; i < fCityNum; i++){ //0から番号を入れておく
            fRouteList[i] = 0;
        }
        fTourLength = Double.NaN;
        fRandom = new Random();
        fCityData = new TInstance(fCityNum);
        fCityData.copyFrom(instance);

    }

    /**
     * 前の都市
     * @param index
     * @return
     */
    public int prev(int index){
        if(index == 0){
            return fRouteList[fCityNum];
        }
        return fRouteList[index - 1];
    }

    /**
     * 次の都市
     * @param index
     * @return
     */
    public int next(int index){
        if(index == fCityNum){
            return fRouteList[0];
        }
        return fRouteList[index + 1];
    }

    /**
     * 経路の初期化、経路長の計算
     */
    public void init(){
        int swapNum;
        int tmp;
        for(int i = 0 ; i < fCityNum; i++){
            swapNum = fRandom.nextInt() % (fCityNum - i);
            tmp = fRouteList[i];
            fRouteList[i] = fRouteList[swapNum];
            fRouteList[swapNum] = tmp;
        }

        calcTourLength();
    }

    /**
     * fRouteListに基づく経路長の計算
     */
    public void calcTourLength(){
        double xd, yd;
        double distance = 0.0;

        xd = Math.abs(fCityData.getCity(fRouteList[fCityNum]).getFx() - fCityData.getCity(fRouteList[0]).getFx() );
        yd = Math.abs(fCityData.getCity(fRouteList[fCityNum]).getFy() - fCityData.getCity(fRouteList[0]).getFy() );
        distance += Math.floor(Math.sqrt(xd * xd + yd * yd) + 0.5);

        for(int i = 0; i < fCityNum - 1; i++){ //距離の計算
            xd = Math.abs(fCityData.getCity(fRouteList[i]).getFx() - fCityData.getCity(fRouteList[i + 1]).getFx() );
            yd = Math.abs(fCityData.getCity(fRouteList[i]).getFy() - fCityData.getCity(fRouteList[i + 1]).getFy() );
            distance += Math.floor(Math.sqrt(xd * xd + yd * yd) + 0.5);
        } //end for(i)

        fTourLength = distance;
    }


    public int getfCityNum() {
        return fCityNum;
    }

    public void setfCityNum(int fCityNum) {
        this.fCityNum = fCityNum;
    }

    public int[] getfRouteList() {
        return fRouteList;
    }

    public void setfRouteList(int[] fRouteList) {
        this.fRouteList = fRouteList;
    }

    public double getfTourLength() {
        return fTourLength;
    }

    public void setfTourLength(double fTourLength) {
        this.fTourLength = fTourLength;
    }

    public TInstance getfCityData() {
        return fCityData;
    }

    public void setfCityData(TInstance fCityData) {
        this.fCityData = fCityData;
    }
}
