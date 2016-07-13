import java.util.Random;

/**
 * Created by Yamada on 2016/07/07.
 */
public class TRoute {
    private int fCityNum;
    private int[] fRouteList; //経路のリスト
    private int [] fIdToRoute; //都市IDを入れると経路での場所を返す
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
            fRouteList[i] = i;
        }

        fIdToRoute = new int[fCityNum];
        for(int i = 0; i < fCityNum; i++){ //0から番号を入れておく
            fIdToRoute[i] = i;
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
            return fRouteList[fCityNum - 1];
        }
        return fRouteList[index - 1];
    }

    /**
     * 次の都市
     * @param index
     * @return
     */
    public int next(int index){
        if(index == fCityNum - 1 ){
            return fRouteList[0];
        }
        return fRouteList[index + 1];
    }

    /**
     * 経路の初期化、経路長の計算
     */
    public void init(){
        int swapNum;
        int tmp, tmpid;

        for(int i = 0 ; i < fCityNum; i++){
            swapNum = fRandom.nextInt(fCityNum - i);

            tmpid = fIdToRoute[fRouteList[i]];
            fIdToRoute[fRouteList[i]] = fIdToRoute[fRouteList[i + swapNum]];
            fIdToRoute[fRouteList[i + swapNum]] = tmpid;

            tmp = fRouteList[i];
            fRouteList[i] = fRouteList[i + swapNum];
            fRouteList[i + swapNum] = tmp;

        }

        calcTourLength();
    }

    /**
     * fRouteListに基づく経路長の計算
     */
    public void calcTourLength(){
        double xd, yd;
        double distance = 0.0;

        xd = Math.abs(fCityData.getCity(fRouteList[fCityNum - 1]).getFx() - fCityData.getCity(fRouteList[0]).getFx() );
        yd = Math.abs(fCityData.getCity(fRouteList[fCityNum - 1]).getFy() - fCityData.getCity(fRouteList[0]).getFy() );
        distance += Math.floor(Math.sqrt(xd * xd + yd * yd) + 0.5);
//        distance += Math.sqrt(xd * xd + yd * yd);

        for(int i = 0; i < fCityNum - 1; i++){ //距離の計算
            xd = Math.abs(fCityData.getCity(fRouteList[i]).getFx() - fCityData.getCity(fRouteList[i + 1]).getFx() );
            yd = Math.abs(fCityData.getCity(fRouteList[i]).getFy() - fCityData.getCity(fRouteList[i + 1]).getFy() );
            distance += Math.floor(Math.sqrt(xd * xd + yd * yd) + 0.5);
//            distance += Math.sqrt(xd * xd + yd * yd);
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

    public int[] getfIdToRoute() {
        return fIdToRoute;
    }

    public void setfIdToRoute(int[] fIdToRoute) {
        this.fIdToRoute = fIdToRoute;
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

    /**
     * 与えられたindexに相当する都市の番号を返す
     * @param index
     * @return
     */
    public int getNumberOfCity(int index){
        return fRouteList[index];
    }


    /**
     * 与えられた都市IDの経路での場所を返すを返す
     * @param id
     * @return
     */
    public int getPositionOfCity(int id){
        return fIdToRoute[id];
    }


    /**
     * 2都市間の距離を返す
     * @param v_a
     * @param v_b
     * @return
     */
    public double calcDistance(int v_a, int v_b) {
        double xd, yd;
        xd = Math.abs(fCityData.getCity(v_a).getFx() - fCityData.getCity(v_b).getFx() );
        yd = Math.abs(fCityData.getCity(v_a).getFy() - fCityData.getCity(v_b).getFy() );
        return  Math.floor(Math.sqrt(xd * xd + yd * yd) + 0.5);
//        return  Math.sqrt(xd * xd + yd * yd);
    }

    /**
     * idから経路での前の都市
     * @param id
     * @return
     */
    public int prevFromId(int id){

        if (fIdToRoute[id] == 0) {
            return fRouteList[fCityNum - 1];
        }
        else{
            return fRouteList[fIdToRoute[id] - 1];
        }

    }

    /**
     * idから経路での次の都市
     * @param id
     * @return
     */
    public int nextFromId(int id){
        if (fIdToRoute[id] == fCityNum - 1) {
            return  fRouteList[0];
        }
        else{
            return fRouteList[fIdToRoute[id] + 1];
        }

    }

    public void swapEdge(int v_a, int v_b, int v_c, int v_d){
        int positionV_a = fIdToRoute[v_a];
        int positionV_b = fIdToRoute[v_b];
        int positionV_c = fIdToRoute[v_c];
        int positionV_d = fIdToRoute[v_d];
        int difference;
        int swapPosi1, swapPosi2;
        int swapId1, swapId2;
        int tmpRoute, tmpId;
        int loopNum;
        int p1, p2; //内側二つの点
        boolean inner = true;

        if(positionV_a < positionV_d){
            //pva,pvb,   , pvc,pvdとなるパターン
            if(positionV_a < positionV_b){
                p1 = positionV_c;
                p2 = positionV_b;

//                difference = positionV_c - positionV_b;
//                if(difference % 2 == 0){
//                    loopNum = difference / 2;
//                }
//                else{
//                    loopNum = difference / 2 + 1;
//                }
//
//                for(int i = 0; i < loopNum; i++){
//
//                    if(positionV_c - i < 0){
//                        swapPosi2 = positionV_c - i + fCityNum;
//                    }
//                    else{
//                        swapPosi2 = positionV_c - i;
//                    }
//
//                    if(positionV_b + i >= fCityNum){
//                        swapPosi1 = (positionV_b + i) - fCityNum;
//                    }
//                    else{
//                        swapPosi1 = positionV_b + i;
//                    }
//
//                    swapId1 = fRouteList[swapPosi1];
//                    swapId2 = fRouteList[swapPosi2];
//
//                    //経路の交換
//                    tmpRoute = swapId1;
//                    fRouteList[swapPosi1] = swapId2;
//                    fRouteList[swapPosi2] = tmpRoute;
//
//                    //Idから経路出す方の交換
//                    tmpId = fIdToRoute[swapId1];
//                    fIdToRoute[swapId1] = fIdToRoute[swapId2];
//                    fIdToRoute[swapId2] = tmpId;
//                } //end for(i)
            }
            //pvb,pva,   , pvd,pvcとなるパターン
            else {

                p1 = positionV_d;
                p2 = positionV_a;


//                difference = positionV_d - positionV_a;
//
//                if(difference % 2 == 0){
//                    loopNum = difference / 2;
//                }
//                else{
//                    loopNum = difference / 2 + 1;
//                }
//
//
//                for(int i = 0; i < loopNum; i++){
//
//                    if(positionV_d - i < 0){
//                        swapPosi2 = positionV_d - i + fCityNum;
//                    }
//                    else{
//                        swapPosi2 = positionV_d - i;
//                    }
//
//                    if(positionV_a + i >= fCityNum){
//                        swapPosi1 = (positionV_a + i) - fCityNum;
//                    }
//                    else{
//                        swapPosi1 = positionV_a + i;
//                    }
//
//                    swapId1 = fRouteList[swapPosi1];
//                    swapId2 = fRouteList[swapPosi2];
//
//                    //経路の交換
//                    tmpRoute = swapId1;
//                    fRouteList[swapPosi1] = swapId2;
//                    fRouteList[swapPosi2] = tmpRoute;
//
//                    //Idから経路出す方の交換
//                    tmpId = fIdToRoute[swapId1];
//                    fIdToRoute[swapId1] = fIdToRoute[swapId2];
//                    fIdToRoute[swapId2] = tmpId;
//                } //end for(i)
            }
        }
        else {
            //pvc,pvd,   , pva,pvbとなるパターン
            if(positionV_c < positionV_d){

                p1 = positionV_a;
                p2 = positionV_d;

//                difference = positionV_a - positionV_d;
//                if(difference % 2 == 0){
//                    loopNum = difference / 2;
//                }
//                else{
//                    loopNum = difference / 2 + 1;
//                }
//
//
//                for(int i = 0; i < loopNum; i++){
//
//                    if(positionV_a - i < 0){
//                        swapPosi2 = positionV_a - i + fCityNum;
//                    }
//                    else{
//                        swapPosi2 = positionV_a - i;
//                    }
//
//                    if(positionV_d + i >= fCityNum){
//                        swapPosi1 = (positionV_d + i) - fCityNum;
//                    }
//                    else{
//                        swapPosi1 = positionV_d + i;
//                    }
//
//                    swapId1 = fRouteList[swapPosi1];
//                    swapId2 = fRouteList[swapPosi2];
//
//                    //経路の交換
//                    tmpRoute = swapId1;
//                    fRouteList[swapPosi1] = swapId2;
//                    fRouteList[swapPosi2] = tmpRoute;
//
//                    //Idから経路出す方の交換
//                    tmpId = fIdToRoute[swapId1];
//                    fIdToRoute[swapId1] = fIdToRoute[swapId2];
//                    fIdToRoute[swapId2] = tmpId;
//                } //end for(i)
            }
            //pvd,pvc,   , pvb,pvaとなるパターン
            else {

                p1 = positionV_b;
                p2 = positionV_c;

//                difference = positionV_b - positionV_c;
//
//                if(difference % 2 == 0){
//                    loopNum = difference / 2;
//                }
//                else{
//                    loopNum = difference / 2 + 1;
//                }
//
//
//                for(int i = 0; i < loopNum; i++){
//
//                    if(positionV_b - i < 0){
//                        swapPosi2 = positionV_b - i + fCityNum;
//                    }
//                    else{
//                        swapPosi2 = positionV_b - i;
//                    }
//
//                    if(positionV_c + i >= fCityNum){
//                        swapPosi1 = (positionV_c + i) - fCityNum;
//                    }
//                    else{
//                        swapPosi1 = positionV_c + i;
//                    }
//
//                    swapId1 = fRouteList[swapPosi1];
//                    swapId2 = fRouteList[swapPosi2];
//
//                    //経路の交換
//                    tmpRoute = swapId1;
//                    fRouteList[swapPosi1] = swapId2;
//                    fRouteList[swapPosi2] = tmpRoute;
//
//                    //Idから経路出す方の交換
//                    tmpId = fIdToRoute[swapId1];
//                    fIdToRoute[swapId1] = fIdToRoute[swapId2];
//                    fIdToRoute[swapId2] = tmpId;
//                } //end for(i)
            }
        }

        difference = p1 - p2;
        if(difference > (fCityNum / 2)) { //内側間が長い時
            inner = false;
        }

        if(inner == true){
            if(difference % 2 == 0){
                loopNum = difference / 2;
            }
            else{
                loopNum = difference / 2 + 1;
            }

            for(int i = 0; i < loopNum; i++){

                if(p1 - i < 0){
                    swapPosi1 = p1 - i + fCityNum;
                }
                else{
                    swapPosi1 = p1 - i;
                }

                if(p2 + i >= fCityNum){
                    swapPosi2 = p2 + i - fCityNum;
                }
                else{
                    swapPosi2 = p2 + i;
                }

                swapId1 = fRouteList[swapPosi1];
                swapId2 = fRouteList[swapPosi2];

                //経路の交換
                tmpRoute = swapId1;
                fRouteList[swapPosi1] = swapId2;
                fRouteList[swapPosi2] = tmpRoute;

                //Idから経路出す方の交換
                tmpId = fIdToRoute[swapId1];
                fIdToRoute[swapId1] = fIdToRoute[swapId2];
                fIdToRoute[swapId2] = tmpId;
            } //end for(i)
        }
        else{
            if((fCityNum - difference) % 2 == 0){
                loopNum = (fCityNum - difference) / 2 - 1;
            }
            else {
                loopNum = (fCityNum - difference) / 2;
            }

            for(int i = 0; i < loopNum; i++){

                if(p2 - 1 - i < 0){
                    swapPosi2 = p2 - 1 - i + fCityNum;
                }
                else{
                    swapPosi2 = p2 - 1 - i;
                }

                if(p1 + 1 + i >= fCityNum){
                    swapPosi1 = (p1 + 1 + i) - fCityNum;
                }
                else{
                    swapPosi1 = p1 + 1 + i;
                }

                swapId1 = fRouteList[swapPosi1];
                swapId2 = fRouteList[swapPosi2];

                //経路の交換
                tmpRoute = swapId1;
                fRouteList[swapPosi1] = swapId2;
                fRouteList[swapPosi2] = tmpRoute;

                //Idから経路出す方の交換
                tmpId = fIdToRoute[swapId1];
                fIdToRoute[swapId1] = fIdToRoute[swapId2];
                fIdToRoute[swapId2] = tmpId;
            } //end for(i)
        }

    } //end swapEdge

}
