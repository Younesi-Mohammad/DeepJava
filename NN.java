public class NN {
    Matrix w_InToHidden;
    Matrix w_HiddenToOut;
    Matrix bias_hidden;
    Matrix bias_out;
    Matrix X;
    Matrix y;
    double learning_rate = 0.001;

    public NN(int input, int hidden, int out){
        w_InToHidden = new Matrix(hidden,input);
        w_HiddenToOut = new Matrix(out, hidden);
        bias_hidden = new Matrix(hidden,1);
        bias_out = new Matrix(out,1);
    }

    public void setXy(double[] X, double[] y){
        this.X = new Matrix(X.length,1);
        for(int i =0;i<X.length;i++){
            this.X.mat[i][0]=X[i];
        }
        this.y = new Matrix(y.length,1);
        for(int i =0;i<y.length;i++){
            this.y.mat[i][0]=y[i];
        }
    }

    public double train(){
        Matrix hidden = Matrix.multMat(w_InToHidden, X);
        hidden = Matrix.addMat(hidden,bias_hidden);
        hidden.logistic();
        Matrix out = Matrix.multMat(w_HiddenToOut,hidden);
        out = Matrix.addMat(out,bias_out);
        out.logistic();

        Matrix error = Matrix.subMat(y,out);
        double[][] sq_error = Matrix.multMat(Matrix.transpose(error), error).mat;

        /*back propagation to update hidden to out weight matrix
        J = 1/2(y - out)^2 ==> dJ/dout = -(y-out)
        out = Logistic(a) ==> dout/da = (1-out)(out)
        a = W_hidden_to_out * hidden + bias_out ==> da/dW_hidden_to_out = Transpose(hidden)
        ==> W_hidden_to_out = W_hidden_to_out - alpha * dJ/dW_hidden_to_out
        ==> W_hidden_to_out = W_hidden_to_out + alpha * (y-out) * (1-out)(out) * Transpose(hidden)
         */
        Matrix gradient = out.d_logistic();
        gradient = Matrix.multiply_element(gradient,error);
        gradient = Matrix.multiply(gradient, learning_rate);
        //System.out.println("rows: " + gradient.rows);
        //System.out.println("cols: " + gradient.cols);
        Matrix hToOut_upd = Matrix.multMat(gradient, Matrix.transpose(hidden));
        w_HiddenToOut = Matrix.addMat(w_HiddenToOut, hToOut_upd);
        bias_out = Matrix.addMat(bias_out, gradient);

        /*back propagation to update input to hidden weight matrix
        IMPORTANT: a is before logistic activation function of output layer, alpha : learning rate
        dJ/dW_input_to_hidden = dJ/da * da/dhidden * dhidden/dbeforeActivation * dbeforeActivation/dW_input_to_hidden
        dJ/da = -(y-out) * (1-out)(out)
        da/dhidden = Transpose(W_hidden_to_out)
        dhidden/dbeforeActivation = hidden(1-hidden)
        dbeforeActivation/dW_input_to_hidden = Transpose(X)
        ==> W_input_to_hidden = W_input_to_hidden + alpha * (y-out) * (1-out)(out) * Transpose(W_hidden_to_out) * hidden(1-hidden) * Transpose(X)
         */

        Matrix h_error = Matrix.multMat(Matrix.transpose(w_HiddenToOut), gradient);
        Matrix h_grad = hidden.d_logistic();
        h_grad = Matrix.multiply_element(h_grad, h_error);
        Matrix InToHUpd = Matrix.multMat(h_grad, Matrix.transpose(X));
        w_InToHidden = Matrix.addMat(w_InToHidden, InToHUpd);
        bias_hidden = Matrix.addMat(bias_hidden, h_grad);

        return sq_error[0][0];
    }

    public double[][] predict(double[] X){
        Matrix input = new Matrix(X.length,1);
        for(int i =0;i<X.length;i++){
            input.mat[i][0]=X[i];
        }
        Matrix hidden = Matrix.multMat(w_InToHidden, input);
        hidden = Matrix.addMat(hidden,bias_hidden);
        hidden.logistic();
        Matrix out = Matrix.multMat(w_HiddenToOut,hidden);
        out = Matrix.addMat(out,bias_out);
        out.logistic();

        return out.mat;
    }

    public void fit(double[][]X, double[][]y, int epochs){
        for(int i=0; i<epochs; i++){
            int sample = (int) (Math.random() * X.length);
            setXy(X[sample], y[sample]);
            double error = this.train();
            if(i%100 == 0){
                System.out.println("Error at epoch " + i + ": "+ error);
            }
        }
    }

}
