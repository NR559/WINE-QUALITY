// Load validation data
Dataset<Row> validationData = spark.read().csv("ValidationDataset.csv");

// Evaluate F1 score
MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("quality")
    .setMetricName("f1");

double f1 = evaluator.evaluate(model.transform(validationData));
