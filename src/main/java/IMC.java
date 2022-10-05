import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {
	private TextField pesoText, alturaText;
	private Label imcLabel, estadoLabel;

	private DoubleProperty nPeso = new SimpleDoubleProperty();
	private DoubleProperty nAltura = new SimpleDoubleProperty();
	private DoubleProperty nImc = new SimpleDoubleProperty();
	private StringProperty estado = new SimpleStringProperty();


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		HBox hboxPeso = new HBox();
		pesoText = new TextField();
		hboxPeso.setSpacing(5);
		hboxPeso.getChildren().addAll(new Label("Peso: "), pesoText, new Label("Kg"));

		HBox hboxAltura = new HBox();
		alturaText = new TextField();
		hboxAltura.setSpacing(5);
		hboxAltura.getChildren().addAll(new Label("Altura: "), alturaText, new Label("cm"));

		HBox hboxIMC = new HBox();
		imcLabel = new Label();
		hboxIMC.setSpacing(5);
		hboxIMC.getChildren().addAll(new Label("IMC: "),imcLabel);

		HBox hboxValores = new HBox();
		hboxValores.setSpacing(5);
		estadoLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
		hboxValores.getChildren().addAll(estadoLabel);

		VBox rootPanel = new VBox();
		rootPanel.setSpacing(5);
		rootPanel.setAlignment(Pos.CENTER);
		rootPanel.setFillWidth(false);
		rootPanel.getChildren().addAll(hboxPeso, hboxAltura, hboxIMC, hboxValores);

		Scene scene = new Scene(rootPanel, 320, 200);

		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();

		//bindings
		
		pesoText.textProperty().bindBidirectional(nPeso, new NumberStringConverter());
		alturaText.textProperty().bindBidirectional(nAltura, new NumberStringConverter());
		imcLabel.textProperty().bindBidirectional(nImc, new NumberStringConverter());
		estadoLabel.textProperty().bind(estado);
		
		DoubleBinding alturaM = nAltura.divide(100);
		nImc.bind(nPeso.divide(alturaM.multiply(alturaM)));
		
		nImc.addListener((o, ov ,nv) -> {
			String estado_imc = "";
			double newValue = nv.doubleValue();
			if (newValue < 18.5) {
				estado_imc = "Bajo peso";
			} else if ((newValue >= 18.5) && (newValue < 25)) {
				estado_imc = "Normal";
			} else if ((newValue >= 25) && (newValue < 30)) {
				estado_imc = "SobrePeso";
			} else {
				estado_imc = "Obeso";
			}
			estado.set(estado_imc);
		});
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
