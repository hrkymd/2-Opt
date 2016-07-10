import java.io.*;
import java.util.*;

/**
 * Created by Yamada on 2016/07/07.
 */
public class TMakeNearestList {

    public static void main(String[] args) {

//        String fileName = "src/ca4663.tsp";
//        String dataName = "ca4663";

//        String fileName = "src/ja9847.tsp";
//        String dataName = "ja9847";

//        String fileName = "src/fi10639.tsp";
//        String dataName = "fi10639";

//        String fileName = "src/bm33708.tsp";
//        String dataName = "bm33708";

//        String fileName = "src/ch71009.tsp";
//        String dataName = "ch71009";

        String fileName = "src/mona-lisa100K.tsp";
        String dataName = "monalisa";

        TInstance cityData;
        int cityNum;
        int k = 50;

        try{
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));

            if(fileName != "src/mona-lisa100K.tsp") {
                String emp = br.readLine(); //NAME
                emp = br.readLine(); //COMMENT
                emp = br.readLine(); //COMMENT
                emp = br.readLine(); //TYPE
                String[] strDimension = br.readLine().split(" "); //DIMENSION
                cityNum = Integer.parseInt(strDimension[2]);
                emp = br.readLine(); //EDGE_WEIGHT_TYPE
                emp = br.readLine(); //NODE_COORD_SECTION
            }
            else{ //モナリザだけ形式違う
                String emp = br.readLine(); //NAME
                emp = br.readLine(); //COMMENT
                emp = br.readLine(); //TYPE
                String[] strDimension = br.readLine().split(" "); //DIMENSION
                cityNum = Integer.parseInt(strDimension[1]);
                emp = br.readLine(); //EDGE_WEIGHT_TYPE
                emp = br.readLine(); //NODE_COORD_SECTION
            }

            System.out.println("cityNum : " + cityNum);

            cityData = new TInstance(cityNum);
            for(int i = 0; i < cityNum; i++){
                String[] strArray = br.readLine().split(" ");
                cityData.getCity(i).setFx(Double.parseDouble(strArray[1])); //strArrayは 0.index, 1.x, 2.yが格納
                cityData.getCity(i).setFy(Double.parseDouble(strArray[2]));
            }

            //k近傍リストの作成
            //距離のListの用意
            ArrayList<TCityDistance> cityDistanceList = new ArrayList<>(cityNum);
            for(int i = 0; i < cityNum; i++){
                cityDistanceList.add(new TCityDistance());
            }

            String writeListName = "kNearlestListOf" + dataName + ".csv";
            File writeFile = new File(writeListName);
            PrintWriter pw = new PrintWriter(new FileWriter(writeFile));

            pw.println(cityNum);

            long start = System.currentTimeMillis(); //時間計測

            for(int i = 0; i < cityNum; i++){ //各都市について

                for(int j = 0; j < cityNum; j++){ //距離の計算
                    double xd = Math.abs(cityData.getCity(i).getFx() - cityData.getCity(j).getFx() );
                    double yd = Math.abs(cityData.getCity(i).getFy() - cityData.getCity(j).getFy() );
                    double dij = Math.floor(Math.sqrt(xd * xd + yd * yd) + 0.5);
                    if(i == j){
                        dij = Double.POSITIVE_INFINITY;
                    }
                    cityDistanceList.get(j).setData(j, dij);
                } //end for(j)

                Collections.sort(cityDistanceList, new Comparator<TCityDistance>() {
                    @Override
                    public int compare(TCityDistance t1, TCityDistance t2) {
                        return Double.compare(t1.getfDistance(), t2.getfDistance());
                    }
                }); //end sort

                //k個書き出し
                for(int j = 0; j < k; j++){
                    pw.print(+ cityDistanceList.get(j).getfIndex() + ",");
                }
                pw.println();

            } //end for(i)
            pw.close();

            long end = System.currentTimeMillis(); //時間計測
            System.out.println((end - start)/1000.0 / 60.0 + "m");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
