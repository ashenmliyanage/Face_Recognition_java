package org.example;

import org.opencv.core.*;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.CvType;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.face.Face;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;

    public class FaceRecognitionSystem{

    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the pre-trained face detection model
        CascadeClassifier faceCascade = new CascadeClassifier("E:\\face\\src\\main\\resources\\haarcascade_frontalface_default.xml");

        // Create a face recognition model
        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

        // Load pre-trained face recognition model
        faceRecognizer.read("E:\\face\\src\\main\\resources\\face_recognition_model.xml");

        // Read the input image
        Mat image = Imgcodecs.imread("E:\\face\\src\\images\\dd2.jpg");

        // Convert the image to grayscale for face detection
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayImage, grayImage);

        // Detect faces in the image
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(grayImage, faces);

        // Recognize faces in the image
        for (Rect rect : faces.toArray()) {
            Mat faceMat = new Mat(grayImage, rect);
            int[] label = new int[1];
            double[] confidence = new double[1];
            faceRecognizer.predict(faceMat, label, confidence);

            // You would typically have a mapping of label to person names
            // For simplicity, we print the label and confidence here
            System.out.println("Person ID: " + label[0] + " Confidence: " + confidence[0]);
        }
    }
}
