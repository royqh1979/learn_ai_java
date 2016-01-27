package net.roy.learn.ai.neuralnetwork;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Modified According to "Maching Learning",Tom M. Micheal,Chap4.
 * Created by Roy on 2016/1/27.
 */
public class Neural_2H {
    private final int numIn;
    private final int numHidden1;
    private final int numHidden2;
    private final int numOut;
    private float trainingRate=0.5f;

    private final float[][] W1;
    private final float[][] W2;
    private final float[][] W3;

    public Neural_2H(int numIn, int numHidden1, int numHidden2, int numOut) {
        this.numIn = numIn;
        this.numHidden1 = numHidden1;
        this.numHidden2 = numHidden2;
        this.numOut = numOut;

        W1=new float[numIn][numHidden1];
        W2=new float[numHidden1][numHidden2];
        W3=new float[numHidden2][numOut];

        randomizeWeights();
    }

    public void randomizeWeights() {
        // Randomize weights here:
        for (int ii = 0; ii < numIn; ii++)
            for (int hh = 0; hh < numHidden1; hh++)
                W1[ii][hh] =
                        2f * (float) Math.random() - 1f;
        for (int ii = 0; ii < numHidden1; ii++)
            for (int hh = 0; hh < numHidden2; hh++)
                W2[ii][hh] =
                        2f * (float) Math.random() - 1f;
        for (int hh = 0; hh < numHidden2; hh++)
            for (int oo = 0; oo < numOut; oo++)
                W3[hh][oo] =
                        2f * (float) Math.random() - 1f;
    }

    public float[] recall(float[] in){
        Preconditions.checkArgument(in.length==numIn);
        float[] hidden1=new float[numHidden1];
        float[] hidden2=new float[numHidden2];
        float[] out=new float[numOut];

        forwardPass(in,hidden1,hidden2,out);
        return out;
    }

    /* use stateless style function, prepare for thread safe version */
    private void forwardPass(float[] in, float[] hidden1, float[] hidden2, float[] out) {
        for (int i=0;i<numHidden1;i++) {
            float temp=0;
            for (int j=0;j<numIn;j++) {
                temp+=W1[j][i]*in[j];
            }
            hidden1[i]=sigmoid(temp);
        }
        for (int i=0;i<numHidden2;i++){
            float temp=0;
            for (int j=0;j<numHidden1;j++){
                temp +=W2[j][i]* hidden1[j];
            }
            hidden2[i]+=sigmoid(temp);
        }
        for (int i=0;i<numOut;i++) {
            float temp=0;
            for (int j=0;j<numHidden2;j++) {
                 temp+=W3[j][i]* hidden2[j];
            }
            out[i]=sigmoid(temp);
        }
    }

    public float train(float[] sampleIn, float[] sampleOut){
        Preconditions.checkArgument(sampleIn.length==numIn);
        Preconditions.checkArgument(sampleOut.length==numOut);

        float[] hidden1=new float[numHidden1];
        float[] hidden2=new float[numHidden2];
        float[] out=new float[numOut];
        float[] hidden1_errors=new float[numHidden1];
        float[] hidden2_errors=new float[numHidden2];
        float[] out_errors=new float[numOut];

        forwardPass(sampleIn, hidden1, hidden2, out);

        calcErrors(sampleOut, hidden1, hidden2, out,
                hidden1_errors, hidden2_errors, out_errors);

        updateWeights(sampleIn, hidden1, hidden2, hidden1_errors, hidden2_errors, out_errors);

        float error=0.0f;
        for (int i=0;i<numOut;i++) {
            error+=(sampleOut[i]-out[i])*(sampleOut[i]-out[i])/2;
        }
        return error;
    }

    private void updateWeights(float[] sampleIn, float[] hidden1, float[] hidden2, float[] hidden1_errors, float[] hidden2_errors, float[] out_errors) {
        updateWeight(W3,hidden2, out_errors,numHidden2,numOut);
        updateWeight(W2,hidden1,hidden2_errors,numHidden1,numHidden2);
        updateWeight(W1,sampleIn,hidden1_errors,numIn,numHidden1);
    }

    private void updateWeight(float[][] weights,
                              float[] insideLayerOutputs,
                              float[] outsideLayerErrors,
                              int numInsideLayer, int numOutsideLayer) {
        Preconditions.checkArgument(insideLayerOutputs.length==numInsideLayer);
        Preconditions.checkArgument(outsideLayerErrors.length==numOutsideLayer);
        for (int i=0;i< numInsideLayer;i++) {
            for (int j=0;j< numOutsideLayer;j++) {
                weights[i][j]+=getTrainingRate()
                        *insideLayerOutputs[i]
                        *outsideLayerErrors[j];
            }
        }
    }

    private void calcErrors(float[] sampleOut, float[] hidden1,float[] hidden2, float[] out, float[] hidden1_errors, float[] hidden2_errors, float[] out_errors) {
        for (int i=0;i<numOut;i++) {
            out_errors[i]=(sampleOut[i]-out[i])*out[i]*(1-out[i]);
        }
        for (int i=0;i<numHidden2;i++) {
            float temp=0;
            for (int j=0;j<numOut;j++) {
                temp+=out_errors[j]*W3[i][j];
            }
            hidden2_errors[i]=temp*hidden2[i]*(1-hidden2[i]);
        }
        for (int i=0;i<numHidden1;i++) {
            float temp=0;
            for (int j=0;j<numHidden2;j++){
                temp+=W2[i][j]*hidden2_errors[j];
            }
            hidden1_errors[i]=temp*hidden1[i]*(1-hidden1[i]);
        }
    }

    public static float sigmoid(float x) {
        return (float) (1.0f / (1.0f + Math.exp((double) (-x))));
    }


    public int getNumIn() {
        return numIn;
    }

    public int getNumHidden1() {
        return numHidden1;
    }

    public int getNumHidden2() {
        return numHidden2;
    }

    public int getNumOut() {
        return numOut;
    }

    public float getTrainingRate() {
        return trainingRate;
    }

    public void setTrainingRate(float trainingRate) {
        this.trainingRate = trainingRate;
    }

    public static Neural_2H factory(String serialized_file_name) {
        //TODO:
        return null;
    }
}
