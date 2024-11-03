module id.dojo.kriptografi {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires static lombok;


    opens id.dojo.kriptografi to javafx.fxml;
    exports id.dojo.kriptografi;
}