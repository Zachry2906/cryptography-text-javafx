package id.dojo.kriptografi;

import id.dojo.kriptografi.method.Method;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private List<TextModel> textModels = new ArrayList<>();

    @FXML private TextField ciphertext, hasildekrip, hasilenkrip, key, plaintext;
    @FXML private Label id, textMethod;
    @FXML private Button save, itemBaru, blockecb, caesar, rc4, superenkrip, vigenere;
    @FXML private VBox vitems;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
        updateUI();
    }

    private void initializeButtons() {
        save.setOnAction(event -> {
            try {
                handleSave();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        itemBaru.setOnAction(this::handleNewItem);
        blockecb.setOnAction(event -> handleMethodSelection("Block ECB"));
        caesar.setOnAction(event -> handleMethodSelection("Caesar Cipher"));
        rc4.setOnAction(event -> handleMethodSelection("RC4"));
        superenkrip.setOnAction(event -> handleMethodSelection("Superenkrip"));
        vigenere.setOnAction(event -> handleMethodSelection("Vigenere Cipher"));
    }

    private void handleSave() throws Exception {
        String method = textMethod.getText();
        String kunci = key.getText();
        String plain = plaintext.getText();
        String cipher = ciphertext.getText();

        if (!validateInput(method, kunci, plain, cipher)) {
            return;
        }

        processEncryptionDecryption(method, kunci, plain, cipher);
        saveTextModel(method, kunci, plain, cipher);
        updateUI();
    }

    private boolean validateInput(String method, String kunci, String plain, String cipher) {
        if (kunci.isEmpty() || (plaintext.getText().isEmpty() && ciphertext.getText().isEmpty())) {
            showDialogue("Error", "Data tidak lengkap", "Data tidak boleh kosong");
            return false;
        }

        if ((method.equals("Vigenere Cipher") || method.equals("Block ECB") || method.equals("RC4"))) {
            if (!plain.matches("[a-zA-Z ]+") && !plain.isEmpty()) {
                showDialogue("Error", "Data harus berupa huruf", "Data harus berupa huruf");
                return false;
            }

            if (method.equals("Block ECB") && !(kunci.length() == 16 || kunci.length() == 24 || kunci.length() == 32)) {
                showDialogue("Error", "Kunci harus 16, 24, atau 32 karakter", "Kunci harus 16, 24, atau 32 karakter");
                return false;
            }
        }

        if ((method.equals("Caesar Cipher") || method.equals("Superenkrip"))) {
            if (!kunci.matches("\\d+")){
                showDialogue("Error", "Kunci harus berupa angka", "Kunci harus berupa angka");
                return false;
            }
            if (!plain.matches("[a-zA-Z ]+") && !plain.isEmpty()) {
                showDialogue("Error", "Data harus berupa huruf", "Data harus berupa huruf");
                return false;
            }
        }

        return true;
    }

    private void processEncryptionDecryption(String method, String kunci, String plain, String cipher) throws Exception {
        String hasildekripp, hasilenkripp;

        switch (method) {
            case "Vigenere Cipher":
                hasildekripp = Method.VigenereDecrypt(cipher, kunci);
                hasilenkripp = Method.VigenereEncrypt(plain, kunci);
                break;
            case "Caesar Cipher":
                int key = Integer.parseInt(kunci);
                hasildekripp = Method.CaesarDecrypt(cipher, key);
                hasilenkripp = Method.CaesarEncrypt(plain, key);
                break;
            case "Block ECB":
                hasildekripp = Method.BlockECBDecrypt(cipher, kunci);
                hasilenkripp = Method.BlockECBEncrypt(plain, kunci);
                break;
            case "RC4":
                hasildekripp = Method.RC4Decrypt(cipher, kunci);
                hasilenkripp = Method.RC4Encrypt(plain, kunci);
                break;
            case "Superenkrip":
                int superKey = Integer.parseInt(kunci);
                hasildekripp = Method.superDecrypt(cipher, superKey);
                hasilenkripp = Method.superEncrypt(plain, superKey);
                break;
            default:
                throw new IllegalStateException("Unexpected method: " + method);
        }

        hasildekrip.setText(hasildekripp);
        hasilenkrip.setText(hasilenkripp);
    }

    private void saveTextModel(String method, String kunci, String plain, String cipher) {
        int idValue = id.getText().isEmpty() ? textModels.size() : Integer.parseInt(id.getText());
        TextModel model = new TextModel(kunci, plain, cipher, method, hasildekrip.getText(), hasilenkrip.getText(), idValue);

        if (id.getText().isEmpty()) {
            textModels.add(model);
        } else {
            textModels.set(idValue, model);
        }
    }

    private void showDialogue(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleNewItem(javafx.event.ActionEvent actionEvent) {
        key.clear();
        plaintext.clear();
        ciphertext.clear();
        hasildekrip.clear();
        hasilenkrip.clear();
        id.setText("");
    }

    private void updateUI() {
        vitems.getChildren().clear();
        for (int i = 0; i < textModels.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("component.fxml"));
                Node node = loader.load();
                ItemController controller = loader.getController();
                TextModel app = textModels.get(i);

                String prev = app.getCiphertext().isEmpty() ? app.getPlaintext() : app.getCiphertext();
                controller.setItemInfo(app.getMethod(), prev);

                setupNodeInteractions(node, app);
                vitems.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupNodeInteractions(Node node, TextModel app) {
        node.setOnMouseEntered(event -> node.setStyle("-fx-background-color: #303250"));
        node.setOnMouseExited(event -> node.setStyle("-fx-background-color: #101010FF"));
        node.setOnMousePressed(event -> {
            node.setStyle("-fx-background-color: #101010FF");
            updateFieldsFromModel(app);
        });
    }

    private void updateFieldsFromModel(TextModel app) {
        key.setText(app.getKey());
        plaintext.setText(app.getPlaintext());
        ciphertext.setText(app.getCiphertext());
        textMethod.setText(app.getMethod());
        hasildekrip.setText(app.getHasildekrip());
        hasilenkrip.setText(app.getHasilenkrip());
        id.setText(String.valueOf(app.getId()));
    }

    private void handleMethodSelection(String methodName) {
        resetButtonStyles();
        getButtonByMethodName(methodName).setStyle("-fx-background-color: #101010FF");
        textMethod.setText(methodName);
    }

    private void resetButtonStyles() {
        blockecb.setStyle("-fx-background-color: #303230FF");
        caesar.setStyle("-fx-background-color: #303230FF");
        rc4.setStyle("-fx-background-color: #303230FF");
        superenkrip.setStyle("-fx-background-color: #303230FF");
        vigenere.setStyle("-fx-background-color: #303230FF");
    }

    private Button getButtonByMethodName(String methodName) {
        switch (methodName) {
            case "Block ECB": return blockecb;
            case "Caesar Cipher": return caesar;
            case "RC4": return rc4;
            case "Superenkrip": return superenkrip;
            case "Vigenere Cipher": return vigenere;
            default: throw new IllegalArgumentException("Unknown method: " + methodName);
        }
    }
}