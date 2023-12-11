package org.example;

import org.opencv.core.*;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addmember extends JFrame {

    private JLabel cameraScreen;
    private JButton captureButton;
    private VideoCapture videoCapture;
    private CascadeClassifier faceCascade;

    public addmember() {
        setLayout(new BorderLayout());

        cameraScreen = new JLabel();
        add(cameraScreen, BorderLayout.CENTER);

        captureButton = new JButton("Capture Photo");
        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                capturePhoto();
            }
        });
        add(captureButton, BorderLayout.SOUTH);

        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Load the pre-trained Haarcascades face classifier
        faceCascade = new CascadeClassifier();
        faceCascade.load("E:\\face\\src\\main\\resources\\haarcascade_frontalface_default.xml");

        startCamera();
    }

    private void startCamera() {
        videoCapture = new VideoCapture(0);

        if (!videoCapture.isOpened()) {
            System.out.println("Error: Camera not found!");
            System.exit(1);
        }

        Mat frame = new Mat();
        MatOfRect faces = new MatOfRect();

        while (true) {
            videoCapture.read(frame);

            if (!frame.empty()) {
                detectAndDrawFaces(frame, faces);
                displayFrame(frame);
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void detectAndDrawFaces(Mat frame, MatOfRect faces) {
//        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.equalizeHist(frame, frame);

        faceCascade.detectMultiScale(frame, faces, 1.1, 3, 0, new Size(30, 30), new Size());

        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(frame, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 2);
        }
    }

    private void displayFrame(Mat frame) {
        ImageIcon image = matToImageIcon(frame);
        cameraScreen.setIcon(image);
    }

//    private void capturePhoto() {
//
//        Mat frame = new Mat();
//        videoCapture.read(frame);
//
//        if (!frame.empty()) {
//            MatOfRect faces = new MatOfRect();
//            faceCascade.detectMultiScale(frame, faces, 1.1, 3, 0, new Size(30, 30), new Size());
//
//            if (!faces.empty()) {
//                Rect rect = faces.toArray()[0]; // Assuming only one face is detected
//
//                // Extract the detected face from the frame
//                Mat detectedFace = new Mat(frame, rect);
//
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                String photoName = "E:\\face\\src\\images\\captured_photo_" + timeStamp + ".png";
//
//                // Save the entire frame with bounding box
//                Imgcodecs.imwrite(photoName, frame);
//                System.out.println("Photo captured: " + photoName);
//
//                String faceName = "E:\\face\\src\\images\\detected_face_" + timeStamp + ".png";
//                Imgcodecs.imwrite(faceName, detectedFace);
//                System.out.println("Detected face saved: " + faceName);
//            } else {
//                System.out.println("No face detected. Photo not saved.");
//            }
//        }
//    }
private void capturePhoto() {
    Mat frame = new Mat();
    videoCapture.read(frame);

    if (!frame.empty()) {
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(frame, faces, 1.1, 3, 0, new Size(30, 30), new Size());

        if (!faces.empty()) {
            Rect rect = faces.toArray()[0]; // Assuming only one face is detected

            // Extract the detected face from the frame
            Mat detectedFace = new Mat(frame, rect);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String photoName = "E:\\face\\src\\images\\captured_photo_" + timeStamp + ".png";

            // Save the entire frame with bounding box
            Imgcodecs.imwrite(photoName, frame);
            System.out.println("Photo captured: " + photoName);

            // Convert the entire frame to grayscale
            Mat grayFrame = new Mat();
//            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

            String bwPhotoName = "E:\\face\\src\\images\\captured_photo_bw_" + timeStamp + ".png";
            Imgcodecs.imwrite(bwPhotoName, grayFrame);
            System.out.println("Photo captured (black and white): " + bwPhotoName);

            String faceName = "E:\\face\\src\\images\\detected_face_" + timeStamp + ".png";
            Imgcodecs.imwrite(faceName, detectedFace);
            System.out.println("Detected face saved: " + faceName);
        } else {
            System.out.println("No face detected. Photo not saved.");
        }
    }
}



    public static ImageIcon matToImageIcon(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            BufferedImage bufferedImage = ImageIO.read(bis);
            return new ImageIcon(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new addmember();
    }
}
