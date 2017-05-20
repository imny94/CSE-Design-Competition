package sample;

import Server.BackgroundFireBase;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class LoginController implements Initializable {
    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton signup;

    @FXML
    private JFXTextField username;

    @FXML
    private StackPane stackPane;

    public static String userN, passW;


    @FXML
    void authenticate(ActionEvent event) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InterruptedException {


        System.out.println("Authenticating");
        String username = this.username.getText();
        String password = this.password.getText();
        final boolean[] authenticated = {false};

        //AES the password
        // Generate the secret key specs.
        byte[] user = username.getBytes();
        byte[] key = password.getBytes();
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);

        user = sha.digest(user);
        user = Arrays.copyOf(user, 16);


        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        // Instantiate the cipher
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        // Encrypt the username using the hashed password AES key
        byte[] encryptedBytes = new byte[0];
        try {
            encryptedBytes = cipher.doFinal(user);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        String base64format = DatatypeConverter.printBase64Binary(encryptedBytes);
        System.out.println(base64format);

//        Phaser phaser = new Phaser();
//        phaser.bulkRegister(2); // Register this thread and firebase thread

        String finalUsername = username;
        Runnable firebaseAuthentication = new Runnable() {
            @Override
            public void run() {
                // Fetch from firebase and compare the string
                BackgroundFireBase firebaseSingleton = BackgroundFireBase.getInstance();
                String firebaseData = firebaseSingleton.Authenticate(finalUsername);

                if (firebaseData.equals(base64format)){
                    System.out.println("succeeded");
                    authenticated[0] = true;
                }
                else {
                    System.out.println("failed");
                }
            }
        };

        Thread firebaseAuthenticationThread = new Thread(firebaseAuthentication);
        firebaseAuthenticationThread.start();
        try {
            firebaseAuthenticationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (authenticated[0]) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("home.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            System.out.println("moving on");
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } else {
            System.out.println("Not moving on");
            // tells the user to try again
        }

        System.out.println("Finished authentication");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
