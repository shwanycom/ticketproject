package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RootController implements Initializable{
	public Stage primaryStage;
	
	@FXML private TextField textId;
	@FXML private PasswordField textPassword;
	@FXML private Button btnLogin;
	@FXML private Button btnClose;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.setOnAction(event-> {handlerBtnLoginAction();}); // 로그인버튼 액션 추가
		btnClose.setOnAction(event-> {primaryStage.close();}); // 로그인창 닫기 버튼 액션
		textPassword.setOnKeyPressed(event -> {
	         if (event.getCode().equals(KeyCode.ENTER)) {
	            handlerBtnLoginAction();
	         }
	    });
	}
	public void handlerBtnLoginAction(){
		if(!(textId.getText().equals("a")&&textPassword.getText().equals("1"))) {
			callAlert("login error : Check your ID or Password.");
			textId.clear(); textPassword.clear();
			return;
		}
		try {
			Stage mainStage = new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/main.fxml"));
			Parent root = loader.load();
			
			MainController mainController = loader.getController();
			mainController.mainStage=mainStage; // 화면 체인지 추가
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/addmain.css").toString());
			mainStage.setScene(scene);
			primaryStage.close(); //기존 메인화면 닫기
			mainStage.show();
			
			callAlert("화면전환성공 : 메인화면으로 전환되었습니다.");
		}catch (IOException e) {
			callAlert("화면전환오류 : 화면 전환에 문제가 있습니다.");
			e.printStackTrace();
		}
	}

	
	// 기타 : 알림창 = "오류정보 : 값을 제대로 입력해주세요" (꼭 콜론을 적어줄것)
	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
}
