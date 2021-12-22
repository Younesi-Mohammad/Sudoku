import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Num[][] A = new Num[4][4];
        List<myPoint> zeros = new CopyOnWriteArrayList<>();
        int finish =16;
        int finish1=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                A[i][j] = new Num();
                A[i][j].val = scanner.nextInt();
                A[i][j].possibles.add(1);
                A[i][j].possibles.add(2);
                A[i][j].possibles.add(3);
                A[i][j].possibles.add(4);
                if(A[i][j].val!=0) {
                    A[i][j].possibles.remove(A[i][j].possibles.indexOf(A[i][j].val));
                    finish--;
                }else{
                    myPoint mm = new myPoint();
                    mm.col = j;
                    mm.row = i;
                    mm.setLen(A[i][j].possibles.size());
                    zeros.add(mm);
                }
            }
        }

        while(finish != finish1) {
            boolean haschanged = false;
            for(myPoint mp:zeros){
                int before = A[mp.row][mp.col].possibles.size();
                forward_checking(A,mp.row,mp.col);
                mp.setLen(A[mp.row][mp.col].possibles.size());
                if(before > mp.getLen())
                    haschanged=true;
                if(mp.getLen() == 1){
                    A[mp.row][mp.col].val = A[mp.row][mp.col].possibles.get(0);
                    zeros.remove(mp);
                    finish1++;
                }
            }

            zeros.sort(Comparator.comparing(myPoint::getLen));
            if(!haschanged && A[zeros.get(zeros.size()-1).row][zeros.get(zeros.size()-1).col].val==0) {
                if(A[zeros.get(zeros.size() - 1).row][zeros.get(zeros.size() - 1).col].possibles.size()>=3)
                    A[zeros.get(zeros.size() - 1).row][zeros.get(zeros.size() - 1).col].val = A[zeros.get(zeros.size() - 1).row][zeros.get(zeros.size() - 1).col].possibles.get(2);
                else
                    A[zeros.get(zeros.size() - 1).row][zeros.get(zeros.size() - 1).col].val = A[zeros.get(zeros.size() - 1).row][zeros.get(zeros.size() - 1).col].possibles.get(0);
                zeros.remove(zeros.size()-1);
                finish1++;
            }
        }

        printArr(A);
    }

    static void forward_checking(Num A[][],int i, int j){
        for(int m=0; m<4 ; m++){
            if(A[i][j].possibles.contains(A[m][j].val)){
                A[i][j].possibles.remove(A[i][j].possibles.indexOf(A[m][j].val));
            }
        }
        for(int n=0; n<4 ; n++){
            if(A[i][j].possibles.contains(A[i][n].val)){
                A[i][j].possibles.remove(A[i][j].possibles.indexOf(A[i][n].val));
            }
        }
    }

    static void printArr(Num A[][]){
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                System.out.print(A[i][j].val + " ");
            }
            System.out.println();
        }
    }
}

class Num {
    public Num() {
        this.possibles = new ArrayList<>();
        this.val = 0;
    }

    List<Integer> possibles;
    int val;
}

class myPoint{
    int col;
    int row;
    private int len;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
