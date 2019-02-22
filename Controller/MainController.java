package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.PerTicket;
import Model.Performance;
import Model.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable{
	public Stage mainStage;
	
	// tab1 멤버변수
	private ArrayList<Performance> ButtonViewPerformance= new ArrayList<>();
	private ArrayList<Performance> ButtonEditPerformance= new ArrayList<>();
	private ObservableList<String> c2GenreObList =FXCollections.observableArrayList(); // 장르콤보박스 ob리스트
	private ObservableList<String> c2LocationObList=FXCollections.observableArrayList(); // 지역콤보박스 ob리스트
	// tab2 멤버변수
	private ObservableList<String> t2GenreObList=FXCollections.observableArrayList(); // 장르콤보박스 ob리스트
	private ObservableList<String> t2LocationObList=FXCollections.observableArrayList(); // 지역콤보박스 ob리스트
	
	private ObservableList<Performance> t2PerformanceObList=FXCollections.observableArrayList();
	private ArrayList<Performance> t2PerformanceDbArrayList= new ArrayList<>();
	

	
	private ObservableList<Ticket> t2RemainTicketObList=FXCollections.observableArrayList();
	private ArrayList<Ticket> t2RemainTicketDbArrayList= new ArrayList<>();
	
	private ObservableList<Ticket> t2SellTicketObList=FXCollections.observableArrayList();
	private ArrayList<Ticket> t2SellTicketDbArrayList= new ArrayList<>();
	
	private ObservableList<PerTicket> t2PerTicketObList=FXCollections.observableArrayList();
	private ArrayList<PerTicket> t2PerTicketDbArrayList= new ArrayList<>();
	
	
	

	
	private ArrayList<Integer> remainTicketDbArrayList= new ArrayList<>();
	private ArrayList<Integer> sellTicketDbArrayList= new ArrayList<>();
	
	private ArrayList<Integer> ticketPriceDbArrayList= new ArrayList<>();
	
	private ArrayList<Integer> quarterlyRevenueDbArrayList=new ArrayList<>();
	
	private ArrayList<Integer> genreRatioDbArrayList=new ArrayList<>();

	private ArrayList<Integer> genreMusicalRevenueDbArrayList=new ArrayList<>();
	private ArrayList<Integer> genreConcertRevenueDbArrayList=new ArrayList<>();
	private ArrayList<Integer> genrePlayRevenueDbArrayList=new ArrayList<>();
	private ArrayList<Integer> genreFestivalRevenueDbArrayList=new ArrayList<>();
	private ArrayList<Integer> genreExhibitionRevenueDbArrayList=new ArrayList<>();
	
	private Ticket ticketRemainToSell=null; 
	private Ticket ticketSellToRemain=null;
	private String t1SelectedPerformanceCode=null;

	
	private File imageDir = new File("C:/images");
	private File t2SelectFile=null;
	private File c2SelectFile=null;
	private String t2FileName="";
	private String c2FileName="";
	private Ticket selectedTicketRemainToSell;
	private int selectedTicketRemainToSellIndex;
	private Ticket selectedTicketSellToRemain;
	private int selectedTicketSellToRemainIndex;
//-------------------------------------------------------------- tab1
	@FXML Label t1LabelTitle;
	@FXML DatePicker t1DatePicker;
	@FXML DatePicker t1DatePickerLast;
	@FXML Button t1ButtonTicket;
	@FXML Button t1ButtonEdit;
	@FXML Button t1ButtonDelete;
	@FXML TableView<PerTicket> t1TableView;
	@FXML Button t1ButtonDateSearch;
	@FXML TextField t1TextFieldSearch;
	@FXML Button t1ButtonResetTable;
	@FXML Button t1ButtonSearch;
	@FXML Button t1ButtonView;
	@FXML ImageView t1ImageView;
	@FXML TextField t1TextFieldGenre;
	@FXML TextField t1TextFieldTitle;
	@FXML TextField t1TextFieldLocation;
	@FXML TextField t1TextFieldDate;
	@FXML TextField t1TextFieldTag;
	@FXML TextField t1TextFieldRunningTime;
	@FXML TextField t1TextFieldCast;
	@FXML TextField t1TextFieldSynopsis;
	@FXML TextField t1TextFieldProduce;
	@FXML Label t1LabelNotice;
	@FXML Label t1LabelR;
	@FXML Label t1LabelA;
	@FXML Label t1LabelB;
	@FXML Label t1LabelRTicket;
	@FXML Label t1LabelATicket;
	@FXML Label t1LabelBTicket;
	@FXML Label t1LabelRPrice;
	@FXML Label t1LabelAPrice;
	@FXML Label t1LabelBPrice;
	@FXML Label t1LabelRTicketCount;
	@FXML Label t1LabelATicketCount;
	@FXML Label t1LabelBTicketCount;
	@FXML Label t1LabelRPriceNumber;
	@FXML Label t1LabelAPriceNumber;
	@FXML Label t1LabelBPriceNumber;
	
//-------------------------------------------------------------- tab1
//-------------------------------------------------------------- tab2
	@FXML Label t2LabelTitle;
	@FXML ImageView t2ImageView;
	@FXML Button t2ButtonImage;
	@FXML TextField t2TextFieldTitle;
	@FXML ComboBox<String> t2CmbGenre;
	@FXML ComboBox<String> t2CmbLocation;
	@FXML DatePicker t2DatePicker;
	@FXML TextField t2TextFieldCast;
	@FXML TextField t2TextFieldRunnigTime;
	@FXML RadioButton t2RdoFamily;
	@FXML RadioButton t2RdoFriends;
	@FXML RadioButton t2RdoCouple;
	@FXML RadioButton t2RdoKids;
	@FXML RadioButton t2RdoRomantic;
	@FXML RadioButton t2RdoComedy;
	@FXML RadioButton t2RdoScience;
	@FXML RadioButton t2RdoAction;
	@FXML RadioButton t2RdoHorror;
	@FXML RadioButton t2RdoDramatic;
	@FXML TextField t2TextFieldNotice;
	@FXML TextField t2TextFieldSynopsis;
	@FXML TextField t2TextFieldProduce;
	@FXML TextField t2TextFieldRTicket;
	@FXML TextField t2TextFieldRPrice;
	@FXML TextField t2TextFieldATicket;
	@FXML TextField t2TextFieldAPrice;
	@FXML TextField t2TextFieldBTicket;
	@FXML TextField t2TextFieldBPrice;
	@FXML Button t2ButtonCreateTicket;
	@FXML TableView<Performance> t2TableViewPerformance;
	@FXML Button t2ButtonRegist;
	@FXML Button t2ButtonClear;
	@FXML Label t2LabelB;
//-------------------------------------------------------------- tab3
	
	@FXML TextField t3TextFieldFirst;
	@FXML TextField t3TextFieldSecond;
	@FXML TextField t3TextFieldThird;
	@FXML TextField t3TextFieldFourth;
	@FXML TextField t3TextFieldTotal;
	@FXML TextField t3TextFieldMusical;
	@FXML TextField t3TextFieldConcert;
	@FXML TextField t3TextFieldPlay;
	@FXML TextField t3TextFieldFestival;
	@FXML TextField t3TextFieldExhibition;
	@FXML LineChart t3LineChartRevenue;
	@FXML BarChart t3BarChartRevenue;
	@FXML BarChart t3BarChartGenre;
	@FXML PieChart t3PieChartGenre;
	@FXML Tab t3Tab;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Connection con =DBUtility.getConnection();
		
		t1TableViewConnectDB(); // t1 tableview db 연동
		t1ButtonTicket.setOnAction((event)->{t2ButtonTicketSet();}); // t1 티켓 버튼 클릭시 새창
		t1ButtonEdit.setOnAction(event-> {t1ButtonEditSet();}); // t1 에딧 버튼 클릭시 새창
		t1ButtonView.setOnAction(event-> {t1ButtonViewSet();}); // t1 뷰 버튼 세팅
		t1ButtonDelete.setOnAction(event->{t1ButtonDeleteSet();}); // t1 딜리트 버튼 세팅
		t1ButtonSearch.setOnAction(event-> {t1ButtonSearchSet();}); // t1 타이틀 서치 버튼 세팅
		t1ButtonDateSearch.setOnAction(event ->{t1ButtonDateSearchSet();}); // t1 데이트 서치 버튼 세팅
		t1ButtonResetTable.setOnAction(event-> {t1ButtonResetTableSet();}); // t1 리셋테이블 버튼 세팅
		
		t2CmbSet(); // t2 콤보박스 세팅
		t2TableViewConnectDB(); // t2TableViewPerformance db연동
		t2ButtonRegist.setOnAction(event-> {t2PerformanceRegistToDB();}); // t2 regist 버튼 Performance DB저장 // 수정필요(이미지, 비고)
		t2ButtonClear.setOnAction(event-> {t2ButtonClearSet();}); // t2 clear 버튼 설정
		t2ButtonImage.setOnAction(event-> {t2ImageViewRegist();}); // t2 image 버튼 액션
		t2ButtonCreateTicket.setOnAction(event-> {t2ButtonCreateTicketSet();}); //t2createticket 버튼 액션 -> 버튼 누르면 새창, 등록 한 공연의 티켓 리스트 생성, 확인 버튼 액션
	
		
		
		t3textFieldSet(); // t3textfield seteditable false
		t3LineChartRevenue.setOnMouseClicked( event->{t3LineChartSet(event);});	// t3 linechart 세팅
		t3BarChartRevenue.setOnMouseClicked(event->{t3BarChartRevenueSet(event);}); // t3 바차트 누적액 세팅
		t3PieChartGenre.setOnMouseClicked(event-> {t3PieCharGenreSet(event);}); // t3 파이차트 세팅
		t3BarChartGenre.setOnMouseClicked(event-> {t3BarChartGenreRevenueSet(event);}); // t3 장르별매출 바차트 세팅
		t3Tab.setOnSelectionChanged(event-> {t3TabRefreshSet();}); // t3tab 전환 될때마다 리셋 세팅
	}





//-------------------------------------------------------------------모듈부-------------------------------------------------------------------//
	
	//********************************************************************************************************************************* t1 모듈부

	// t1 ButtonEdit 세팅
	private void t1ButtonEditSet() {
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.setTitle("Edit");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/edit.fxml"));
			Parent root;
			root = loader.load();
			TextField c2TextFieldTitle = (TextField)root.lookup("#c2TextFieldTitle");
			ComboBox<String> c2CmbGenre=(ComboBox<String>)root.lookup("#c2CmbGenre");
			ComboBox<String> c2CmbLocation=(ComboBox<String>)root.lookup("#c2CmbLocation");
			DatePicker c2DatePicker = (DatePicker)root.lookup("#c2DatePicker");
			TextField c2TextFieldProduce = (TextField)root.lookup("#c2TextFieldProduce");
			TextField c2TextFieldRunningTime = (TextField)root.lookup("#c2TextFieldRunningTime");
			ImageView c2ImageView = (ImageView)root.lookup("#c2ImageView");
			Button c2ButtonImage = (Button)root.lookup("#c2ButtonImage");
			RadioButton c2RdoFamily = (RadioButton)root.lookup("#c2RdoFamily");
			RadioButton c2RdoCouple = (RadioButton)root.lookup("#c2RdoCouple");
			RadioButton c2RdoFriends = (RadioButton)root.lookup("#c2RdoFriends");
			RadioButton c2RdoKids = (RadioButton)root.lookup("#c2RdoKids");
			RadioButton c2RdoRomantic = (RadioButton)root.lookup("#c2RdoRomantic");
			RadioButton c2RdoComedy = (RadioButton)root.lookup("#c2RdoComedy");
			RadioButton c2RdoScience = (RadioButton)root.lookup("#c2RdoScience");
			RadioButton c2RdoAction = (RadioButton)root.lookup("#c2RdoAction");
			RadioButton c2RdoHorror = (RadioButton)root.lookup("#c2RdoHorror");
			RadioButton c2RdoDramatic = (RadioButton)root.lookup("#c2RdoDramatic");
			TextField c2TextFieldNotice = (TextField)root.lookup("#c2TextFieldNotice");
			TextField c2TextFieldCast = (TextField)root.lookup("#c2TextFieldCast");
			TextField c2TextFieldSynopsis = (TextField)root.lookup("#c2TextFieldSynopsis");
			Button c2ButtonEdit = (Button)root.lookup("#c2ButtonEdit");
			Button c2ButtonClear = (Button)root.lookup("#c2ButtonClear");
			
			
			c2GenreObList.clear();c2LocationObList.clear();
			c2GenreObList.addAll("MUSICAL", "CONCERT", "PLAY", "FESTIVAL", "EXHIBITION");
			c2CmbGenre.setItems(t2GenreObList);
			c2LocationObList.addAll("서울", "광주", "대구", "대전", "부산","울산","인천","강원","경기남부","경기동부", "경기북부", "경기서부", "경남", "경북", "전남", "전북", "제주", "충남", "충북");
			c2CmbLocation.setItems(t2LocationObList);
			
			ButtonEditPerformance.clear();
			t1SelectedPerformanceCode=null;
			t1SelectedPerformanceCode=t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode();
			ButtonEditPerformance=PerformanceDAO.getPerformanceSelectedData(t1SelectedPerformanceCode);
			c2TextFieldTitle.setText(ButtonEditPerformance.get(0).getPerformanceTitle());
			c2CmbGenre.setValue(ButtonEditPerformance.get(0).getGenre());
			c2CmbLocation.setValue(ButtonEditPerformance.get(0).getLocation());
			c2DatePicker.setValue(LocalDate.parse(ButtonEditPerformance.get(0).getPerformanceDate()));
			c2TextFieldProduce.setText(ButtonEditPerformance.get(0).getProduce());
			c2TextFieldRunningTime.setText(ButtonEditPerformance.get(0).getRunningTime());
			if(ButtonEditPerformance.get(0).getPoster().equals(null)) {
				c2ImageView.setImage(new Image(getClass().getResource("../images/basicimage.png").toString()));
			}else {
				Image image = new Image("file:///"+imageDir.getAbsolutePath()+"/"+ButtonEditPerformance.get(0).getPoster());
				c2ImageView.setImage(image);
			}
			c2FileName=ButtonEditPerformance.get(0).getPoster();
//			System.out.println(c2FileName);
			String[] c2TagList = ButtonEditPerformance.get(0).getTag().split("#");
			for(String t:c2TagList) {
				if(t.equals("FAMILY".trim())) {
					c2RdoFamily.setSelected(true);
				}
				if(t.equals("COUPLE".trim())) {
					c2RdoCouple.setSelected(true);
				}
				if(t.equals("FRIENDS".trim())) {
					c2RdoFriends.setSelected(true);
				}
				if(t.equals("KIDS".trim())) {
					c2RdoKids.setSelected(true);
				}
				if(t.equals("ROMANTIC".trim())) {
					c2RdoRomantic.setSelected(true);
				}
				if(t.equals("COMEDY".trim())) {
					c2RdoComedy.setSelected(true);
				}
				if(t.equals("SCIENCE".trim())) {
					c2RdoScience.setSelected(true);
				}
				if(t.equals("ACTION".trim())) {
					c2RdoAction.setSelected(true);
				}
				if(t.equals("HORROR".trim())) {
					c2RdoHorror.setSelected(true);
				}
				if(t.equals("DRAMATIC".trim())) {
					c2RdoDramatic.setSelected(true);
				}
			}
			c2TextFieldNotice.setText(ButtonEditPerformance.get(0).getNotice());
			c2TextFieldCast.setText(ButtonEditPerformance.get(0).getCasting());
			c2TextFieldSynopsis.setText(ButtonEditPerformance.get(0).getSynopsis());
			c2ButtonImage.setOnAction(e->{
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
				c2SelectFile = fileChooser.showOpenDialog(mainStage);
				
				String localURL = null;
				if(c2SelectFile != null) {
					try {
						localURL = c2SelectFile.toURI().toURL().toString();
					} catch (MalformedURLException e2) {
					}
				}else {
					
				}
				c2ImageView.setImage(new Image(localURL, false)); // false = 배경으로 가져오는게 아니다.
				c2FileName=c2SelectFile.getName(); // 선택된 파일명을 준다 : fileName은 반드시 이미지 파일을 선택하였을때 값을 유지한다.
				ButtonViewPerformance=PerformanceDAO.getPerformanceSelectedData(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
				imageDelete();
			});
			c2ButtonEdit.setOnAction(e-> {
				c2ImageSave();
				String rdoFamily="";
				String rdoFriends="";
				String rdoCouple="";
				String rdoKids="";
				String rdoAction="";
				String rdoRomantic="";
				String rdoComedy="";
				String rdoScience="";
				String rdoHorror="";
				String rdoDramatic="";
				if(c2RdoFamily.isSelected()) {rdoFamily=c2RdoFamily.getUserData().toString();}
				if(c2RdoFriends.isSelected()) {rdoFriends=c2RdoFriends.getUserData().toString();}
				if(c2RdoCouple.isSelected()) {rdoCouple=c2RdoCouple.getUserData().toString();}
				if(c2RdoKids.isSelected()) {rdoKids=c2RdoKids.getUserData().toString();}
				if(c2RdoAction.isSelected()) {rdoAction=c2RdoAction.getUserData().toString();}
				if(c2RdoRomantic.isSelected()) {rdoRomantic=c2RdoRomantic.getUserData().toString();}
				if(c2RdoComedy.isSelected()) {rdoComedy=c2RdoComedy.getUserData().toString();}
				if(c2RdoScience.isSelected()) {rdoScience=c2RdoScience.getUserData().toString();}
				if(c2RdoHorror.isSelected()) {rdoHorror=c2RdoHorror.getUserData().toString();}
				if(c2RdoDramatic.isSelected()) {rdoDramatic=c2RdoDramatic.getUserData().toString();}
				
				int canUpdatePerformance=0;
				for(Performance per : t2PerformanceDbArrayList) {
					try {
						if((c2TextFieldTitle.getText()+c2DatePicker.getValue().toString()).equals(per.getPerformanceCode())) {
							canUpdatePerformance++;
						}
					}catch(NullPointerException er) {
						callAlert("NULL ERROR : \nTITLE, GENRE, LOCATION, DATE, \nRUNNING TIME, PRODUCE는 필수 입력 사항입니다.");
						return;
					}
				}
				
				if(canUpdatePerformance==0||t1SelectedPerformanceCode.equals(c2TextFieldTitle.getText()+c2DatePicker.getValue().toString())) {
					try {
						if(c2TextFieldProduce.getText().equals("")) {
							callAlert("NULL ERROR : \nTITLE, GENRE, LOCATION, DATE, \nRUNNING TIME, PRODUCE는 필수 입력 사항입니다.");
							return;
						}
						Performance updatePerformance=new Performance(
								c2TextFieldTitle.getText()+c2DatePicker.getValue().toString(), 
								c2TextFieldTitle.getText(), 
								c2CmbGenre.getSelectionModel().getSelectedItem().toString(), 
								c2CmbLocation.getSelectionModel().getSelectedItem().toString(), 
								c2DatePicker.getValue().toString(), 
								rdoFamily+rdoCouple+rdoFriends+rdoKids+rdoRomantic+rdoComedy+rdoScience+rdoAction+rdoHorror+rdoDramatic, 
								c2TextFieldRunningTime.getText(), 
								c2TextFieldCast.getText(), 
								c2TextFieldSynopsis.getText(), 
								c2FileName, 
								c2TextFieldProduce.getText(), 
								c2TextFieldNotice.getText());
						int count = PerformanceDAO.updatePerformanceData(t1SelectedPerformanceCode, updatePerformance);
						if(count!=0) {
							// t2registtableview 리프레시
							t2PerformanceDbArrayList.clear();
							t2PerformanceObList.clear();
							t2PerformanceDbArrayList=PerformanceDAO.getPerformanceTotalData();
							for(Performance per : t2PerformanceDbArrayList) {
								t2PerformanceObList.add(per);
							}
							t2TableViewPerformance.setItems(t2PerformanceObList);	
							
							// t1TableView 리프레시
							t2PerTicketDbArrayList.clear();
							t2PerTicketObList.clear();
							t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
							for(PerTicket pert : t2PerTicketDbArrayList) {
								t2PerTicketObList.add(pert);
							}
							t1TableView.setItems(t2PerTicketObList);
						}
						canUpdatePerformance--;
						callAlert("EDIT SUCCESS : 수정완료");
						editStage.close();
					}catch(NullPointerException er) {
						callAlert("NULL ERROR : \nTITLE, GENRE, LOCATION, DATE, \nRUNNING TIME, PRODUCE는 필수 입력 사항입니다.");
						return;
					}
				}else {
					callAlert("CODE ERROR : 이미 공연이 존재합니다.");
					return;
				}
				c2FileName=null;
				c2SelectFile=null;
			});
			c2ButtonClear.setOnAction(e3-> {
				c2ImageView.setImage(new Image(getClass().getResource("../images/basicimage.png").toString()));
				c2SelectFile=null;
				c2FileName="";
				c2TextFieldTitle.clear();
				c2CmbGenre.getSelectionModel().clearSelection();
				c2CmbLocation.getSelectionModel().clearSelection();
				c2DatePicker.setValue(null);
				c2TextFieldCast.clear();
				c2TextFieldRunningTime.clear();
				c2RdoFamily.setSelected(false);
				c2RdoCouple.setSelected(false);
				c2RdoFriends.setSelected(false);
				c2RdoKids.setSelected(false);
				c2RdoRomantic.setSelected(false);
				c2RdoComedy.setSelected(false);
				c2RdoScience.setSelected(false);
				c2RdoAction.setSelected(false);
				c2RdoHorror.setSelected(false);
				c2RdoDramatic.setSelected(false);
				c2RdoCouple.setSelected(false);
				c2TextFieldSynopsis.clear();
				c2TextFieldNotice.clear();
				c2TextFieldProduce.clear();
			});
			
			Scene scene = new Scene(root);
			editStage.setScene(scene);
			editStage.setResizable(false);
			editStage.show();
		} catch (IOException e) {
		}
	}
	
	// t1 ButtonView 세팅
	private void t1ButtonViewSet() {
		ButtonViewPerformance.clear();
		ButtonViewPerformance=PerformanceDAO.getPerformanceSelectedData(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
		t1TextFieldGenre.setText(ButtonViewPerformance.get(0).getGenre());
		t1TextFieldTitle.setText(ButtonViewPerformance.get(0).getPerformanceTitle());
		t1TextFieldLocation.setText(ButtonViewPerformance.get(0).getLocation());
		t1TextFieldDate.setText(ButtonViewPerformance.get(0).getPerformanceDate());
		t1TextFieldTag.setText(ButtonViewPerformance.get(0).getTag());
		t1TextFieldRunningTime.setText(ButtonViewPerformance.get(0).getRunningTime());
		t1TextFieldCast.setText(ButtonViewPerformance.get(0).getCasting());
		t1TextFieldSynopsis.setText(ButtonViewPerformance.get(0).getSynopsis());
		t1TextFieldProduce.setText(ButtonViewPerformance.get(0).getProduce());
		if(ButtonViewPerformance.get(0).getPoster().equals("")) {
			t1ImageView.setImage(new Image(getClass().getResource("../images/basicimage.png").toString()));
		}else {
			Image image2 = new Image("file:///"+imageDir.getAbsolutePath()+"/"+ButtonViewPerformance.get(0).getPoster());
//			System.out.println(ButtonViewPerformance.get(0).getPoster());
//			System.out.println(image2);
			t1ImageView.setImage(image2);
		}
		t1LabelNotice.setText(ButtonViewPerformance.get(0).getNotice());
		
		remainTicketDbArrayList.clear();
		remainTicketDbArrayList=TicketDAO.remainTicketCount(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
		remainTicketDbArrayList.get(0);
		t1LabelRTicketCount.setText(String.valueOf(remainTicketDbArrayList.get(0)));
		t1LabelATicketCount.setText(String.valueOf(remainTicketDbArrayList.get(1)));
		t1LabelBTicketCount.setText(String.valueOf(remainTicketDbArrayList.get(2)));
	
		sellTicketDbArrayList.clear();
		sellTicketDbArrayList=TicketDAO.sellTicketCount(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
		
		ticketPriceDbArrayList.clear();
		ticketPriceDbArrayList=TicketDAO.getSelectedTicketPriceData(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
		t1LabelRPriceNumber.setText(String.valueOf(ticketPriceDbArrayList.get(0)));
		t1LabelAPriceNumber.setText(String.valueOf(ticketPriceDbArrayList.get(1)));
		t1LabelBPriceNumber.setText(String.valueOf(ticketPriceDbArrayList.get(2)));
	}
	
	// t1 tableview db 연동
	private void t1TableViewConnectDB(){
		TableColumn tcCode=t1TableView.getColumns().get(0);
		tcCode.setCellValueFactory(new PropertyValueFactory<>("performanceCode"));
		tcCode.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcGenre=t1TableView.getColumns().get(1);
		tcGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		tcGenre.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcTitle=t1TableView.getColumns().get(2);
		tcTitle.setCellValueFactory(new PropertyValueFactory<>("performanceTitle"));
		tcTitle.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcLocation=t1TableView.getColumns().get(3);
		tcLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
		tcLocation.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcDate=t1TableView.getColumns().get(4);
		tcDate.setCellValueFactory(new PropertyValueFactory<>("performanceDate"));
		tcDate.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcTag=t1TableView.getColumns().get(5);
		tcTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
		tcTag.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcRunningTime=t1TableView.getColumns().get(6);
		tcRunningTime.setCellValueFactory(new PropertyValueFactory<>("runningTime"));
		tcRunningTime.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcRSeat=t1TableView.getColumns().get(7);
		tcRSeat.setCellValueFactory(new PropertyValueFactory<>("seatR"));
		tcRSeat.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcASeat=t1TableView.getColumns().get(8);
		tcASeat.setCellValueFactory(new PropertyValueFactory<>("seatA"));
		tcASeat.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcBSeat=t1TableView.getColumns().get(9);
		tcBSeat.setCellValueFactory(new PropertyValueFactory<>("seatB"));
		tcBSeat.setStyle("-fx-alignment:CENTER;");
		
		// 테이블뷰에 db리스트 세팅
		t2PerTicketDbArrayList.clear();
		t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
		for(PerTicket pert : t2PerTicketDbArrayList) {
			t2PerTicketObList.add(pert);
		}
		t1TableView.setItems(t2PerTicketObList);	
	}
	
	// t1 button ticket 세팅
	private void t2ButtonTicketSet() {
		try {
			Stage ticketStage = new Stage(StageStyle.UTILITY);
			ticketStage.initModality(Modality.WINDOW_MODAL);
			ticketStage.initOwner(mainStage);
			ticketStage.setTitle("Ticket");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ticket.fxml"));
			
			Parent root=null;
			root = loader.load();
			Label c1LabelTitle=(Label)root.lookup("#c1LabelTitle");
			TableView<Ticket> c1TableViewTicket = (TableView)root.lookup("#c1TableViewTicket");
			TableView<Ticket> c1TableViewSellTicket = (TableView)root.lookup("#c1TableViewSellTicket");
			TextField c1TextFieldName = (TextField)root.lookup("#c1TextFieldName");
			TextField c1TextFieldPhone = (TextField)root.lookup("#c1TextFieldPhone");
			DatePicker c1DatePicker = (DatePicker)root.lookup("#c1DatePicker");
			TextField c1TextFieldRRemain = (TextField)root.lookup("#c1TextFieldRRemain");
			TextField c1TextFieldARemain = (TextField)root.lookup("#c1TextFieldARemain");
			TextField c1TextFieldBRemain = (TextField)root.lookup("#c1TextFieldBRemain");
			TextField c1TextFieldRSell = (TextField)root.lookup("#c1TextFieldRSell");
			TextField c1TextFieldASell = (TextField)root.lookup("#c1TextFieldASell");
			TextField c1TextFieldBSell = (TextField)root.lookup("#c1TextFieldBSell");
			Button c1ButtonSell = (Button)root.lookup("#c1ButtonSell");
			Button c1ButtonRefund = (Button)root.lookup("#c1ButtonRefund");
			Button c1ButtonDelete = (Button)root.lookup("#c1ButtonDelete");
			TextField c1TextFieldSearch = (TextField)root.lookup("#c1TextFieldSearch");
			Button c1ButtonSearch = (Button)root.lookup("#c1ButtonSearch");
			
			
			
			
			// c1 remaintableview 연동
			TableColumn tcTitle=c1TableViewTicket.getColumns().get(0);
			tcTitle.setCellValueFactory(new PropertyValueFactory<>("performanceTitle"));
			tcTitle.setStyle("-fx-alignment:CENTER;");
			
			TableColumn tcTicketName=c1TableViewTicket.getColumns().get(1);
			tcTicketName.setCellValueFactory(new PropertyValueFactory<>("ticketName"));
			tcTicketName.setStyle("-fx-alignment:CENTER;");
			
			TableColumn tcPrice=c1TableViewTicket.getColumns().get(2);
			tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
			tcPrice.setStyle("-fx-alignment:CENTER;");
			t2RemainTicketObList.clear();
			t1SelectedPerformanceCode=t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode();
			t2RemainTicketDbArrayList=TicketDAO.getSelectRemainTicketTotalData(t1SelectedPerformanceCode);
			for(Ticket t : t2RemainTicketDbArrayList) {
				t2RemainTicketObList.add(t);
			}
			c1TableViewTicket.setItems(t2RemainTicketObList);
			
			remainTicketDbArrayList.clear();
			remainTicketDbArrayList=TicketDAO.remainTicketCount(t1SelectedPerformanceCode);
			remainTicketDbArrayList.get(0);
			c1TextFieldRRemain.setText(String.valueOf(remainTicketDbArrayList.get(0)));
			c1TextFieldARemain.setText(String.valueOf(remainTicketDbArrayList.get(1)));
			c1TextFieldBRemain.setText(String.valueOf(remainTicketDbArrayList.get(2)));
			
			sellTicketDbArrayList.clear();
			sellTicketDbArrayList=TicketDAO.sellTicketCount(t1SelectedPerformanceCode);
			c1TextFieldRSell.setText(String.valueOf(sellTicketDbArrayList.get(0)));
			c1TextFieldASell.setText(String.valueOf(sellTicketDbArrayList.get(1)));
			c1TextFieldBSell.setText(String.valueOf(sellTicketDbArrayList.get(2)));
			
			
			// c1 selltableview 연동
			TableColumn tcSellTicketName=c1TableViewSellTicket.getColumns().get(0);
			tcSellTicketName.setCellValueFactory(new PropertyValueFactory<>("ticketName"));
			tcSellTicketName.setStyle("-fx-alignment:CENTER;");
			
			TableColumn tcCustomer=c1TableViewSellTicket.getColumns().get(1);
			tcCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
			tcCustomer.setStyle("-fx-alignment:CENTER;");
			
			TableColumn tcCustomerPhone=c1TableViewSellTicket.getColumns().get(2);
			tcCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
			tcCustomerPhone.setStyle("-fx-alignment:CENTER;");
			
			TableColumn tcSellDate=c1TableViewSellTicket.getColumns().get(3);
			tcSellDate.setCellValueFactory(new PropertyValueFactory<>("sellDate"));
			tcSellDate.setStyle("-fx-alignment:CENTER;");
			t2SellTicketObList.clear();
			t2SellTicketDbArrayList=TicketDAO.getSelectSellTicketTotalData(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
			for(Ticket t : t2SellTicketDbArrayList) {
				t2SellTicketObList.add(t);
			}
			c1TableViewSellTicket.setItems(t2SellTicketObList);
			
			c1ButtonSell.setOnAction(e-> {
				selectedTicketRemainToSell = c1TableViewTicket.getSelectionModel().getSelectedItem();
				selectedTicketRemainToSellIndex=c1TableViewTicket.getSelectionModel().getSelectedIndex();
				try{
					String customer=c1TextFieldName.getText();
//					System.out.println(c1TextFieldPhone.getText());
					String customerPhone = c1TextFieldPhone.getText();
					if(customer.equals("")||customerPhone.equals("")) {
						callAlert("NULL ERROR : CUSTOMER NAME, PHONE, DATE 는 필수 입력 사항입니다.");
						return;
					}
					String sellDate = c1DatePicker.getValue().toString();
					ticketRemainToSell = new Ticket(
							selectedTicketRemainToSell.getPerformanceCode(), 
							selectedTicketRemainToSell.getPerformanceTitle(), 
							selectedTicketRemainToSell.getPerformanceDate(), 
							selectedTicketRemainToSell.getGrade(), 
							selectedTicketRemainToSell.getTicketName(), 
							selectedTicketRemainToSell.getPrice(),
							customer, 
							customerPhone, 
							sellDate
							);					
				}catch(NullPointerException er) {
					callAlert("NULL ERROR : CUSTOMER NAME, PHONE, DATE 는 필수 입력 사항입니다.");
					return;
				}
				
					
				int count = TicketDAO.ticketRemainToSell(ticketRemainToSell);
				if(count!=0) {
					t2RemainTicketObList.remove(selectedTicketRemainToSellIndex);
					t2SellTicketObList.add(ticketRemainToSell);
					t2RemainTicketDbArrayList.remove(selectedTicketRemainToSell);
					t2SellTicketDbArrayList.add(ticketRemainToSell);
					callAlert("판매 완료 : "+selectedTicketRemainToSell.getTicketName()+"가 수정되었습니다.");
					c1TableViewTicket.getSelectionModel().clearSelection();
					c1TableViewSellTicket.getSelectionModel().clearSelection();
					remainTicketDbArrayList.clear();
					remainTicketDbArrayList=TicketDAO.remainTicketCount(t1SelectedPerformanceCode);
					remainTicketDbArrayList.get(0);
					c1TextFieldRRemain.setText(String.valueOf(remainTicketDbArrayList.get(0)));
					c1TextFieldARemain.setText(String.valueOf(remainTicketDbArrayList.get(1)));
					c1TextFieldBRemain.setText(String.valueOf(remainTicketDbArrayList.get(2)));
					
					sellTicketDbArrayList.clear();
					sellTicketDbArrayList=TicketDAO.sellTicketCount(t1SelectedPerformanceCode);
					c1TextFieldRSell.setText(String.valueOf(sellTicketDbArrayList.get(0)));
					c1TextFieldASell.setText(String.valueOf(sellTicketDbArrayList.get(1)));
					c1TextFieldBSell.setText(String.valueOf(sellTicketDbArrayList.get(2)));
	
					// t1TableView 리프레시
					t2PerTicketDbArrayList.clear();
					t2PerTicketObList.clear();
					t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
					for(PerTicket pert : t2PerTicketDbArrayList) {
						t2PerTicketObList.add(pert);
					}
					t1TableView.setItems(t2PerTicketObList);	
					
					for(PerTicket pt:t2PerTicketObList) {
						if(t1SelectedPerformanceCode.trim().equals(pt.getPerformanceCode().trim())) {
							t1TableView.getSelectionModel().select(pt);
						}
					}// end of for each
					
				}else {
					return;
				}
			});
			c1ButtonRefund.setOnAction(e2-> {
				try {
					selectedTicketSellToRemain = c1TableViewSellTicket.getSelectionModel().getSelectedItem();
					selectedTicketSellToRemainIndex=c1TableViewSellTicket.getSelectionModel().getSelectedIndex();
						ticketSellToRemain = new Ticket(
							selectedTicketSellToRemain.getPerformanceCode(), 
							selectedTicketSellToRemain.getPerformanceTitle(), 
							selectedTicketSellToRemain.getPerformanceDate(), 
							selectedTicketSellToRemain.getGrade(), 
							selectedTicketSellToRemain.getTicketName(), 
							selectedTicketSellToRemain.getPrice(), 
							null, 
							null, 
							null
							);					
					int count = TicketDAO.ticketSellToRemain(ticketSellToRemain);
					if(count!=0) {
						t2SellTicketObList.remove(selectedTicketSellToRemainIndex);
						t2RemainTicketObList.add(ticketSellToRemain);
						t2SellTicketDbArrayList.remove(selectedTicketSellToRemainIndex);
						t2RemainTicketDbArrayList.add(ticketSellToRemain);
						callAlert("환불 완료 : "+selectedTicketSellToRemain.getTicketName()+"가 수정되었습니다.");
						c1TableViewTicket.getSelectionModel().clearSelection();
						c1TableViewSellTicket.getSelectionModel().clearSelection();
						
						remainTicketDbArrayList.clear();
						
						remainTicketDbArrayList=TicketDAO.remainTicketCount(t1SelectedPerformanceCode);
						remainTicketDbArrayList.get(0);
						c1TextFieldRRemain.setText(String.valueOf(remainTicketDbArrayList.get(0)));
						c1TextFieldARemain.setText(String.valueOf(remainTicketDbArrayList.get(1)));
						c1TextFieldBRemain.setText(String.valueOf(remainTicketDbArrayList.get(2)));
						
						sellTicketDbArrayList.clear();
						sellTicketDbArrayList=TicketDAO.sellTicketCount(t1SelectedPerformanceCode);
						c1TextFieldRSell.setText(String.valueOf(sellTicketDbArrayList.get(0)));
						c1TextFieldASell.setText(String.valueOf(sellTicketDbArrayList.get(1)));
						c1TextFieldBSell.setText(String.valueOf(sellTicketDbArrayList.get(2)));
					}else {
						return;
					}
					
					// t1TableView 리프레시
					t2PerTicketDbArrayList.clear();
					t2PerTicketObList.clear();
					t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
					for(PerTicket pert : t2PerTicketDbArrayList) {
						t2PerTicketObList.add(pert);
					}
					t1TableView.setItems(t2PerTicketObList);	
					for(PerTicket pt:t2PerTicketObList) {
						if(t1SelectedPerformanceCode.trim().equals(pt.getPerformanceCode().trim())) {
							t1TableView.getSelectionModel().select(pt);
						}
					}// end of for each
				}catch(NullPointerException e) {
					callAlert("NULL ERROR : 환불 티켓을 지정해주세요");
					return;
				}
				
			});
			c1ButtonDelete.setOnAction(e3 ->{
				Ticket selectedTicketRemainToDel = c1TableViewTicket.getSelectionModel().getSelectedItem();
				int selectedTicketRemainToDelIndex = c1TableViewTicket.getSelectionModel().getSelectedIndex();
				int count = TicketDAO.deleteTicketData(selectedTicketRemainToDel.getTicketName());
				if(count!=0) {
					callAlert("삭제 완료 : "+selectedTicketRemainToDel.getTicketName()+"가 삭제되었습니다.");
					c1TableViewTicket.getSelectionModel().clearSelection();
					c1TableViewSellTicket.getSelectionModel().clearSelection();
					t2RemainTicketObList.remove(selectedTicketRemainToDelIndex);
					t2RemainTicketDbArrayList.remove(selectedTicketRemainToDel);
					
					remainTicketDbArrayList.clear();
					remainTicketDbArrayList=TicketDAO.remainTicketCount(t1SelectedPerformanceCode);
					remainTicketDbArrayList.get(0);
					c1TextFieldRRemain.setText(String.valueOf(remainTicketDbArrayList.get(0)));
					c1TextFieldARemain.setText(String.valueOf(remainTicketDbArrayList.get(1)));
					c1TextFieldBRemain.setText(String.valueOf(remainTicketDbArrayList.get(2)));
				}else {
					return;
				}
				// t1TableView 리프레시
				t2PerTicketDbArrayList.clear();
				t2PerTicketObList.clear();
				t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
				for(PerTicket pert : t2PerTicketDbArrayList) {
					t2PerTicketObList.add(pert);
				}
				t1TableView.setItems(t2PerTicketObList);	
				
			});
			c1ButtonSearch.setOnAction(event->{
				for(Ticket t:t2SellTicketObList) {
					if(c1TextFieldSearch.getText().trim().equals(t.getCustomerPhone())) {
						c1TableViewSellTicket.getSelectionModel().select(t);
					}
				}// end of for each
			});
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/addticketedit.css").toString());
			ticketStage.setScene(scene);
			ticketStage.setResizable(false);
			ticketStage.show();
		} catch (IOException e) {
		}
	}
	
	// t1 button delete 세팅
	private void t1ButtonDeleteSet() {
		ButtonViewPerformance.clear();
		ButtonViewPerformance=PerformanceDAO.getPerformanceSelectedData(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
		imageDelete();
		int count2 = TicketDAO.deleteTicketDataFromT1TableView(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
		int count = PerformanceDAO.deletePerformanceData(t1TableView.getSelectionModel().getSelectedItem().getPerformanceCode());
		if(count!=0) {
			// t2registtableview 리프레시
			t2PerformanceDbArrayList.clear();
			t2PerformanceObList.clear();
			t2PerformanceDbArrayList=PerformanceDAO.getPerformanceTotalData();
			for(Performance per : t2PerformanceDbArrayList) {
				t2PerformanceObList.add(per);
			}
			t2TableViewPerformance.setItems(t2PerformanceObList);	
			
			// t1TableView 리프레시
			t2PerTicketDbArrayList.clear();
			t2PerTicketObList.clear();
			t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
			for(PerTicket pert : t2PerTicketDbArrayList) {
				t2PerTicketObList.add(pert);
			}
			t1TableView.setItems(t2PerTicketObList);
		}else {
			return;
		}
	}
	
	// t1 button search 세팅
	private void t1ButtonSearchSet() {
		int searchCount=0;
		t2PerTicketDbArrayList.clear();
		t2PerTicketObList.clear();
		t2PerTicketDbArrayList=PerformanceDAO.getPerTicketSelectedTitleData(t1TextFieldSearch.getText().trim());
		for(PerTicket pert : t2PerTicketDbArrayList) {
			t2PerTicketObList.add(pert);
		}
		t1TableView.setItems(t2PerTicketObList);	
	}
	
	// t1 button date search 세팅
	private void t1ButtonDateSearchSet() {
		try {
			t2PerTicketDbArrayList.clear();
			t2PerTicketObList.clear();
			t2PerTicketDbArrayList=PerformanceDAO.getPerTicketSelectedDateData(t1DatePicker.getValue().toString(), t1DatePickerLast.getValue().toString());
			for(PerTicket pert : t2PerTicketDbArrayList) {
				t2PerTicketObList.add(pert);
			}
			t1TableView.setItems(t2PerTicketObList);	
		}catch(NullPointerException e) {
			callAlert("NULL ERROR : 날짜 범위를 지정해주세요");
		}
	}
	
	// t1 button reset Table 세팅
	private void t1ButtonResetTableSet() {
		t2PerTicketDbArrayList.clear();
		t2PerTicketObList.clear();
		t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
		for(PerTicket pert : t2PerTicketDbArrayList) {
			t2PerTicketObList.add(pert);
		}
		t1TableView.setItems(t2PerTicketObList);	
	}

	
	//********************************************************************************************************************************* t2 모듈부
	// t2 콤보박스 세팅, 티켓 텍스트필드 세팅
	private void t2CmbSet() {
		t2GenreObList.addAll("MUSICAL", "CONCERT", "PLAY", "FESTIVAL", "EXHIBITION");
		t2CmbGenre.setItems(t2GenreObList);
		t2LocationObList.addAll("서울", "광주", "대구", "대전", "부산","울산","인천","강원","경기남부","경기동부", "경기북부", "경기서부", "경남", "경북", "전남", "전북", "제주", "충남", "충북");
		t2CmbLocation.setItems(t2LocationObList);
		
		t2TextFieldRTicket.setText("0");
		t2TextFieldATicket.setText("0");
		t2TextFieldBTicket.setText("0");
		t2TextFieldRPrice.setText("0");
		t2TextFieldAPrice.setText("0");
		t2TextFieldBPrice.setText("0");
		inputDecimalFormat(t2TextFieldRTicket);
		inputDecimalFormat(t2TextFieldATicket);
		inputDecimalFormat(t2TextFieldBTicket);
		inputDecimalFormatWonDigit(t2TextFieldRPrice);
		inputDecimalFormatWonDigit(t2TextFieldAPrice);
		inputDecimalFormatWonDigit(t2TextFieldBPrice);
	}
	
	// t2 TableViewPerformance db연동
	private void t2TableViewConnectDB() {
		TableColumn tcCode=t2TableViewPerformance.getColumns().get(0);
		tcCode.setCellValueFactory(new PropertyValueFactory<>("performanceCode"));
		tcCode.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcTitle=t2TableViewPerformance.getColumns().get(1);
		tcTitle.setCellValueFactory(new PropertyValueFactory<>("performanceTitle"));
		tcTitle.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcGenre=t2TableViewPerformance.getColumns().get(2);
		tcGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		tcGenre.setStyle("-fx-alignment:CENTER;");

		TableColumn tcLocation=t2TableViewPerformance.getColumns().get(3);
		tcLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
		tcLocation.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcDate=t2TableViewPerformance.getColumns().get(4);
		tcDate.setCellValueFactory(new PropertyValueFactory<>("performanceDate"));
		tcDate.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcRunningTime=t2TableViewPerformance.getColumns().get(5);
		tcRunningTime.setCellValueFactory(new PropertyValueFactory<>("runningTime"));
		tcRunningTime.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcTag=t2TableViewPerformance.getColumns().get(6);
		tcTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
		tcTag.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcCasting=t2TableViewPerformance.getColumns().get(7);
		tcCasting.setCellValueFactory(new PropertyValueFactory<>("casting"));
		tcCasting.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcSynopsis=t2TableViewPerformance.getColumns().get(8);
		tcSynopsis.setCellValueFactory(new PropertyValueFactory<>("synopsis"));
		tcSynopsis.setStyle("-fx-alignment:CENTER;");

		TableColumn tcProduce=t2TableViewPerformance.getColumns().get(9);
		tcProduce.setCellValueFactory(new PropertyValueFactory<>("produce"));
		tcProduce.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcPoster=t2TableViewPerformance.getColumns().get(10);
		tcPoster.setCellValueFactory(new PropertyValueFactory<>("poster"));
		tcPoster.setStyle("-fx-alignment:CENTER;");
		
		TableColumn tcNotice=t2TableViewPerformance.getColumns().get(11);
		tcNotice.setCellValueFactory(new PropertyValueFactory<>("notice"));
		tcNotice.setStyle("-fx-alignment:CENTER;");
	
		// 테이블뷰에 db리스트 세팅
		t2PerformanceDbArrayList=PerformanceDAO.getPerformanceTotalData();
		for(Performance per : t2PerformanceDbArrayList) {
			t2PerformanceObList.add(per);
		}
		t2TableViewPerformance.setItems(t2PerformanceObList);	
	}
	
	// t2 regist 버튼 DB연동
	private void t2PerformanceRegistToDB() {
		t2ImageSave();
		String rdoFamily="";
		String rdoFriends="";
		String rdoCouple="";
		String rdoKids="";
		String rdoAction="";
		String rdoRomantic="";
		String rdoComedy="";
		String rdoScience="";
		String rdoHorror="";
		String rdoDramatic="";
		if(t2RdoFamily.isSelected()) {rdoFamily=t2RdoFamily.getUserData().toString();}
		if(t2RdoFriends.isSelected()) {rdoFriends=t2RdoFriends.getUserData().toString();}
		if(t2RdoCouple.isSelected()) {rdoCouple=t2RdoCouple.getUserData().toString();}
		if(t2RdoKids.isSelected()) {rdoKids=t2RdoKids.getUserData().toString();}
		if(t2RdoAction.isSelected()) {rdoAction=t2RdoAction.getUserData().toString();}
		if(t2RdoRomantic.isSelected()) {rdoRomantic=t2RdoRomantic.getUserData().toString();}
		if(t2RdoComedy.isSelected()) {rdoComedy=t2RdoComedy.getUserData().toString();}
		if(t2RdoScience.isSelected()) {rdoScience=t2RdoScience.getUserData().toString();}
		if(t2RdoHorror.isSelected()) {rdoHorror=t2RdoHorror.getUserData().toString();}
		if(t2RdoDramatic.isSelected()) {rdoDramatic=t2RdoDramatic.getUserData().toString();}
		int canCreatePerformance=0;
		for(Performance per : t2PerformanceDbArrayList) {
			try {
				if((t2TextFieldTitle.getText()+t2DatePicker.getValue().toString()).equals(per.getPerformanceCode())) {
					canCreatePerformance++;
				}
			}catch(NullPointerException e) {
				callAlert("NULL ERROR : TITLE, DATE는 필수 입력 사항입니다.");
				return;
			}
		}
		try {
			if(canCreatePerformance==0) {
				if(t2TextFieldProduce.getText().equals("")) {
					callAlert("NULL ERROR : \nTITLE, GENRE, LOCATION, DATE, \nRUNNING TIME, PRODUCE는 필수 입력 사항입니다.");
					return;
				}
				Performance performance = new Performance(
						t2TextFieldTitle.getText()+t2DatePicker.getValue().toString(),
						t2TextFieldTitle.getText(),
						t2CmbGenre.getSelectionModel().getSelectedItem().toString(),
						t2CmbLocation.getSelectionModel().getSelectedItem().toString(),
						t2DatePicker.getValue().toString(),
						rdoFamily+rdoCouple+rdoFriends+rdoKids+rdoRomantic+rdoComedy+rdoScience+rdoAction+rdoHorror+rdoDramatic,
						t2TextFieldRunnigTime.getText(), 
						t2TextFieldCast.getText(), 
						t2TextFieldSynopsis.getText(), 
						t2FileName, 
						t2TextFieldProduce.getText(), 
						t2TextFieldNotice.getText());
				t2PerformanceObList.add(performance);
				int count=PerformanceDAO.insertPerformanceData(performance); // 데이터베이스에 삽입
				if(count!=0) {
					callAlert("입력성공 : 공연이 등록되었습니다.");
				}
				t2PerformanceDbArrayList.clear();
				t2PerformanceObList.clear();
				t2PerformanceDbArrayList=PerformanceDAO.getPerformanceTotalData();
				for(Performance per : t2PerformanceDbArrayList) {
					t2PerformanceObList.add(per);
				}
				t2TableViewPerformance.setItems(t2PerformanceObList);	
				// t1TableView 리프레시
				// t1TableView 리프레시
				t2PerTicketDbArrayList.clear();
				t2PerTicketObList.clear();
				t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
				for(PerTicket pert : t2PerTicketDbArrayList) {
					t2PerTicketObList.add(pert);
				}
				t1TableView.setItems(t2PerTicketObList);	
			}else {
				callAlert("CODE ERROR : 이미 공연이 존재합니다.");
				return;
			}
		}catch(NullPointerException e) {
			callAlert("NULL ERROR : \nTITLE, GENRE, LOCATION, DATE, \nRUNNING TIME, PRODUCE는 필수 입력 사항입니다.");
			return;
		}
		
	}
	
	// t2 imageView 버튼 세팅
	private void t2ImageViewRegist() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		t2SelectFile = fileChooser.showOpenDialog(mainStage);
		String localURL = null;
		if(t2SelectFile != null) {
			try {
				localURL = t2SelectFile.toURI().toURL().toString();
//				System.out.println(localURL);
			} catch (MalformedURLException e) {
			}
		}
		t2ImageView.setImage(new Image(localURL, false)); // false = 배경으로 가져오는게 아니다.
		t2FileName=t2SelectFile.getName(); // 선택된 파일명을 준다 : fileName은 반드시 이미지 파일을 선택하였을때 값을 유지한다.
	}
	
	// t2 clear 버튼 세팅
	private void t2ButtonClearSet() {
		t2ImageView.setImage(new Image(getClass().getResource("../images/basicimage.png").toString()));
		t2SelectFile=null;
		t2FileName="";
		t2TextFieldTitle.clear();
		t2CmbGenre.getSelectionModel().clearSelection();
		t2CmbLocation.getSelectionModel().clearSelection();
		t2DatePicker.setValue(null);
		t2TextFieldCast.clear();
		t2TextFieldRunnigTime.clear();
		t2RdoFamily.setSelected(false);
		t2RdoCouple.setSelected(false);
		t2RdoFriends.setSelected(false);
		t2RdoKids.setSelected(false);
		t2RdoRomantic.setSelected(false);
		t2RdoComedy.setSelected(false);
		t2RdoScience.setSelected(false);
		t2RdoAction.setSelected(false);
		t2RdoHorror.setSelected(false);
		t2RdoDramatic.setSelected(false);
		t2RdoCouple.setSelected(false);
		t2TextFieldSynopsis.clear();
		t2TextFieldNotice.clear();
		
		t2TextFieldProduce.clear();
		t2TextFieldRTicket.setText("0");
		t2TextFieldRPrice.setText("0");
		t2TextFieldATicket.setText("0");
		t2TextFieldAPrice.setText("0");
		t2TextFieldBTicket.setText("0");
		t2TextFieldBPrice.setText("0");
	}
	
	// t2 createticket 버튼 세팅
	private void t2ButtonCreateTicketSet() {
		try {
			Stage createTicketStage = new Stage(StageStyle.UTILITY);
			createTicketStage.initModality(Modality.WINDOW_MODAL);
			createTicketStage.initOwner(mainStage);
			createTicketStage.setTitle("NewTicket");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/createticket.fxml"));
			Parent root=null;
			root = loader.load();
			ObservableList<Ticket> c3NewTicketObList=FXCollections.observableArrayList();
			ArrayList<Ticket> c3TicketDbArrayList= new ArrayList<>();
			Label c1LabelTitle=(Label)root.lookup("#c3LabelTitle");
			TableView<Ticket> c3TableView=(TableView)root.lookup("#c3TableView");
			Button c3ButtonConfirm=(Button)root.lookup("#c3ButtonConfirm");
			
			int rCount=Integer.parseInt(t2TextFieldRTicket.getText());
			int aCount=Integer.parseInt(t2TextFieldATicket.getText());
			int bCount=Integer.parseInt(t2TextFieldBTicket.getText());
			int canCreateTicket=0;
			t2PerformanceDbArrayList=PerformanceDAO.getPerformanceTotalData();
			
			for(Performance per : t2PerformanceDbArrayList) {
				try {
					if((t2TextFieldTitle.getText()+t2DatePicker.getValue().toString()).equals(per.getPerformanceCode())) {
						canCreateTicket++;
						break;
					}
				}catch(NullPointerException e) {
					callAlert("NULL ERROR : TITLE, DATE는 필수 입력 사항입니다.");
					return;
				}
			}
			if(canCreateTicket==1) {
				for(int i=0;i<rCount;i++) {
					Ticket rTicket = new Ticket(t2TextFieldTitle.getText()+t2DatePicker.getValue().toString(), t2TextFieldTitle.getText(), t2DatePicker.getValue().toString(), "R", t2TextFieldTitle.getText()+t2DatePicker.getValue().toString()+"/R/"+i, Integer.parseInt(t2TextFieldRPrice.getText()), null, null, null);
					c3NewTicketObList.add(rTicket);
					int count=TicketDAO.insertTicketData(rTicket);
				}	
			
				for(int i=0;i<aCount;i++) {
					Ticket aTicket = new Ticket(t2TextFieldTitle.getText()+t2DatePicker.getValue().toString(), t2TextFieldTitle.getText(), t2DatePicker.getValue().toString(), "A", t2TextFieldTitle.getText()+t2DatePicker.getValue().toString()+"/A/"+i, Integer.parseInt(t2TextFieldAPrice.getText()), null, null, null);
					c3NewTicketObList.add(aTicket);
					int count=TicketDAO.insertTicketData(aTicket);
				}
				for(int i=0;i<bCount;i++) {
					Ticket bTicket = new Ticket(t2TextFieldTitle.getText()+t2DatePicker.getValue().toString(), t2TextFieldTitle.getText(), t2DatePicker.getValue().toString(), "B", t2TextFieldTitle.getText()+t2DatePicker.getValue().toString()+"/B/"+i, Integer.parseInt(t2TextFieldBPrice.getText()), null, null, null);
					c3NewTicketObList.add(bTicket);
					int count=TicketDAO.insertTicketData(bTicket); 
				}
				TableColumn tcCode=c3TableView.getColumns().get(0);
				tcCode.setCellValueFactory(new PropertyValueFactory<>("performanceCode"));
				tcCode.setStyle("-fx-alignment:CENTER;");
				
				TableColumn tcTitle=c3TableView.getColumns().get(1);
				tcTitle.setCellValueFactory(new PropertyValueFactory<>("performanceTitle"));
				tcTitle.setStyle("-fx-alignment:CENTER;");
				
				TableColumn tcDate=c3TableView.getColumns().get(2);
				tcDate.setCellValueFactory(new PropertyValueFactory<>("performanceDate"));
				tcDate.setStyle("-fx-alignment:CENTER;");

				TableColumn tcGrade=c3TableView.getColumns().get(3);
				tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
				tcGrade.setStyle("-fx-alignment:CENTER;");
				
				TableColumn tcTicketName=c3TableView.getColumns().get(4);
				tcTicketName.setCellValueFactory(new PropertyValueFactory<>("ticketName"));
				tcTicketName.setStyle("-fx-alignment:CENTER;");
				
				TableColumn tcPrice=c3TableView.getColumns().get(5);
				tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
				tcPrice.setStyle("-fx-alignment:CENTER;");
				
				c3TableView.setItems(c3NewTicketObList);
			}else {
				callAlert("CODE ERROR : 존재하는 공연이 아닙니다.");
				return;
			}
				
			// 테이블뷰에 db리스트 세팅

			
			// confrim버튼에 액션
			c3ButtonConfirm.setOnAction((ActionEvent e)-> {createTicketStage.close();});
			
			// t1TableView 리프레시
			t2PerTicketDbArrayList.clear();
			t2PerTicketObList.clear();
			t2PerTicketDbArrayList=PerformanceDAO.getPerTicketTotalData();
			for(PerTicket pert : t2PerTicketDbArrayList) {
				t2PerTicketObList.add(pert);
			}
			t1TableView.setItems(t2PerTicketObList);	
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/addticketedit.css").toString());
			createTicketStage.setScene(scene);
			createTicketStage.setResizable(false);
			createTicketStage.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//********************************************************************************************************************************* t3 모듈부
	// t3 textfield seteditable false
	private void t3textFieldSet() {
		t3TextFieldMusical.setEditable(false);
		t3TextFieldConcert.setEditable(false);
		t3TextFieldPlay.setEditable(false);
		t3TextFieldFestival.setEditable(false);
		t3TextFieldExhibition.setEditable(false);
		t3TextFieldFirst.setEditable(false);
		t3TextFieldSecond.setEditable(false);
		t3TextFieldThird.setEditable(false);
		t3TextFieldFourth.setEditable(false);
		t3TextFieldTotal.setEditable(false);
	}
	
	// t3 분기별 매출 linechart 세팅
	private void t3LineChartSet(MouseEvent event) {
		if(event.getClickCount()==1) {
			t3LineChartRevenue.getData().clear();
			quarterlyRevenueDbArrayList=TicketDAO.sellTicketQuarterlyRevenue("2019");
			t3TextFieldFirst.setText(String.valueOf(quarterlyRevenueDbArrayList.get(0)));
			t3TextFieldSecond.setText(String.valueOf(quarterlyRevenueDbArrayList.get(1)));
			t3TextFieldThird.setText(String.valueOf(quarterlyRevenueDbArrayList.get(2)));
			t3TextFieldFourth.setText(String.valueOf(quarterlyRevenueDbArrayList.get(3)));
			t3TextFieldTotal.setText(String.valueOf(quarterlyRevenueDbArrayList.get(3)+quarterlyRevenueDbArrayList.get(2)+quarterlyRevenueDbArrayList.get(1)+quarterlyRevenueDbArrayList.get(0)));
			t3LineChartRevenue.setAnimated(false);
			
			XYChart.Series seriesFirstQuarter =new XYChart.Series<>();
			seriesFirstQuarter.setName("1/4");
			ObservableList firstQuarterObList = FXCollections.observableArrayList();
			firstQuarterObList.add(new XYChart.Data<>("1/4", quarterlyRevenueDbArrayList.get(0)));
			seriesFirstQuarter.setData(firstQuarterObList);
			t3LineChartRevenue.getData().add(seriesFirstQuarter);
			
			XYChart.Series seriesSecondQuarter =new XYChart.Series<>();
			seriesSecondQuarter.setName("2/4");
			ObservableList secondQuarterObList = FXCollections.observableArrayList();
			secondQuarterObList.add(new XYChart.Data<>("2/4", quarterlyRevenueDbArrayList.get(1)));
			seriesSecondQuarter.setData(secondQuarterObList);
			t3LineChartRevenue.getData().add(seriesSecondQuarter);

			XYChart.Series seriesThirdQuarter =new XYChart.Series<>();
			seriesThirdQuarter.setName("3/4");
			ObservableList thirdQuarterObList = FXCollections.observableArrayList();
			thirdQuarterObList.add(new XYChart.Data<>("3/4", quarterlyRevenueDbArrayList.get(2)));
			seriesThirdQuarter.setData(thirdQuarterObList);
			t3LineChartRevenue.getData().add(seriesThirdQuarter);
			
			XYChart.Series seriesFourthQuarter =new XYChart.Series<>();
			seriesFourthQuarter.setName("4/4");
			ObservableList fourthQuarterObList = FXCollections.observableArrayList();
			fourthQuarterObList.add(new XYChart.Data<>("4/4", quarterlyRevenueDbArrayList.get(3)));
			seriesFourthQuarter.setData(fourthQuarterObList);
			t3LineChartRevenue.getData().add(seriesFourthQuarter);
		}
	}
	
	// t3 누적 매출액 바차트 세팅
	private void t3BarChartRevenueSet(MouseEvent event) {
		if(event.getClickCount()==1) {
			t3BarChartRevenue.getData().clear();
			t3BarChartRevenue.setCategoryGap(65);
			t3BarChartRevenue.setAnimated(false);
			quarterlyRevenueDbArrayList=TicketDAO.sellTicketQuarterlyRevenue("2019");
			t3TextFieldFirst.setText(String.valueOf(quarterlyRevenueDbArrayList.get(0)));
			t3TextFieldSecond.setText(String.valueOf(quarterlyRevenueDbArrayList.get(1)));
			t3TextFieldThird.setText(String.valueOf(quarterlyRevenueDbArrayList.get(2)));
			t3TextFieldFourth.setText(String.valueOf(quarterlyRevenueDbArrayList.get(3)));
			t3TextFieldTotal.setText(String.valueOf(quarterlyRevenueDbArrayList.get(3)+quarterlyRevenueDbArrayList.get(2)+quarterlyRevenueDbArrayList.get(1)+quarterlyRevenueDbArrayList.get(0)));
			XYChart.Series seriesFirstQuarterAcc =new XYChart.Series<>();
			seriesFirstQuarterAcc.setName("1/4");
			
			ObservableList firstQuarterAccObList = FXCollections.observableArrayList();
			firstQuarterAccObList.add(new XYChart.Data<>("1/4", quarterlyRevenueDbArrayList.get(0)));
			seriesFirstQuarterAcc.setData(firstQuarterAccObList);
			t3BarChartRevenue.getData().add(seriesFirstQuarterAcc);
			
			t3BarChartRevenue.setBarGap(1);
			XYChart.Series seriesSecondQuarterAcc =new XYChart.Series<>();
			seriesSecondQuarterAcc.setName("2/4");
			ObservableList secondQuarterAccObList = FXCollections.observableArrayList();
			secondQuarterAccObList.add(new XYChart.Data<>("2/4", quarterlyRevenueDbArrayList.get(1)+quarterlyRevenueDbArrayList.get(0)));
			seriesSecondQuarterAcc.setData(secondQuarterAccObList);
			t3BarChartRevenue.getData().add(seriesSecondQuarterAcc);

			XYChart.Series seriesThirdQuarterAcc =new XYChart.Series<>();
			seriesThirdQuarterAcc.setName("3/4");
			ObservableList thirdQuarterAccObList = FXCollections.observableArrayList();
			thirdQuarterAccObList.add(new XYChart.Data<>("3/4", quarterlyRevenueDbArrayList.get(2)+quarterlyRevenueDbArrayList.get(1)+quarterlyRevenueDbArrayList.get(0)));
			seriesThirdQuarterAcc.setData(thirdQuarterAccObList);
			t3BarChartRevenue.getData().add(seriesThirdQuarterAcc);

			XYChart.Series seriesFourthQuarterAcc =new XYChart.Series<>();
			seriesFourthQuarterAcc.setName("4/4");
			ObservableList fourthQuarterAccObList = FXCollections.observableArrayList();
			fourthQuarterAccObList.add(new XYChart.Data<>("4/4", quarterlyRevenueDbArrayList.get(3)+quarterlyRevenueDbArrayList.get(2)+quarterlyRevenueDbArrayList.get(1)+quarterlyRevenueDbArrayList.get(0)));
			seriesFourthQuarterAcc.setData(fourthQuarterAccObList);
			t3BarChartRevenue.getData().add(seriesFourthQuarterAcc);
		}
	}
	
	// t3 genre 별 매출 바 차트 세팅
	private void t3BarChartGenreRevenueSet(MouseEvent event) {
		if(event.getClickCount()==1) {
		
			t3BarChartGenre.getData().clear();
			t3BarChartGenre.setAnimated(false);
			t3BarChartGenre.setCategoryGap(52);
			genreMusicalRevenueDbArrayList=PerformanceDAO.getPerformanceGenreRevenue("musical");
			t3TextFieldMusical.setText(String.valueOf(genreMusicalRevenueDbArrayList.get(0)));
			XYChart.Series seriesMusical =new XYChart.Series<>();
			seriesMusical.setName("musical");
			ObservableList seriesMusicalObList = FXCollections.observableArrayList();
			seriesMusicalObList.add(new XYChart.Data<>("musical", genreMusicalRevenueDbArrayList.get(0)));
			seriesMusical.setData(seriesMusicalObList);
			t3BarChartGenre.getData().add(seriesMusical);
			
			genreConcertRevenueDbArrayList=PerformanceDAO.getPerformanceGenreRevenue("concert");
			t3TextFieldConcert.setText(String.valueOf(genreConcertRevenueDbArrayList.get(0)));
			XYChart.Series seriesConcert =new XYChart.Series<>();
			seriesConcert.setName("concert");
			ObservableList seriesConcertObList = FXCollections.observableArrayList();
			seriesConcertObList.add(new XYChart.Data<>("concert", genreConcertRevenueDbArrayList.get(0)));
			seriesConcert.setData(seriesConcertObList);
			t3BarChartGenre.getData().add(seriesConcert);

			
			genrePlayRevenueDbArrayList=PerformanceDAO.getPerformanceGenreRevenue("play");
			t3TextFieldPlay.setText(String.valueOf(genrePlayRevenueDbArrayList.get(0)));
			XYChart.Series seriesPlay =new XYChart.Series<>();
			seriesPlay.setName("play");
			ObservableList seriesPlayObList = FXCollections.observableArrayList();
			seriesPlayObList.add(new XYChart.Data<>("play", genrePlayRevenueDbArrayList.get(0)));
			seriesPlay.setData(seriesPlayObList);
			t3BarChartGenre.getData().add(seriesPlay);
			
			genreFestivalRevenueDbArrayList=PerformanceDAO.getPerformanceGenreRevenue("festival");
			t3TextFieldFestival.setText(String.valueOf(genreFestivalRevenueDbArrayList.get(0)));
			XYChart.Series seriesFestival =new XYChart.Series<>();
			seriesFestival.setName("festival");
			ObservableList seriesFestivalObList = FXCollections.observableArrayList();
			seriesFestivalObList.add(new XYChart.Data<>("festival", genreFestivalRevenueDbArrayList.get(0)));
			seriesFestival.setData(seriesFestivalObList);
			t3BarChartGenre.getData().add(seriesFestival);
			
			genreExhibitionRevenueDbArrayList=PerformanceDAO.getPerformanceGenreRevenue("exhibition");
			t3TextFieldExhibition.setText(String.valueOf(genreExhibitionRevenueDbArrayList.get(0)));
			XYChart.Series seriesExhibition =new XYChart.Series<>();
			seriesExhibition.setName("exhibition");
			ObservableList seriesExhibitionObList = FXCollections.observableArrayList();
			seriesExhibitionObList.add(new XYChart.Data<>("exhibition", genreExhibitionRevenueDbArrayList.get(0)));
			seriesExhibition.setData(seriesExhibitionObList);
			t3BarChartGenre.getData().add(seriesExhibition);

		}
		
		
	}
	
	// t3 genre 비율 파이차트 세팅
	private void t3PieCharGenreSet(MouseEvent event) {
		if(event.getClickCount()==1) {
			t3PieChartGenre.getData().clear();
			t3PieChartGenre.setAnimated(false);
			genreRatioDbArrayList=PerformanceDAO.getPerformanceGenreRatioData();
			ObservableList pieGenreRatioObList=FXCollections.observableArrayList();
//			t3TextFieldMusical.setText(String.valueOf(genreRatioDbArrayList.get(2)));
//			t3TextFieldConcert.setText(String.valueOf(genreRatioDbArrayList.get(0)));
//			t3TextFieldPlay.setText(String.valueOf(genreRatioDbArrayList.get(1)));
//			t3TextFieldFestival.setText(String.valueOf(genreRatioDbArrayList.get(4)));
//			t3TextFieldExhibition.setText(String.valueOf(genreRatioDbArrayList.get(3)));
			pieGenreRatioObList.add(new PieChart.Data("CONCERT", genreRatioDbArrayList.get(0)));
			pieGenreRatioObList.add(new PieChart.Data("PLAY", genreRatioDbArrayList.get(1)));
			pieGenreRatioObList.add(new PieChart.Data("MUSICAL", genreRatioDbArrayList.get(2)));
			pieGenreRatioObList.add(new PieChart.Data("EXHIBITION", genreRatioDbArrayList.get(3)));
			pieGenreRatioObList.add(new PieChart.Data("FESTIVAL", genreRatioDbArrayList.get(4)));
			t3PieChartGenre.setData(pieGenreRatioObList);
		}
	}
	
	// t3 tab 전환 될때마다 리셋 세팅
	private void t3TabRefreshSet() {
		t3LineChartRevenue.getData().clear();
		t3BarChartRevenue.getData().clear();
		t3PieChartGenre.getData().clear();
		t3BarChartGenre.getData().clear();
		t3TextFieldFirst.clear();
		t3TextFieldSecond.clear();
		t3TextFieldThird.clear();
		t3TextFieldFourth.clear();
		t3TextFieldTotal.clear();
		t3TextFieldMusical.clear();
		t3TextFieldConcert.clear();
		t3TextFieldPlay.clear();
		t3TextFieldFestival.clear();
		t3TextFieldExhibition.clear();
	}
	
	
	
	//------------------------------------------------------------------------------------------ 기타 모듈부
	// 기타 : 알림창 = "오류정보 : 값을 제대로 입력해주세요" (꼭 콜론을 적어줄것)
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
	
	
	// 기타 : 입력값 필드 포맷 성정 기능 : 숫자 4자리만 받는 기능.
	private void inputDecimalFormat(TextField textField) {
		DecimalFormat format = new DecimalFormat("####");
		textField.setTextFormatter(new TextFormatter<>(event -> {  
			if (event.getControlNewText().isEmpty()) { return event; }
			    	ParsePosition parsePosition = new ParsePosition(0);
			    	Object object = format.parse(event.getControlNewText(), parsePosition); 
			if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
			      || event.getControlNewText().length() == 5) {
			     return null;    
			}else {
			     return event;    
			}   
		}));
	}

	// 기타 : 입력값 필드 포맷 성정 기능 : 숫자 2자리만 받는 기능.
	private void inputDecimalFormatWonDigit(TextField textField) {
		DecimalFormat format = new DecimalFormat("########");
		textField.setTextFormatter(new TextFormatter<>(event -> {  
			if (event.getControlNewText().isEmpty()) { return event; }
			    	ParsePosition parsePosition = new ParsePosition(0);
			    	Object object = format.parse(event.getControlNewText(), parsePosition); 
			if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
			      || event.getControlNewText().length() == 9) {
			     return null;    
			}else {
			     return event;    
			}   
		}));
	}

	// 기타 : 이미지 c:/images/student120938213_images.jpg로 재가공된 이름으로 저장하는 기능
	private void t2ImageSave() {
		// 단계6, 만약 이미지용 디렉토리가 생성 안되어있으면 폴더 생성
		if(!imageDir.exists()) {
			imageDir.mkdir();
		}
		// 단계6, 선택된 이미지를 C:/images/에 "선택된 이미지 이름명"으로 저장
		FileInputStream fis=null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fis=new FileInputStream(t2SelectFile); // 13번에서 버튼 fileChooser을 통해서 선택된 파일
			bis = new BufferedInputStream(fis);
			// 선택된(fileChooser) 파일명이 C:/kkk/pppp/jjjj/이미지.jpg >> .getName(); 하면 이미지.jpg만 가져옴
			// 새로운 파일명을 규정하는데 "student"+밀리단위 시간+이미지.jpg 로 재가공 된다.
			t2FileName="poster_"+System.currentTimeMillis()+"_"+t2SelectFile.getName();
			// imageDir.getAbsolutePath()+"\\"+fileName -> C:/images/student12313124_이미지.jpg로 저장 됨
			// C:/kkk/pppp/jjjj/이미지.jpg 를 읽어서 C:/images/student12313124_이미지.jpg로 저장함.
			fos = new FileOutputStream(imageDir.getAbsolutePath()+"\\"+t2FileName);
			bos = new BufferedOutputStream(fos);
			
			int data=-1;
			while((data=bis.read())!=-1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			
		}finally {
			try {
				if(fis != null) {fis.close();}
				if(bis != null) {bis.close();}
				if(fos != null) {fos.close();}
				if(bos != null) {bos.close();}	
			}catch (IOException e) {
			}
		}
	}
	private void c2ImageSave() {
		// 단계6, 만약 이미지용 디렉토리가 생성 안되어있으면 폴더 생성
		if(!imageDir.exists()) {
			imageDir.mkdir();
		}
		// 단계6, 선택된 이미지를 C:/images/에 "선택된 이미지 이름명"으로 저장
		FileInputStream fis=null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fis=new FileInputStream(c2SelectFile); // 13번에서 버튼 fileChooser을 통해서 선택된 파일
			bis = new BufferedInputStream(fis);
			// 선택된(fileChooser) 파일명이 C:/kkk/pppp/jjjj/이미지.jpg >> .getName(); 하면 이미지.jpg만 가져옴
			// 새로운 파일명을 규정하는데 "student"+밀리단위 시간+이미지.jpg 로 재가공 된다.
			c2FileName="poster_"+System.currentTimeMillis()+"_"+c2SelectFile.getName();
			// imageDir.getAbsolutePath()+"\\"+fileName -> C:/images/student12313124_이미지.jpg로 저장 됨
			// C:/kkk/pppp/jjjj/이미지.jpg 를 읽어서 C:/images/student12313124_이미지.jpg로 저장함.
			fos = new FileOutputStream(imageDir.getAbsolutePath()+"\\"+c2FileName);
			bos = new BufferedOutputStream(fos);
			
			int data=-1;
			while((data=bis.read())!=-1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			
		}finally {
			try {
				if(fis != null) {fis.close();}
				if(bis != null) {bis.close();}
				if(fos != null) {fos.close();}
				if(bos != null) {bos.close();}	
			}catch (IOException e) {
			}
		}
	}
	
	
	// 기타 : 삭제 버튼에 있는 이미지 파일 삭제 기능
	private void imageDelete() {
		boolean delFlag=false;
		File imageFile = new File(imageDir.getAbsolutePath()+"\\"+ButtonViewPerformance.get(0).getPoster()); // 삭제버튼으로 이미지파일 삭제
		if(imageFile.exists() && imageFile.isFile()) {
			delFlag=imageFile.delete();
			if(delFlag==false) {
				callAlert("이미지 제거 실패 : 이미지 제거 실패, 점검 요망");
			}
		}
	}
	

}
