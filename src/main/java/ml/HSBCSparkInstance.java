package ml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by louis on 12/01/17.
 */
public class HSBCSparkInstance {
    private final static String APP_NAME = "HSBC";
    private final static String MASTER_URL= "local";
    // Create a HashingTF instance to map question text to vectors of 100 features.
    private final HashingTF tf = new HashingTF(100);

    private List<Intent> intents ;
    private JavaRDD<LabeledPoint> trainingData;
    private LogisticRegressionModel model;
    private JavaSparkContext sparkContext;

    public HSBCSparkInstance(){
        SparkConf sparkConf = new SparkConf().setAppName(APP_NAME);
        sparkConf.setMaster(MASTER_URL);
        System.out.println("Launch app: "+APP_NAME+" on "+MASTER_URL);
        intents = new ArrayList<Intent>();
        sparkContext = new JavaSparkContext(sparkConf);
    }

    /**Add intent data to instance**/
    public void addIntent(Intent intent){
        Double label = intents.size()*1.0;
        intent.transform_to_points(tf, sparkContext, label);
        intents.add(intent);
        System.out.println("Intent with label "+label+" added.");
    }

    /** Use data from intents to learn and generate a model **/
    public void learnAndCreateModel(){
        System.out.println("Starting creation of the model...");
        System.out.println("Adding intent's data...");

        for( Intent intent : intents){
            /**For the first iteration trainingData is null**/
            trainingData = trainingData == null ?  intent.getRdd() :  trainingData.union(intent.getRdd());
            System.out.println("One RDD added");
        }
        trainingData.cache();
        LogisticRegressionWithLBFGS lrLBFGS = new LogisticRegressionWithLBFGS();
        lrLBFGS.setNumClasses(intents.size());
        model = lrLBFGS.run(trainingData.rdd());
        System.out.println("Model created");
    }

    public Double predict(String question){
        Vector qestionAsVector = tf.transform(Arrays.asList(question.split(" ")));
        return model.predict(qestionAsVector);
    }

    public String findResponse(String question){
        return Intent.getResponse(predict(question));
    }

    /**Getters**/
    public LogisticRegressionModel getModel(){
        return model;
    }

    public HashingTF getTf(){
        return tf;
    }
}
