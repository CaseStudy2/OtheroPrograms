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
    private String state; // �I�Z���̐F�����cB�A���cW�A��cE
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
  private List<Koma> komalist;                  //�R�}���̃I�u�W�F�N�g�����������X�g
  private int ynum = 0;
  private int xnum = 0;

  public Field(int xnum, int ynum){             //�R���X�g���N�^�ŔՖ�(x*y)������
    this.xnum = xnum;
    this.ynum = ynum;
  }

  public void prepare(){                        //�����i�K
    this.komalist = new ArrayList<>();          //���̃��X�g�쐬�F�I�u�W�F�N�g���X�g���́A"komalist"
    for(int y=0;y<this.ynum;y++){
      for(int x=0;x<this.xnum;x++){
        Koma koma = new Koma(x,y);              //�Ֆʈ��ɃI�u�W�F�N�g�𐶐��F�I�u�W�F�N�g���́A"koma"
        this.komalist.add(koma);                //�����koma�I�u�W�F�N�g��komalist�ɂǂ�ǂ�ǉ�
      }
    }
  }

  public Koma getKoma(int y, int x){            //�I�u�W�F�N�g�����^�[��
    for(Koma koma : this.komalist){             //komalist�ɑ΂��āA�Ֆʂ̑S�ẴI�u�W�F�N�g�ɃA�N�Z�X����g��for��
      int[]pos = koma.getPosition();            //koma�I�u�W�F�N�g������W�������
      if(pos[0]==y && pos[1]==x){               //�����Ώۂ̃I�u�W�F�N�g��������΁A����koma�I�u�W�F�N�g�����^�[��
        return koma;
      }
    }
    return null;                                //������񎞃k���k�����y�y���[�V����
  }

  public void putKoma(int x, int y, String state){
    Koma koma = this.getKoma(x,y);              //�w����W��koma�I�u�W�F�N�g�����
    koma.setState(state);                       //��ɓ��ꂽ�I�u�W�F�N�g�̃X�e�[�^�X�ύX
  }

  public void feature(){
    String [][] board = new String[ynum][xnum]; //�񎟌��z����`�F���O��"board"
    for(Koma koma : this.komalist){             //komalist���g��for���őS�ĎQ��
      int[] pos = koma.getPosition();           //pos�z��ɍ��W������
      String state = koma.getState();           //�X�e�[�^�X���Q�b�g
      board[pos[1]][pos[0]] = state;            //board�z��ɃX�e�[�^�X������
    }                                           //board�z�񂪂���Ŗ��t�ɂȂ�

    System.out.println("\ny\\x\t0\t1\t2\t3\t4\t5\t6\t7\n");//�ȉ��A�o��
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
