// WineQualityModel.java
public class WineQualityModel {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
            .appName("WineQualityTraining")
            .master("spark://<master-ip>:7077") // Connect to cluster
            .getOrCreate();

        // Load training data
        Dataset<Row> trainingData = spark.read()
            .format("csv")
            .option("header", "true")
            .option("inferSchema", "true")
            .load("s3://your-bucket/TrainingDataset.csv");

        // Feature engineering
        VectorAssembler assembler = new VectorAssembler()
            .setInputCols(new String[]{"fixed_acidity", "volatile_acidity", ...})
            .setOutputCol("features");

        // Define model (e.g., Random Forest)
        RandomForestClassifier rf = new RandomForestClassifier()
            .setLabelCol("quality")
            .setFeaturesCol("features")
            .setNumTrees(100);

        // Pipeline
        Pipeline pipeline = new Pipeline()
            .setStages(new PipelineStage[]{assembler, rf});

        // Train model
        PipelineModel model = pipeline.fit(trainingData);

        // Save model
        model.write().overwrite().save("s3://your-bucket/model");
    }
}
