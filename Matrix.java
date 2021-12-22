public class Matrix {
    double [][] mat;
    int rows;
    int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        mat = new double[rows][cols];

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){

                mat[i][j] = Math.random();
            }
        }
    }

    public static Matrix multiply(Matrix A, double mult_num){
        int rows = A.rows;
        int cols = A.cols;
        Matrix temp = new Matrix(rows, cols);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                temp.mat[i][j] =  A.mat[i][j] * mult_num;
            }
        }
        return temp;
    }

    public static Matrix multiply_element(Matrix A, Matrix mult_mat){
        int rows = A.rows;
        int cols = A.cols;
        Matrix temp = new Matrix(rows, cols);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                temp.mat[i][j] = A.mat[i][j] * mult_mat.mat[i][j];
            }
        }
        return temp;
    }

    public static Matrix addMat(Matrix A, Matrix B){
        int rows = A.rows;
        int cols = A.cols;
        Matrix sum = new Matrix(rows, cols);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){

                sum.mat[i][j] = A.mat[i][j] + B.mat[i][j];
            }
        }

        return sum;
    }

    public static Matrix subMat(Matrix A, Matrix B){
        int rows = A.rows;
        int cols = A.cols;
        Matrix sub = new Matrix(rows, cols);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){

                sub.mat[i][j] = A.mat[i][j] - B.mat[i][j];
            }
        }

        return sub;
    }

    public static Matrix multMat(Matrix A, Matrix B){
        if(A.cols != B.rows){
            System.out.println("ERROR!");
        }
        int rows = A.rows;
        int cols = B.cols;
        Matrix mult = new Matrix(rows, cols);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                double temp =0;
                for(int k=0; k<A.cols; k++){
                    temp += A.mat[i][k] * B.mat[k][j];
                }
                mult.mat[i][j] = temp;
            }
        }

        return mult;
    }

    public static Matrix transpose(Matrix A){
        int rows = A.rows;
        int cols = A.cols;
        Matrix trans = new Matrix(cols,rows);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                trans.mat[j][i] = A.mat[i][j];
            }
        }
        return trans;
    }

    public void logistic(){
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                mat[i][j] = 1/(1+Math.exp(-mat[i][j]));
            }
        }
    }
    //derivative of that is e^(-x)/(1+e^(-x))^2 =
    //1-logistic(x)*logistic(x)

    public Matrix d_logistic(){
        Matrix temp = new Matrix(rows,cols);
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                temp.mat[i][j] = mat[i][j] * (1-mat[i][j]);
            }
        }
        return temp;
    }



    public double[][] getMat() {
        return mat;
    }

    public void setMat(double[][] mat) {
        this.mat = mat;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}
