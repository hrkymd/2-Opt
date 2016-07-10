import java.io.*;
import java.util.ArrayList;

/**
 * Created by Yamada on 2016/07/07.
 */
public class T2Opt {

    public static void main(String[] args) throws Exception{

        String fileName = "src/ca4663.tsp";
        String kNearlestFileName = "kNearlestListOfca4663.csv";
        String reverseListFileName = "reverseListOfca4663.csv";
        String dataName = "ca4663";
//
//        String fileName = "src/ja9847.tsp";
//        String kNearlestFileName = "kNearlestListOfja9847.csv";
//        String reverseListFileName = "reverseListOfja9847.csv";
//        String dataName = "ja9847";
//
//        String fileName = "src/fi10639.tsp";
//        String kNearlestFileName = "kNearlestListOffi10639.csv";
//        String reverseListFileName = "reverseListOffi10639.csv";
//        String dataName = "fi10639";
//
//        String fileName = "src/bm33708.tsp";
//        String kNearlestFileName = "kNearlestListOfbm33708.csv";
//        String reverseListFileName = "reverseListOfbm33708.csv";
//        String dataName = "bm33708";
//
//        String fileName = "src/ch71009.tsp";
//        String kNearlestFileName = "kNearlestListOfch71009.csv";
//        String reverseListFileName = "reverseListOfch71009.csv";
//        String dataName = "ch71009";
//
//        String fileName = "src/mona-lisa100K.tsp";
//        String kNearlestFileName = "kNearlestListOfmonalisa.csv";
//        String reverseListFileName = "reverseListOfmonalisa.csv";
//        String dataName = "monalisa";

        TInstance cityData; //都市データ
        int[][] kNearlestList; //k近傍リスト
        ArrayList<Integer>[] reverseList; //逆近傍リスト
        int cityNum;
        int k = 50;

        //都市データの読み込み
//        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));

            if (fileName != "src/mona-lisa100K.tsp") {
                String emp = br.readLine(); //NAME
                emp = br.readLine(); //COMMENT
                emp = br.readLine(); //COMMENT
                emp = br.readLine(); //TYPE
                String[] strDimension = br.readLine().split(" "); //DIMENSION
                cityNum = Integer.parseInt(strDimension[2]);
                emp = br.readLine(); //EDGE_WEIGHT_TYPE
                emp = br.readLine(); //NODE_COORD_SECTION
            } else { //モナリザだけ形式違う
                String emp = br.readLine(); //NAME
                emp = br.readLine(); //COMMENT
                emp = br.readLine(); //TYPE
                String[] strDimension = br.readLine().split(" "); //DIMENSION
                cityNum = Integer.parseInt(strDimension[1]);
                emp = br.readLine(); //EDGE_WEIGHT_TYPE
                emp = br.readLine(); //NODE_COORD_SECTION
            }

            cityData = new TInstance(cityNum);
            for(int i = 0; i < cityNum; i++){
                String[] strArray = br.readLine().split(" ");
                cityData.getCity(i).setFx(Double.parseDouble(strArray[1])); //strArrayは 0.index, 1.x, 2.yが格納
                cityData.getCity(i).setFy(Double.parseDouble(strArray[2]));
            }

            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //end 都市データ読み込み

        //k近傍リストの読み込み
//        try {
            File file2 = new File(kNearlestFileName);
            BufferedReader br2 = new BufferedReader(new FileReader(file2));

            //都市数の取得
            cityNum = Integer.parseInt(br2.readLine());
            System.out.println("cityNum : " + cityNum);

            //k近傍リスト
            kNearlestList = new int[cityNum][k];

            //k近傍リストの読み込み
            for(int i = 0; i < cityNum; i++){
                //System.out.println(j);
                String[] strArray = br2.readLine().split(",");
                for(int j = 0; j < k; j++){ //都市ごとにk近傍リストを調べる
                    kNearlestList[i][j] = Integer.parseInt(strArray[j]);
                } //end for(j)
            } //end for(i)

            br2.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //end k近傍リストの読み込み

        //k近傍リスト読み出せたか確認
//        for(int i = 0; i < cityNum; i++){
//            for(int j = 0; j < k; j++){
//                System.out.print(kNearlestList[i][j] + " ");
//            }
//            System.out.println();
//        }

        //逆近傍リストの読み込み
//        try {
        File file3 = new File(reverseListFileName);
        BufferedReader br3 = new BufferedReader(new FileReader(file3));

        //都市数の取得
        cityNum = Integer.parseInt(br3.readLine());
        System.out.println("cityNum : " + cityNum);

        reverseList = new ArrayList[cityNum];

        //逆近傍リストの読み込み
        for(int i = 0; i < cityNum; i++){
            //System.out.println(j);
            String[] strArray = br3.readLine().split(","); //0.逆近傍数, 1~.そのリスト
            reverseList[i] = new ArrayList<>(Integer.parseInt(strArray[0])); //要素数分ArrayListの用意

            int reverseListSize = Integer.parseInt(strArray[0]);
            for(int j = 0; j < reverseListSize; j++){ //逆近傍の要素を入れていく
                reverseList[i].add(Integer.parseInt(strArray[j + 1]));
            } //end for(j)
        } //end for(i)

        br3.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //end 逆近傍リストの読み込み

        //逆近傍リスト読み出せたか確認
//        for(int i = 0; i < cityNum; i++){
//            int num = reverseList[i].size();
//            System.out.print(num + " : ");
//            for(int j = 0; j < num; j++){
//                System.out.print(reverseList[i].get(j) + " ");
//            }
//            System.out.println();
//        }

    }//end 2-Opt main

}
