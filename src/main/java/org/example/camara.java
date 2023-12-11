package org.example;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import javax.swing.*;

public class camara {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Imgcodecs.imread("E:\\face\\src\\images\\assa.jpg");

        detectAndsave(image);
    }

    private static void detectAndsave(Mat image) {
        MatOfRect faces = new MatOfRect();

        Mat grayFrame = new Mat();
        Imgproc.cvtColor(image,grayFrame,Imgproc.COLOR_BGR2GRAY);

        Imgproc.equalizeHist(grayFrame,grayFrame);

        int height = grayFrame.height();
        int absolute = 0;

        if (Math.round(height*0.2f)>0){
            absolute = Math.round(height * 0.2f);
        }

        CascadeClassifier faceCascade = new CascadeClassifier();

        faceCascade.load("E:\\face\\src\\images\\haarcascade_frontalface_alt2.xml");
        faceCascade.detectMultiScale(grayFrame,faces,1.1,2,0| Objdetect.CASCADE_SCALE_IMAGE,
                new Size(absolute,absolute),new Size());

        Rect[] faceArrays = faces.toArray();

        for (int i = 0; i < faceArrays.length; i++) {
            Imgproc.rectangle(image,faceArrays[i],new Scalar(0,0,255),3);
        }
        Imgcodecs.imwrite("E:\\face\\src\\images\\Output.jpg",image);
    }

}