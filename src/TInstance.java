import java.util.Arrays;

/**
 * Created by Yamada on 2016/07/07.
 */
public class TInstance {
    private TCity[] fCityData;
    private int fCityNum;


    TInstance(int dimension){
        fCityNum = dimension;
        fCityData = new TCity[fCityNum];
        for(int i = 0; i < fCityNum; i++) {
            fCityData[i] = new TCity();
        }
    }

    /**
     * コピーコンストラクタ
     * @param instance
     */
    public TInstance(TInstance instance){
        if(fCityNum != instance.getfCityData().length){
            fCityNum = instance.getfCityData().length;
            fCityData = new TCity[fCityNum];

            for(int i = 0; i < fCityNum; i++){
                fCityData[i] = new TCity();
            }
        }

        for(int i = 0; i < fCityNum ; i++){
            fCityData[i].copyFrom(instance.fCityData[i]);
        }

    }

    public TInstance clone(){
        return new TInstance(this);
    }

    public void copyFrom(TInstance instance){
        if(fCityNum != instance.getfCityData().length){
            fCityNum = instance.getfCityData().length;
            fCityData = new TCity[fCityNum];
        }

        for(int i = 0; i < fCityNum ; i++){
            fCityData[i].copyFrom(instance.fCityData[i]);
        }
    }

    public TCity getCity(int index){
        return fCityData[index];
    }

    public TCity[] getfCityData() {
        return fCityData;
    }

    public void setfCityData(TCity[] fCityData) {
        this.fCityData = fCityData;
    }

    public int getSize(){
        return fCityData.length;
    }

    @Override
    public String toString() {
        return "TInstance{" +
                "fCityData=" + Arrays.toString(fCityData) +
                '}';
    }


}
