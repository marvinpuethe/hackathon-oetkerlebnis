package com.oetker.oetkerlebnis.vision;

import android.support.annotation.Nullable;

import com.google.common.io.ByteStreams;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.CustomVisionPredictionManager;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.PredictionEndpoint;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.models.ImagePrediction;
import com.microsoft.azure.cognitiveservices.vision.customvision.prediction.models.Prediction;
import com.microsoft.azure.cognitiveservices.vision.customvision.training.CustomVisionTrainingManager;
import com.microsoft.azure.cognitiveservices.vision.customvision.training.TrainingApi;
import com.microsoft.azure.cognitiveservices.vision.customvision.training.Trainings;
import com.microsoft.azure.cognitiveservices.vision.customvision.training.models.Classifier;
import com.microsoft.azure.cognitiveservices.vision.customvision.training.models.Domain;
import com.microsoft.azure.cognitiveservices.vision.customvision.training.models.DomainType;
import com.microsoft.azure.cognitiveservices.vision.customvision.training.models.Project;

import java.util.List;

public class ACS_Image_Classification {
    private static byte[] GetImage(String folder, String fileName)
    {
        try {
            return ByteStreams.toByteArray(ACS_Image_Classification.class.getResourceAsStream(folder + "/" + fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public String run_object_recognition(String path, String file) {
        final String trainingApiKey = "insert your training key here";
        TrainingApi trainClient = CustomVisionTrainingManager.authenticate(trainingApiKey);

        System.out.println("Object Detection Sample");
        Trainings trainer = trainClient.trainings();

        Domain objectDetectionDomain = null;
        List<Domain> domains = trainer.getDomains();
        for (final Domain domain : domains) {
            if (domain.type() == DomainType.OBJECT_DETECTION) {
                objectDetectionDomain = domain;
                break;
            }
        }

        if (objectDetectionDomain == null) {
            System.out.println("Unexpected result; couldn't find object detection domain.");
            return null;
        }

        Project project = trainer.createProject()
                .withName("Oetkerlebnis")
                .withDescription("Dr. Oetker interaktive Erlebnistour")
                .withDomainId(objectDetectionDomain.id())
                .withClassificationType(Classifier.MULTILABEL.toString())
                .execute();

        final String predictionApiKey = "insert your prediction key here";
        PredictionEndpoint predictClient = CustomVisionPredictionManager.authenticate(predictionApiKey);

        byte[] testImage = GetImage(path, file);

        ImagePrediction results = predictClient.predictions().predictImage()
                .withProjectId(project.id())
                .withImageData(testImage)
                .execute();

        for (Prediction prediction: results.predictions())
        {
            return String.format("\t%s: %.2f%% at: %.2f, %.2f, %.2f, %.2f",
                    prediction.tagName(),
                    prediction.probability() * 100.0f,
                    prediction.boundingBox().left(),
                    prediction.boundingBox().top(),
                    prediction.boundingBox().width(),
                    prediction.boundingBox().height()
            );
        }

        return null;
    }
}
