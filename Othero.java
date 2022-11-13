import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Othero{
    public static void main(String[] args){       
        Scanner sc = new Scanner(System.in);
        Field field = new Field(8,8);
        field.prepare();
        field.putKoma(3,3,"W");
        field.putKoma(3,4,"B");
        field.putKoma(4,3,"B");
        field.putKoma(4,4,"W");
        field.feature();
    }
}

class Koma{
    private String state; // オセロの色が黒…B、白…W、空…E
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
  private int ynum = 0;
  private int xnum = 0;

  public Field(int xnum, int ynum){             //コンストラクタで盤面(x*y)を決定
    this.xnum = xnum;
    this.ynum = ynum;
  }

  public void prepare(){                        //準備段階
    this.komalist = new ArrayList<>();          //無のリスト作成：オブジェクトリスト名は、"komalist"
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

  public void putKoma(int x, int y, String state){
    Koma koma = this.getKoma(x,y);              //指定座標のkomaオブジェクトを入手
    koma.setState(state);                       //手に入れたオブジェクトのステータス変更
  }

  public void feature(){
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
        System.out.print(b+"\t");
      }
      System.out.println("\n");
    }
  }
}
