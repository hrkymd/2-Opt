import java.io.*;
import java.util.ArrayList;

/**
 * Created by Yamada on 2016/07/09.
 */
public class TMakeReverseKList {

    public static void main(String[] args) {


        String fileName = "kNearlestListOfca4663.csv";
        String dataName = "ca4663";

//        String fileName = "kNearlestListOfja9847.csv";
//        String dataName = "ja9847";

//        String fileName = "kNearlestListOffi10639.csv";
//        String dataName = "fi10639";

//        String fileName = "kNearlestListOfbm33708.csv";
//        String dataName = "bm33708";

//        String fileName = "kNearlestListOfch71009.csv";
//        String dataName = "ch71009";

//        String fileName = "kNearlestListOfmonalisa.csv";
//        String dataName = "monalisa";

        int cityNum;
        int k = 50;

        try {

            long start = System.currentTimeMillis(); //時間計測

            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));

            //都市数の取得
            cityNum = Integer.parseInt(br.readLine());
            System.out.println("cityNum : " + cityNum);

            //k近傍リスト
            int[][] kNearlestList = new int[cityNum][k];

            //k近傍リストの読み込み
            for(int i = 0; i < cityNum; i++){
                //System.out.println(j);
                String[] strArray = br.readLine().split(",");
                for(int j = 0; j < k; j++){ //都市ごとにk近傍リストを調べる
                    kNearlestList[i][j] = Integer.parseInt(strArray[j]);
                } //end for(j)
            } //end for(i)

            //逆近傍リスト
            ArrayList<Integer> reverseKList = new ArrayList<>(cityNum);

            String reverseFile = "reverseListOf" + dataName + ".csv";
            File writeFile = new File(reverseFile);
            PrintWriter pw = new PrintWriter(new FileWriter(writeFile));
            pw.println(cityNum);

            //逆近傍リストを作成
            int num; //近傍探索のための変数
            for (int i = 0; i < cityNum; i++) { //ある都市について

                for(int j = 0; j < cityNum; j++){ //リストを検索

                    for(int l = 0; l < k; l++){ //都市ごとにk近傍リストを調べる
                        if( kNearlestList[j][l] == i) { //iの都市があるとき
                            reverseKList.add(j);
                            break; //k近傍探索を打ち切り,次のリストに
                        }
                    } //end for(l)
                } //end for(j)

                //ファイルへの書き出し
                int countNum = reverseKList.size();
                pw.print(reverseKList.size() + ",");
                for(int j = 0; j < countNum; j++){
                    pw.print(reverseKList.get(j) + ",");
                }
                pw.println();

                reverseKList.clear();
            } //end for(i)

            br.close();
            pw.close();

            long end = System.currentTimeMillis(); //時間計測
            System.out.println((end - start)/1000.0/60.0 + "m");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
