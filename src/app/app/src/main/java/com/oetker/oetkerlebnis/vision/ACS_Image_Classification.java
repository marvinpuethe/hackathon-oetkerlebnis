package com.oetker.oetkerlebnis.vision;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

public class ACS_Image_Classification {

    private static byte[] GetImage(String path)
    {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            // Compress image to reduce file size for Azure Custom Vision API
            Bitmap original = BitmapFactory.decodeFile(path);
            original.compress(Bitmap.CompressFormat.JPEG, 80, os);

            return os.toByteArray();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public double run_object_recognition(String path) {
        final String trainingApiKey = "54b99ae4ff6c413ca0059f2ba295fd38";
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
        }

        Project project = trainer.getProject(UUID.fromString("0dbbe807-f10d-4fa3-8244-b7dbf55a771f"));

        final String predictionApiKey = "bb605890bc14416aa5f27150a9525d44";
        PredictionEndpoint predictClient = CustomVisionPredictionManager.authenticate(predictionApiKey);

        byte[] testImage = GetImage(path);

        ImagePrediction results = predictClient.predictions().predictImage()
                .withProjectId(project.id())
                .withImageData(testImage)
                .execute();

        // Check for max prediction of classified objects
        double maxPrediction = 0;
        for (Prediction prediction: results.predictions())
        {
            if (prediction.probability() > maxPrediction) {
                maxPrediction = prediction.probability();
            }
        }

        return (maxPrediction * 100);
    }
}
