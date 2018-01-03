package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private ImageView imgView1;

    @FXML
    private ImageView imgView2;

    @FXML
    private ImageView imgView3;

    @FXML
    private ImageView imgView4;

    @FXML
    private ImageView imgView6;

    @FXML
    private ImageView imgView5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //ładowanie biblioteki OpenCV
        detect();
    }
    public void edgeDetection(String nameOfImage,String nameOfSavedImage,ImageView imageView){
        Mat RGBImage = Imgcodecs.imread(nameOfImage);

        Mat GrayImage = new Mat();
        Mat Myimg = new Mat();
        Mat FinalImage = new Mat();

         /*FUNKCJA KONWERTUJĄCA WYBRANY MODEL KOLORÓW
         *Pierwszym argumentem jest obraz, z którego chcemy dokonać konwersji, drugi to obraz,
         * który otrzymany w wyniku konwersji,
         * ostatnim argumentem jest stała określająca pomiędzy jakimi przestrzeniami barw zostanie dokonana konwersja.
         */

        Imgproc.cvtColor(RGBImage,GrayImage,Imgproc.COLOR_RGB2GRAY);

        /*Pierwszy argument to obraz w skali szarości
        drugi argument to to co ma być na wyjściu
        trzeci i czwarty to są wartości progowe thresold
        apertureSize - rozmiar, wymiar określający filtr Sobela
        false: a flag, indicating whether to use a more accurate calculation of the magnitude gradient.
ABY OBRAZ BYŁ MONOCHROMATYCZNY
        * */
        Imgproc.Canny(GrayImage,Myimg,50,150,3,false);

         /*
CV_8U is unsigned 8bit/pixel - ie a pixel can have values 0-255, this is the normal range for most image and video formats.

CV_32F is float - the pixel can have any value between 0-1.0, this is useful for some sets of calculations on data - but it has to be converted into 8bits to save or display by multiplying each pixel by 255.

CV_32S is a signed 32bit integer value for each pixel - again useful of you are doing integer maths on the pixels, but again needs converting into 8bits to save or display. This is trickier since you need to
decide how to convert the much larger range of possible values (+/- 2billion!) into 0-255*/

        Myimg.convertTo(FinalImage, CvType.CV_8U);

        if(Imgcodecs.imwrite(nameOfSavedImage,FinalImage))
            System.out.println("Krawędzie zostały wykryte!");

        File file = new File(nameOfSavedImage);
        Image image = new Image(file.toURI().toString());

        // simple displays ImageView the image as is
        imageView.setImage(image);

    }
    @FXML
    public void detect(){
        edgeDetection("obr/samolot19.jpg","obr/edge1.jpg",imgView1);
        edgeDetection("obr/samolot09.jpg","obr/edge2.jpg",imgView2);
        edgeDetection("obr/samolot02.jpg","obr/edge3.jpg",imgView3);
        edgeDetection("obr/samolot11.jpg","obr/edge4.jpg",imgView4);
        edgeDetection("obr/samolot12.jpg","obr/edge5.jpg",imgView5);
        edgeDetection("obr/samolot10.jpg","obr/edge6.jpg",imgView6);
    }
    @FXML
    void showNormalImage(MouseEvent event) {

        normalImage("obr/samolot19.jpg",imgView1);
        normalImage("obr/samolot09.jpg",imgView2);
        normalImage("obr/samolot02.jpg",imgView3);
        normalImage("obr/samolot11.jpg",imgView4);
        normalImage("obr/samolot12.jpg",imgView5);
        normalImage("obr/samolot10.jpg",imgView6);

    }
    public void normalImage(String nameImage,ImageView imageView){

        File file = new File(nameImage);
        Image image = new Image(file.toURI().toString());

        imageView.setImage(image);
    }

}
