package javaFX;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.Passerelle;
import personnel.SauvegardeImpossible;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.SortedSet;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class Controller {
	@FXML private URL location;
	private Button button;
	public final static int SERIALIZATION = 1, JDBC = 2, TYPE_PASSERELLE = JDBC;  
	private static Passerelle passerelle = new jdbc.JDBC();
	public static int[] idUserLigue = new int[2];
	public static SortedSet<Employe> employes;
	public static List<Card> cardList = new ArrayList<Card>();
	public static Pane[] lstEmployes;
	public VBox vb = new VBox();
	public VBox vbEmploye = new VBox();
	@FXML public static ScrollPane vList;
	public Scene sc;
	@FXML public ScrollPane scEmployes;
	private static Ligue ligue;
	private static GestionPersonnel gp;
	private Stage win;
	private int idRole = 1;
	@FXML Pane mPane;
	@FXML CheckBox chkAll;
	
    @FXML
    private void handleButtonAction(ActionEvent e) throws IOException {
    	button = (Button) e.getSource();
    	Scene scne = button.getScene();
    	Window windows = scne.getWindow();
    	TextField loginField  = (TextField) scne.lookup("#InputLogin");
    	PasswordField passField = (PasswordField) scne.lookup("#InputPass");
    	
    	loginField.getText();
    	 idUserLigue = passerelle.checkUser(loginField.getText(),passField.getText());
    	 importLigue();
    	 //ADMIN
    	if(idUserLigue[0] != -1 && idUserLigue[1] == 1) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("hellofx.fxml"));
    		Parent root = loader.load();
    		win = (Stage)windows;
    		Scene sceneHome = new Scene(root);
    		win.setScene(sceneHome);
    		win.setTitle("Panel de gestion administrateur");
    		win.show();
    	}
        	// USERS
    		else if(idUserLigue[1] == 0){
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("user.fxml"));
    		Parent root = loader.load();
    		win = (Stage)windows;
    		Scene sceneHome = new Scene(root);
    		win.setScene(sceneHome);
    		win.setTitle("Panel utilisateur");
    		win.show();
    		
    	}
    	else {
    		// retourne une erreur
    		Label warningLabel = (Label) scne.lookup("#warningLabel");
    		warningLabel.setText("Mot de passe incorrects");
    	}
    	
    	
    }
    
    private void importLigue() {
    	 gp = gp == null ?  passerelle.getGestionPersonnel() : gp;
      	SortedSet<Ligue> ligues = gp.getLigues();
      	for(Ligue ligue : ligues) {
      		System.out.print(ligue.getId()+" "+idUserLigue[0]+" | ");
      		if(ligue.getId() == idUserLigue[0]) {
      			Controller.ligue = ligue;
      			employes = ligue.getEmployes();
      			 lstEmployes = new Pane[employes.size()];
      			 System.out.println(employes.size());
      		}
      	}
    }
    
    private Pane cardCreator(Employe employe,int idRole){
    	Card card = new Card(employe,vList,idRole);
    	card.setEvent(vbEmploye,scEmployes);
    	cardList.add(card);
    	return card.getShortPane();
    }

    @FXML
    // btn Refresh
    // Ajoute la liste de tout les collaborateurs de la ligue
    private void handleButtonActions(ActionEvent e) {
    	importData(e);
    }
    
    public void importData(ActionEvent e) {
    	importLigue();
    	cardList.clear();
    	if(location.toString().contains("user.fxml")) {
        	idRole = 0;
        	}
        	Button tButton = (Button)e.getSource();
        	chkAll.setSelected(false);
        	tButton.setDisable(true);
        	sc = tButton.getScene();
        	scEmployes = (ScrollPane) sc.lookup("#cardList");
        	 vList = (ScrollPane) sc.lookup("#employeList");
            vList.setPadding(new Insets(0));
            
        	for(Employe employe : employes) {
    		vb.getChildren().add(cardCreator(employe,idRole ));
        	}
        	scEmployes.setContent(vbEmploye);
        	vList.setContent(vb);  
    }
    
    @FXML
    private void handleChkAction(ActionEvent e) {
    	chkAll = (CheckBox) e.getSource();
    	if(chkAll.isSelected()) {
    		for(Card card : cardList) {
        		card.checkChk(vbEmploye,scEmployes,true);
        	}
    	}else {
    		for(Card card : cardList) {
        		card.checkChk(vbEmploye,scEmployes,false);
        	}
    	}
    	
    }
    @FXML private void handleDeleteAction(ActionEvent e) throws SauvegardeImpossible {
    	//TODO FAIRE FENETRE CONFIRMATION DU CHOIX , REUSSITE , ECHEC
    	Group rDelete= new Group();
    	Button bDelete = (Button) e.getSource();
		Window wDelete= bDelete.getScene().getWindow();
		Stage sDelete = new Stage();
		Scene scDelete = new Scene(rDelete);
		sDelete.setScene(scDelete);
		GridPane gpDelete = new GridPane();
		Label lbDelete = new Label("Voulez vous confirmer la suppression ?");
		Button btnOui = new Button("Oui");
		Button btnNon = new Button("Non");
		
		btnNon.setMaxWidth(100);
		btnOui.setMaxWidth(100);
		
		ColumnConstraints col1 = new ColumnConstraints();
	     col1.setPercentWidth(50);
	     ColumnConstraints col2 = new ColumnConstraints();
	     col2.setPercentWidth(50);
	     GridPane.setColumnSpan(lbDelete,2);
	     gpDelete.setHgap(20);
	     gpDelete.setVgap(20);

	     gpDelete.getColumnConstraints().addAll(col1,col2);
		
		rDelete.getChildren().add(gpDelete);
		gpDelete.add(lbDelete, 0, 0);
		gpDelete.add(btnOui, 1, 1);
		gpDelete.add(btnNon,0,1);

		
		gpDelete.setPadding(new Insets(30));
		
		gpDelete.setHgap(20);
		gpDelete.setVgap(5);
		
		 sDelete.setTitle("Supprimer l'utilisateur");
	        sDelete.initModality(Modality.WINDOW_MODAL);
	        sDelete.initOwner(wDelete);
	        sDelete.setResizable(false);
	        sDelete.show();
	        
	        btnOui.setOnAction(en->{
	        	for(Card card : cardList) {
	        		if(card.getcheked()) {
	        			try {
							passerelle.deleteEmploye(card.getEmploye());
						} catch (SauvegardeImpossible e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	        			vbEmploye.getChildren().remove(card.getFullCard());
	        			vb.getChildren().remove(card.getPane());
	        		}
	        	}
	        	sDelete.close();
;	        });
	        
	        btnNon.setOnAction(ens->{
	        	sDelete.close();
	        });
	        
    	
    }
    @FXML
    private void handleAddAction(ActionEvent e) {
    	//TODO FAIRE UNE FENETRE ERREUR & FENETRE REUSSITE
    	Group rAdd = new Group();
    	Button btnAdd = (Button)e.getSource();
		Window wAdd = btnAdd.getScene().getWindow();
		Stage sAdd = new Stage();
		GridPane gpAdd = new GridPane();
		gpAdd.setPadding(new Insets(30));
		
		gpAdd.setHgap(20);
		gpAdd.setVgap(5);
		
		TextField tfPrenom = new TextField();
		TextField tfEmail = new TextField();
		TextField tfNom = new TextField();
		PasswordField pfMdp = new PasswordField();
		PasswordField pfconfirmMdp = new PasswordField();
		
		Button btnCancel = new Button("Annuler");
		Button btnValidate = new Button("Valider");
		
		Label lbPrenom = new Label("Prénom :");
		Label lbNom = new Label("Nom : ");
		Label lbMdp = new Label("Mot de passe : ");
		Label lbEmails = new Label("Email :");
		Label lbConfirmMdp = new Label("Confirmation du mot de passe :");

		//Gestion Affichage dans la grid
		
		GridPane.setRowIndex(lbPrenom,0);
		GridPane.setColumnIndex(lbPrenom,0);
		
		GridPane.setRowIndex(lbNom,0);
		GridPane.setColumnIndex(lbNom,1);
		
		GridPane.setRowIndex(tfPrenom,1);
		GridPane.setColumnIndex(tfPrenom,0);
		
		GridPane.setRowIndex(tfNom,1);
		GridPane.setColumnIndex(tfNom,1);
		
		GridPane.setRowIndex(lbEmails,2);
		GridPane.setColumnIndex(lbEmails,0);
		
		GridPane.setRowIndex(tfEmail,3);
		GridPane.setColumnIndex(tfEmail,0);
		GridPane.setColumnSpan(tfEmail,2);
		
		GridPane.setRowIndex(lbMdp,4);
		GridPane.setColumnIndex(lbMdp,0);
		
		GridPane.setRowIndex(lbConfirmMdp,4);
		GridPane.setColumnIndex(lbConfirmMdp,1);
		
		GridPane.setRowIndex(pfMdp,5);
		GridPane.setColumnIndex(pfMdp,0);
		
		GridPane.setRowIndex(pfconfirmMdp,5);
		GridPane.setColumnIndex(pfconfirmMdp,1);
	
		
		
		GridPane.setRowIndex(btnCancel,6);
		GridPane.setColumnIndex(btnCancel,0);
		
		GridPane.setRowIndex(btnValidate,6);
		GridPane.setColumnIndex(btnValidate,1);
		
		
		gpAdd.getChildren().add(lbPrenom);
		gpAdd.getChildren().add(tfPrenom);
		gpAdd.getChildren().add(lbNom);
		gpAdd.getChildren().add(tfNom);
		gpAdd.getChildren().add(lbEmails);
		gpAdd.getChildren().add(tfEmail);
		gpAdd.getChildren().add(lbMdp);
		gpAdd.getChildren().add(pfMdp);
		gpAdd.getChildren().add(lbConfirmMdp);
		gpAdd.getChildren().add(pfconfirmMdp);
		gpAdd.getChildren().add(btnCancel);
		gpAdd.getChildren().add(btnValidate);
		
		btnCancel.setOnAction(event-> {
			sAdd.close();
		});
		btnValidate.setOnAction(ev->{
			try {
				Employe emp = new Employe(gp,ligue,tfNom.getText(),tfPrenom.getText(),tfEmail.getText(),pfMdp.getText(),LocalDate.now(),null,0);
				vb.getChildren().add(cardCreator(emp,1));
				;
			} catch (SauvegardeImpossible e1) {
				e1.printStackTrace();
			}
		});
		rAdd.getChildren().add(gpAdd);
		Scene scModif = new Scene(rAdd);
		sAdd.setScene(scModif);
		

        sAdd.setTitle("Ajouter un collaborateur ");
        //stage.getIcons().add(icon);
        sAdd.initModality(Modality.WINDOW_MODAL);
        sAdd.initOwner(wAdd);
        sAdd.setResizable(false);
        sAdd.show();
    }
    
    @FXML
    public void handleButtonsLogout(ActionEvent e) throws IOException {
    	Button btnLogout = (Button)e.getSource();
    	Scene scnLogout = btnLogout.getScene();
    	Stage stgLogout = (Stage) scnLogout.getWindow();
    	logout(stgLogout);
    }
    public void logout(Stage outStage) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
		  Parent root = loader.load();
		  Scene scene = new Scene(root);
		  outStage.setTitle("Connexion M2L Ligue");
		  outStage.setScene(scene);
		  outStage.show();
    }
}