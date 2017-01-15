package ml;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by louis on 12/01/17.
 */
public class Intent implements Serializable{
    private String filePath;

    private JavaRDD<LabeledPoint> rdd;

    public Intent(String filePath) {
        // Each line has text from one question.
        this.filePath = filePath;
    }


    public static String getResponse(Double label) {
        String response;
        switch (label.intValue()){
            case 0 :    response =  "Pour avoir une carte appeller l'accueil."; break;
            case 1 :    response =  "Pour prendre des congés rendez-vous sur le CRM hhtps://hcbc-interne.com"; break;
            case 2 :    response =  "Pour recruter un stagiaire prednre contct avec le pole RH."; break;
            default :   response = "Nothing matches"; break;
        }
        return response;
    }

    // Each question is split into words, and each word is mapped to one feature.
    // Create LabeledPoint datasets for each intent
    public void transform_to_points(final HashingTF tf, JavaSparkContext sparkContext, final Double label){
        JavaRDD<String> rdd_string = sparkContext.textFile(filePath);

        this.rdd = rdd_string.map(new Function<String, LabeledPoint>() {
            public LabeledPoint call(String question) {
                return new LabeledPoint(label, tf.transform(Arrays.asList(question.split(" "))));
            }
        });
        System.out.println("Question with label "+label+" has been transfromed");
    }

    public JavaRDD<LabeledPoint> getRdd() {
        return rdd;
    }
}
