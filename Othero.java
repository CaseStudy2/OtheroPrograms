import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Othero{
  public static void main(String[] args){       
    Scanner sc = new Scanner(System.in);
    Field field = new Field(8,8);
    int x,y;
    String state;
    field.prepare();
    field.putKoma(3,3,"W");
    field.putKoma(3,4,"B");
    field.putKoma(4,3,"B");
    field.putKoma(4,4,"W");
    System.out.print("オセロを始めます。");
    do{
      field.feature();
      System.out.println("置く場所：(x,y,state)");
      try{
        x = sc.nextInt();
        y = sc.nextInt();
        state = sc.next();
        field.turnKoma(x, y, state);
      }catch(InputMismatchException e){
        System.out.println("オセロ終了");
        break;
      }
    }while(true);
  }
}

class Koma{
    private String state; // オセロの色が黒…B、白…W、空…E、置けるマス…C
    private int x;
    private int y;

    public Koma(int x,int y){
        this.state = "E";
        this.x = x;
        this.y = y;
    }

    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

    public int[] getPosition(){
        int[]pos = {this.x, this.y};
        return pos;
    }
}

class Field{
  private List<Koma> komalist;                  //コマ一個一個のオブジェクトを持ったリスト
  private List<Koma> komaturnlist;       //ひっくり返すコマのオブジェクトを格納するリスト
  private int ynum = 0;
  private int xnum = 0;

  public Field(int xnum, int ynum){             //コンストラクタで盤面(x*y)を決定
    this.xnum = xnum;
    this.ynum = ynum;
  }

  public void prepare(){                        //準備段階
    this.komalist = new ArrayList<>();          //無のリスト作成：オブジェクトリスト名は、"komalist"
    this.komaturnlist = new ArrayList<>();      //無のリスト作成：オブジェクトリスト名は、"komaturnlist"
    for(int y=0;y<this.ynum;y++){
      for(int x=0;x<this.xnum;x++){
        Koma koma = new Koma(x,y);              //盤面一つ一つにオブジェクトを生成：オブジェクト名は、"koma"
        this.komalist.add(koma);                //作ったkomaオブジェクトをkomalistにどんどん追加
      }
    }
  }

  public Koma getKoma(int y, int x){            //オブジェクトをリターン
    for(Koma koma : this.komalist){             //komalistに対して、盤面の全てのオブジェクトにアクセスする拡張for文
      int[]pos = koma.getPosition();            //komaオブジェクトから座標情報を入手
      if(pos[0]==y && pos[1]==x){               //検索対象のオブジェクトが見つかれば、そのkomaオブジェクトをリターン
        return koma;
      }
    }
    return null;                                //見つからん時ヌルヌルをペペローション
  }

  public void putKoma(int x, int y, String state){//指定の場所に置くだけ
    Koma koma = this.getKoma(x,y);              //指定座標のkomaオブジェクトを入手
    koma.setState(state);                       //手に入れたオブジェクトのステータス変更
  }

  public void feature(){
    System.out.println("print");
    String [][] board = new String[ynum][xnum]; //二次元配列を定義：名前は"board"
    for(Koma koma : this.komalist){             //komalistを拡張for文で全て参照
      int[] pos = koma.getPosition();           //pos配列に座標情報を代入
      String state = koma.getState();           //ステータスもゲット
      board[pos[1]][pos[0]] = state;            //board配列にステータスを入れる
    }                                           //board配列がこれで満杯になる

    System.out.println("\ny\\x\t0\t1\t2\t3\t4\t5\t6\t7\n");//以下、出力
    for(int y=0;y<board.length;y++){
      System.out.print(y+"\t");
      for(int x=0;x<board[0].length;x++){
        String b = board[y][x];
        if(b.equals("W"))System.out.print("●\t");
        else if(b.equals("B"))System.out.print("○\t");
        else System.out.print(b+"\t");
      }
      System.out.println("\n");
    }
  }

  public void turnKoma(int x, int y, String state){//ある方向を見て、敵の色だった場合、自分の色が来るまでオブジェクトを入手し、リストに格納する。
    String enemyState = state.equals("B") ? "W" : "B" ;                 //enemyStateに敵の色を代入
    for(int i = 0; i < 3; i++){
     for(int j = 0; j < 3; j++){
        if(j==1 && i==1)continue;                                                 //この座標は置いた場所だから処理はスキップ
        if(x-1+j < 0 || y-1+i < 0 || x-1+j > 7 || y-1+i > 7)continue;             //盤面外に行く場合、その方向の処理をスキップ
        Koma koma = this.getKoma(x-1+j,y-1+i);                                    //j-1、i+1にすることで、２重ループのi,jで、北西、北、北東、西、東、南西、南、南東の順に座標を参照していく

        if(koma.getState().equals(enemyState)){                                   //そのコマが敵の色の場合
          this.komaturnlist.add(this.getKoma(x,y));                               //置いたkomaも忘れずにkomaturnlistに追加
          
          for(int k = 1; k <= 8 && !(koma.getState().equals(state)); k++){        //komaのステータスが味方の色でない時for文で回す。最大7回まで。つまり8回目は絶対盤面外行く。
            if(x+(j-1)*k < 0 || y+(i-1)*k < 0 || x+(j-1)*k > 7 || y+(i-1)*k > 7 || koma.getState().equals("E")){//盤面外に行ったかどうか、もしくはEmptyマスに入ったか
              this.komaturnlist.clear();                                          //komaturnlistをクリア、下の拡張for文も実行されない。
              break;
            }
            koma = this.getKoma(x+(j-1)*k, y+(i-1)*k);                               //続くkomaをどんどん参照
            this.komaturnlist.add(koma);                                          //ひっくり返すkomaオブジェクトを格納しておくkomaturnlistに仮入れ
          }

          for(Koma tempkoma : this.komaturnlist){                                 //komaturnlistを全参照して、tempkomaオブジェクトに入れる
            koma = tempkoma;                                                      //komaオブジェクトにtempkomaオブジェクトを入れておいて、
            koma.setState(state);                                                 //komaオブジェクトの色を変更して、
            this.komalist.set(this.komalist.indexOf(tempkoma), koma);             //tempkomaオブジェクトに該当するオブジェクトindexOfでkomalistからindexf()で検索し、一行前にステータスを変更したkomaオブジェクトにset()で入れ替える
          }
          this.komaturnlist.clear();
        }
      }
    }
  }
}