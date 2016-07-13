import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yamada on 2016/07/07.
 */
public class T2Opt {

    public static void main(String[] args) throws Exception{

//        String fileName = "src/ca4663.tsp";
//        String kNearlestFileName = "kNearlestListOfca4663.csv";
//        String reverseListFileName = "reverseListOfca4663.csv";
//        String dataName = "ca4663";
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

//        String fileName = "src/ch71009.tsp";
//        String kNearlestFileName = "kNearlestListOfch71009.csv";
//        String reverseListFileName = "reverseListOfch71009.csv";
//        String dataName = "ch71009";

        String fileName = "src/mona-lisa100K.tsp";
        String kNearlestFileName = "kNearlestListOfmonalisa.csv";
        String reverseListFileName = "reverseListOfmonalisa.csv";
        String dataName = "monalisa";
//
        TInstance cityData; //都市データ
        int[][] kNearlestList; //k近傍リスト
        ArrayList<Integer>[] reverseList; //逆近傍リスト
        int cityNum;
        int k = 50;
        Random random = new Random();

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

        long[] time = new long[11];

        for(int count = 0; count < 11; count++) {
            TRoute route = new TRoute(cityData);
            route.init();
            System.out.println("init tourLength : " + route.getfTourLength());

            int fNoOfCandidates = 0;
            int[] fCandidates = new int[cityNum];
            boolean[] fIsCandidate = new boolean[cityNum];

            for (int i = 0; i < cityNum; i++) {
                fCandidates[i] = i;
                fIsCandidate[i] = true;
                fNoOfCandidates++;
            }

            int positionOfv_a;
            int v_a, v_b, v_c, v_d;
            int i;
            boolean jumpTo2;
            //int count = 0;

            long start = System.currentTimeMillis(); //時間計測

            while (fNoOfCandidates > 0) { //ステップ2
                positionOfv_a = random.nextInt(fNoOfCandidates);
                v_a = fCandidates[positionOfv_a];

                i = 0;
                //System.out.println();
                //System.out.println("count" + count++);

                jumpTo2 = false;

                while (i < 2) {

                    if (i == 0) {
                        v_b = route.nextFromId(v_a);
                    } else {
                        v_b = route.prevFromId(v_a);
                    }

                    for (int j = 0; j < k; j++) { //ステップ4

                        v_c = kNearlestList[v_a][j]; //4.a

                        if (-route.calcDistance(v_a, v_b) + route.calcDistance(v_a, v_c) >= 0.0) { //4.b
                            //ステップ5へ
                            break;
                        }

                        if (i == 0) { //4.c
                            v_d = route.nextFromId(v_c);
                        } else {
                            v_d = route.prevFromId(v_c);
                        }

                        //4.d
                        if (-route.calcDistance(v_a, v_b) - route.calcDistance(v_c, v_d) + route.calcDistance(v_a, v_c) + route.calcDistance(v_b, v_d) < 0.0) {
                            //System.out.println("swap");
                            route.swapEdge(v_a, v_b, v_c, v_d);

                            //vb, vc,vdのうち,Hに含まれていないものをHに追加
                            if (!fIsCandidate[v_b]) {
                                fIsCandidate[v_b] = true;
                                fCandidates[fNoOfCandidates++] = v_b;
                            }
                            if (!fIsCandidate[v_c]) {
                                fIsCandidate[v_c] = true;
                                fCandidates[fNoOfCandidates++] = v_c;
                            }
                            if (!fIsCandidate[v_d]) {
                                fIsCandidate[v_d] = true;
                                fCandidates[fNoOfCandidates++] = v_d;
                            }

                            //va, vb, vc,vdをk近傍に持つ都市のうち,Hに含まれていないものをHに追加してステップ2へ
                            int sizeOfAList = reverseList[v_a].size();
                            int sizeOfBList = reverseList[v_b].size();
                            int sizeOfCList = reverseList[v_c].size();
                            int sizeOfDList = reverseList[v_d].size();

                            if (sizeOfAList <= sizeOfBList && sizeOfAList <= sizeOfCList && sizeOfAList <= sizeOfDList) {
                                for (int l = 0; l < sizeOfAList; l++) {
                                    if (reverseList[v_b].contains(reverseList[v_a].get(l))) {
                                        if (reverseList[v_c].contains(reverseList[v_a].get(l))) {
                                            if (reverseList[v_d].contains(reverseList[v_a].get(l))) {
                                                if (!fIsCandidate[l]) {
                                                    fIsCandidate[l] = true;
                                                    fCandidates[fNoOfCandidates++] = l;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (sizeOfBList <= sizeOfAList && sizeOfBList <= sizeOfCList && sizeOfBList <= sizeOfDList) {
                                for (int l = 0; l < sizeOfBList; l++) {
                                    if (reverseList[v_a].contains(reverseList[v_b].get(l))) {
                                        if (reverseList[v_c].contains(reverseList[v_b].get(l))) {
                                            if (reverseList[v_d].contains(reverseList[v_b].get(l))) {
                                                if (!fIsCandidate[l]) {
                                                    fIsCandidate[l] = true;
                                                    fCandidates[fNoOfCandidates++] = l;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (sizeOfCList <= sizeOfAList && sizeOfCList <= sizeOfBList && sizeOfCList <= sizeOfDList) {
                                for (int l = 0; l < sizeOfCList; l++) {
                                    if (reverseList[v_a].contains(reverseList[v_c].get(l))) {
                                        if (reverseList[v_b].contains(reverseList[v_c].get(l))) {
                                            if (reverseList[v_d].contains(reverseList[v_c].get(l))) {
                                                if (!fIsCandidate[l]) {
                                                    fIsCandidate[l] = true;
                                                    fCandidates[fNoOfCandidates++] = l;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                for (int l = 0; l < sizeOfDList; l++) {
                                    if (reverseList[v_a].contains(reverseList[v_d].get(l))) {
                                        if (reverseList[v_b].contains(reverseList[v_d].get(l))) {
                                            if (reverseList[v_c].contains(reverseList[v_d].get(l))) {
                                                if (!fIsCandidate[l]) {
                                                    fIsCandidate[l] = true;
                                                    fCandidates[fNoOfCandidates++] = l;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            jumpTo2 = true;
                            break;

                        } //end if 4.d
                    }// end for(j)

                    if (jumpTo2 == true) {
                        break;
                    }

                    i += 1; //ステップ5
                    if (i == 2) {
                        //System.out.println("remove Num : " + v_a);
                        fIsCandidate[v_a] = false;
                        fNoOfCandidates--;
                        fCandidates[positionOfv_a] = fCandidates[fNoOfCandidates];
//                    route.calcTourLength();
//                    System.out.println("tourLength : " + route.getfTourLength());
                        jumpTo2 = true;
                    }

                } //end while(i < 2)
                //System.out.println("H size : " + cityListH.size());
            } // while (cityListH.size() > 0)

            long end = System.currentTimeMillis(); //時間計測
            System.out.println();
            System.out.println((end - start) / 1000.0 + "s");

            route.calcTourLength();
            System.out.println("final tourLength : " + route.getfTourLength());

            time[count] = (end - start);
        }

        long aveTime = 0;
        for(int count = 1; count < 11; count++){
            aveTime += time[count];
        }

        aveTime = aveTime / 10;
        System.out.println("aveTIme = " + aveTime / 1000.0 + "s");


    }//end 2-Opt main

}
