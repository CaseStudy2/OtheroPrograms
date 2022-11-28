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
    System.out.print("�I�Z�����n�߂܂��B");
    do{
      field.feature();
      System.out.println("�u���ꏊ�F(x,y,state)");
      try{
        x = sc.nextInt();
        y = sc.nextInt();
        state = sc.next();
        field.turnKoma(x, y, state);
      }catch(InputMismatchException e){
        System.out.println("�I�Z���I��");
        break;
      }
    }while(true);
  }
}

class Koma{
    private String state; // �I�Z���̐F�����cB�A���cW�A��cE�A�u����}�X�cC
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
  private List<Koma> komaturnlist;       //�Ђ�����Ԃ��R�}�̃I�u�W�F�N�g���i�[���郊�X�g
  private int ynum = 0;
  private int xnum = 0;

  public Field(int xnum, int ynum){             //�R���X�g���N�^�ŔՖ�(x*y)������
    this.xnum = xnum;
    this.ynum = ynum;
  }

  public void prepare(){                        //�����i�K
    this.komalist = new ArrayList<>();          //���̃��X�g�쐬�F�I�u�W�F�N�g���X�g���́A"komalist"
    this.komaturnlist = new ArrayList<>();      //���̃��X�g�쐬�F�I�u�W�F�N�g���X�g���́A"komaturnlist"
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

  public void putKoma(int x, int y, String state){//�w��̏ꏊ�ɒu������
    Koma koma = this.getKoma(x,y);              //�w����W��koma�I�u�W�F�N�g�����
    koma.setState(state);                       //��ɓ��ꂽ�I�u�W�F�N�g�̃X�e�[�^�X�ύX
  }

  public void feature(){
    System.out.println("print");
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
        if(b.equals("W"))System.out.print("��\t");
        else if(b.equals("B"))System.out.print("��\t");
        else System.out.print(b+"\t");
      }
      System.out.println("\n");
    }
  }

  public void turnKoma(int x, int y, String state){//������������āA�G�̐F�������ꍇ�A�����̐F������܂ŃI�u�W�F�N�g����肵�A���X�g�Ɋi�[����B
    String enemyState = state.equals("B") ? "W" : "B" ;                 //enemyState�ɓG�̐F����
    for(int i = 0; i < 3; i++){
     for(int j = 0; j < 3; j++){
        if(j==1 && i==1)continue;                                                 //���̍��W�͒u�����ꏊ�����珈���̓X�L�b�v
        if(x-1+j < 0 || y-1+i < 0 || x-1+j > 7 || y-1+i > 7)continue;             //�ՖʊO�ɍs���ꍇ�A���̕����̏������X�L�b�v
        Koma koma = this.getKoma(x-1+j,y-1+i);                                    //j-1�Ai+1�ɂ��邱�ƂŁA�Q�d���[�v��i,j�ŁA�k���A�k�A�k���A���A���A�쐼�A��A�쓌�̏��ɍ��W���Q�Ƃ��Ă���

        if(koma.getState().equals(enemyState)){                                   //���̃R�}���G�̐F�̏ꍇ
          this.komaturnlist.add(this.getKoma(x,y));                               //�u����koma���Y�ꂸ��komaturnlist�ɒǉ�
          
          for(int k = 1; k <= 8 && !(koma.getState().equals(state)); k++){        //koma�̃X�e�[�^�X�������̐F�łȂ���for���ŉ񂷁B�ő�7��܂ŁB�܂�8��ڂ͐�ΔՖʊO�s���B
            if(x+(j-1)*k < 0 || y+(i-1)*k < 0 || x+(j-1)*k > 7 || y+(i-1)*k > 7 || koma.getState().equals("E")){//�ՖʊO�ɍs�������ǂ����A��������Empty�}�X�ɓ�������
              this.komaturnlist.clear();                                          //komaturnlist���N���A�A���̊g��for�������s����Ȃ��B
              break;
            }
            koma = this.getKoma(x+(j-1)*k, y+(i-1)*k);                               //����koma���ǂ�ǂ�Q��
            this.komaturnlist.add(koma);                                          //�Ђ�����Ԃ�koma�I�u�W�F�N�g���i�[���Ă���komaturnlist�ɉ�����
          }

          for(Koma tempkoma : this.komaturnlist){                                 //komaturnlist��S�Q�Ƃ��āAtempkoma�I�u�W�F�N�g�ɓ����
            koma = tempkoma;                                                      //koma�I�u�W�F�N�g��tempkoma�I�u�W�F�N�g�����Ă����āA
            koma.setState(state);                                                 //koma�I�u�W�F�N�g�̐F��ύX���āA
            this.komalist.set(this.komalist.indexOf(tempkoma), koma);             //tempkoma�I�u�W�F�N�g�ɊY������I�u�W�F�N�gindexOf��komalist����indexf()�Ō������A��s�O�ɃX�e�[�^�X��ύX����koma�I�u�W�F�N�g��set()�œ���ւ���
          }
          this.komaturnlist.clear();
        }
      }
    }
  }
}