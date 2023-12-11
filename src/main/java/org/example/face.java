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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class face extends JFrame {

    private JLabel cameraScreen;
    private VideoCapture videoCapture;
    private CascadeClassifier faceCascade;

    public face() {
        setLayout(new BorderLayout());

        cameraScreen = new JLabel();
        add(cameraScreen, BorderLayout.CENTER);

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

//    private ImageIcon matToImageIcon(Mat mat) {
//        MatOfByte byteMat = new MatOfByte();
//        Imgcodecs.imencode(".jpg", mat, byteMat);
//        byte[] byteArray = byteMat.toArray();
//
//        BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
//
//        for (int i = 0; i < byteArray.length; i++) {
//            image.getRaster().setDataElements(i % mat.width(), i / mat.width(), 1, 1,
//                    new int[]{byteArray[i] & 0xFF, byteArray[i] & 0xFF, byteArray[i] & 0xFF});
//        }
//
//        return new ImageIcon(image);
//    }


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
        new face();
    }
}
