package id.dojo.kriptografi;

public class TextModel {
    private String key;
    private String plaintext;
    private String ciphertext;
    private String method;
    private String hasildekrip;
    private String hasilenkrip;
    private int id;

    public TextModel(String key, String plaintext, String ciphertext, String method, String hasildekrip, String hasilenkrip, int id) {
        this.key = key;
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
        this.method = method;
        this.hasildekrip = hasildekrip;
        this.hasilenkrip = hasilenkrip;
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHasildekrip() {
        return hasildekrip;
    }

    public void setHasildekrip(String hasildekrip) {
        this.hasildekrip = hasildekrip;
    }

    public String getHasilenkrip() {
        return hasilenkrip;
    }

    public void setHasilenkrip(String hasilenkrip) {
        this.hasilenkrip = hasilenkrip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
