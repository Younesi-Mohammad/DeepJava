public class Main {

    public static void main(String[] args) {
        double[][] X = {{0,0,0,0},
                {0,0,0,1},
                {0,0,1,0},
                {0,1,0,0},
                {1,0,0,0},
                {0,0,1,1},
                {0,1,0,1},
                {1,0,0,1},
                {0,1,1,0},
                {1,0,1,0},
                {1,1,0,0},
                {0,1,1,1},
                {1,0,1,1},
                {1,1,0,1},
                {1,1,1,0},
                {1,1,1,1}};
        double[][] y = {{1},{0},{0},{0},{0},
                {1},{1},{1},{1},{1},{1},
                {0},{0},{0},{0},{1}};

        NN nn = new NN(4,8,1);
        nn.fit(X,y,50000);

        System.out.println("\n---------------");
        System.out.println("Testing:");
        for(int i=0; i<16; i++){
            double out = nn.predict(X[i])[0][0];
            int res = 0;
            if(out>0.5){
                res = 1;
            }

            System.out.print(matToStr(X[i]) + ": expected output: "+ y[i][0]+
                " NN output: " + out + " parity from NN: " + res+"\n");
        }


    }

    public static String matToStr(double[] X){
        String out = "[";
        for(double x:X){
            out += x + ", ";
        }
        out = out.substring(0, out.length()-2) + "]";

        return out;
    }
}
