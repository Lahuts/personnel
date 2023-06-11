package javaFX;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.*;
import personnel.Employe;
import personnel.MauvaiseDate;
import personnel.SauvegardeImpossible;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.*;
import javafx.scene.layout.ColumnConstraints;


public class Card {
	GridPane pane;
	private Label lbName;
	private Label lbEmail;
	private Label lbMdp;
	private Label lbDateArrive;
	private CheckBox chk;
	private Employe employe;
	private GridPane fullCard;
	private int idRole;
	
	public Card(Employe employe, ScrollPane vList,int idRole) {
		this.pane = new GridPane();
		this.lbName = new Label();
		this.lbEmail = new Label();
		this.lbMdp = new Label();
		this.lbDateArrive = new Label();
		this.chk = new CheckBox();
		this.employe = employe;
		this.idRole = idRole;
		

		lbDateArrive.setText(employe.getArrive().toString());
		lbName.setText(employe.getPrenom()+" "+employe.getNom());
		lbName.setStyle("-fx-font: 24 arial;"
				+ "-fx-font-weight: bold;");
    	lbName.setPadding(new Insets(5,0,0,0));
    	lbEmail.setText(employe.getMail());
    	lbEmail.setPadding(new Insets(0,0,20,0));
    	chk.setPadding(new Insets(20));
    	
    	pane.getChildren().add(lbName);
    	pane.getChildren().add(lbEmail);
    	pane.getChildren().add(chk);
    	pane.getChildren().add(lbDateArrive);
    	pane.setStyle("-fx-background-color:white");
    	if(idRole == 1) {
    		pane.getChildren().add(lbMdp);
    	}
    	
    	

		
    	pane.prefWidthProperty().bind(vList.widthProperty());
    	
    	pane.setBorder(new Border(new BorderStroke(Color.valueOf("#ededed"),
    		    BorderStrokeStyle.SOLID,
    		    CornerRadii.EMPTY,
    		    BorderWidths.DEFAULT)));
		
		
	}
	public Pane getPane() {
		return pane;
	}
	public Pane getFullCard() {
		return fullCard;
	}
	public Employe getEmploye() {
		return employe;
	}
	public Boolean getcheked() {
		if(chk.isSelected()) {
			return true;
		}else {
			return false;
		}
	}
	public Pane getShortPane() {
		
		
    	
    	GridPane.setRowIndex(chk, 0);
    	GridPane.setColumnIndex(chk, 0);
    	GridPane.setRowIndex(lbName,0);
    	GridPane.setColumnIndex(lbName,1);
    	GridPane.setRowIndex(lbEmail, 1);
    	GridPane.setColumnIndex(lbEmail, 1);
    	lbDateArrive.setVisible(false);
    	lbMdp.setVisible(false);

    	
		return pane;
	}
	public Pane getFullPane(ScrollPane vList) {
		Label lbUmail = new Label();
		Label lbTmail = new Label();
		Label lbUmdp = new Label();
		Label lbTmdp = new Label();
		Label lbUarrive = new Label();
		Label lbTarrive = new Label();
		Label lbUdepart = new Label();
		Label lbTdepart = new Label();
		Label lbnames = new Label();
		
		lbTmail.setStyle("-fx-font: 12 arial;"
				+ "-fx-font-weight: bold;");
		lbTmdp.setStyle("-fx-font: 12 arial;"
				+ "-fx-font-weight: bold;");
		lbTarrive.setStyle("-fx-font: 12 arial;"
				+ "-fx-font-weight: bold;");
		lbTdepart.setStyle("-fx-font: 12 arial;"
				+ "-fx-font-weight: bold;");
		
		
		Button btnTmodifier = new Button();
		btnTmodifier.setStyle("-fx-background-color: white;"
				+ "-fx-border-color: black;"+"-fx-border-radius:5;");
		
		fullCard = new GridPane();
		fullCard.setStyle("-fx-background-color:white");
		fullCard.setPadding(new Insets(10));
		
		if(idRole == 0) {
			lbUmdp.setVisible(false);
			lbTmdp.setVisible(false);
			btnTmodifier.setVisible(false);
		}
		
		lbTmail.setText("Mail :");
		lbUmail.setText(employe.getMail());
		
		lbTmdp.setText("Mot de passe :");
		lbUmdp.setText("●●●●●●●●●");
		
		lbTarrive.setText("Date d'arrivée :");
		lbUarrive.setText(employe.getArrive().toString());
		
		lbTdepart.setText("Date Départ :");
		
		try {
			lbUdepart.setText(employe.getDepart().toString());
		}
		catch(NullPointerException npe) {
			lbUdepart.setText("");
		}
		
		
		lbnames.setText(employe.getPrenom()+" "+employe.getNom());
		lbnames.setStyle("-fx-font: 20 arial;"
				+ "-fx-font-weight: bold;");
		
		btnTmodifier.setText("Modifier");
		
		btnTmodifier.setOnAction(e ->{
			Group root = new Group();
			Window window = btnTmodifier.getScene().getWindow();
			Stage stage = new Stage();
			GridPane gpModif = new GridPane();
			gpModif.setPadding(new Insets(30));
			
			gpModif.setHgap(20);
			gpModif.setVgap(5);
			
			TextField tfPrenom = new TextField(employe.getPrenom());
			TextField tfEmail = new TextField(employe.getMail());
			TextField tfNom = new TextField(employe.getNom());
			PasswordField pfMdp = new PasswordField();
			PasswordField pfconfirmMdp = new PasswordField();
			TextField tfArrive = new TextField(employe.getArrive().toString());
			TextField tfDepart = new TextField();
			
			try 
			{
				tfDepart.setText(employe.getDepart().toString());
			}
			catch(NullPointerException npe) 
			{
				tfDepart.setText("");
			}
			
			
			Button btnCancel = new Button("Annuler");
			Button btnValidate = new Button("Valider");
			
			Label lbPrenom = new Label("Prénom :");
			Label lbNom = new Label("Nom : ");
			Label lbMdp = new Label("Mot de passe : ");
			Label lbEmails = new Label("Email :");
			Label lbConfirmMdp = new Label("Confirmation du mot de passe :");
			Label lbArrive = new Label("Date d'arrivée :");
			Label lbDepart = new Label("Date de départ :");

			
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
			
			GridPane.setRowIndex(lbArrive,6);
			GridPane.setColumnIndex(lbArrive,0);
			
			GridPane.setRowIndex(lbDepart,6);
			GridPane.setColumnIndex(lbDepart,1);
			
			GridPane.setRowIndex(tfArrive,7);
			GridPane.setColumnIndex(tfArrive,0);
			
			GridPane.setRowIndex(tfDepart,7);
			GridPane.setColumnIndex(tfDepart,1);
			
			GridPane.setRowIndex(btnCancel,8);
			GridPane.setColumnIndex(btnCancel,0);
			
			GridPane.setRowIndex(btnValidate,8);
			GridPane.setColumnIndex(btnValidate,1);
			
			
			gpModif.getChildren().add(lbPrenom);
			gpModif.getChildren().add(tfPrenom);
			gpModif.getChildren().add(lbNom);
			gpModif.getChildren().add(tfNom);
			gpModif.getChildren().add(lbEmails);
			gpModif.getChildren().add(tfEmail);
			gpModif.getChildren().add(lbMdp);
			gpModif.getChildren().add(pfMdp);
			gpModif.getChildren().add(lbConfirmMdp);
			gpModif.getChildren().add(pfconfirmMdp);
			gpModif.getChildren().add(lbArrive);
			gpModif.getChildren().add(tfArrive);
			gpModif.getChildren().add(lbDepart);
			gpModif.getChildren().add(tfDepart);
			gpModif.getChildren().add(btnCancel);
			gpModif.getChildren().add(btnValidate);
			
			btnCancel.setOnAction(event-> {
				stage.close();
			});
			
			btnValidate.setOnAction(events->{
				try {
					// Nom Prénom
					employe.setNom(tfNom.getText());
					employe.setPrenom(tfPrenom.getText());
					lbnames.setText(employe.getPrenom()+" "+employe.getNom());
					lbName.setText(employe.getPrenom()+" "+employe.getNom());
					
					// Email
					employe.setMail(tfEmail.getText());
					lbUmail.setText(employe.getMail());
					lbEmail.setText(employe.getMail());
					
					//Password
					if(!pfMdp.getText().equals("") && pfMdp.getText().equals(pfconfirmMdp.getText())) {
						employe.setPassword(pfMdp.getText());
					}
					
					//date depart
					if(!tfDepart.getText().equals("")) {
						employe.setDepart(tfDepart.getText());	
					}
					
					//date arrivée
					employe.setArrive(tfArrive.getText());
					lbUarrive.setText(employe.getArrive().toString());
					
					try {
						lbUdepart.setText(employe.getDepart().toString());
					}
					catch(NullPointerException npe) {
						lbUdepart.setText("");
					}
					
					Group rConfirm = new Group();
					Window wConfirm = btnValidate.getScene().getWindow();
					Stage sConfirm = new Stage();
					Scene scConfirm = new Scene(rConfirm);
					sConfirm.setScene(scConfirm);
					GridPane gpConfirm = new GridPane();
					Label lbConfirm =new Label("Les changements sur le compte de "+employe.getPrenom()+" "+employe.getNom()+" ont est bien était éffectué !");
					Button btnOk = new Button("ok");
					rConfirm.getChildren().add(gpConfirm);
					
					gpConfirm.add(lbConfirm, 0, 0);
					gpConfirm.add(btnOk, 1, 1);
					btnOk.setOnAction(ev->{
						stage.close();
						sConfirm.close();
					});
					
					gpConfirm.setPadding(new Insets(30));
					
					gpConfirm.setHgap(20);
					gpConfirm.setVgap(5);
					
					 sConfirm.setTitle("Erreur Mauvaise Date");
				        //stage.getIcons().add(icon);
				        sConfirm.initModality(Modality.WINDOW_MODAL);
				        sConfirm.initOwner(wConfirm);
				        sConfirm.setResizable(false);
				        sConfirm.show();
					
					
				} catch (SauvegardeImpossible e1) {
					// TODO Auto-generated catch block
					Group rWarning = new Group();
					Window wWarning = btnValidate.getScene().getWindow();
					Stage sWarning = new Stage();
					Scene scWarning = new Scene(rWarning);
					sWarning.setScene(scWarning);
					GridPane gpWarning = new GridPane();
					Label lbWarning =new Label(e1.printStacktrace(e1));
					Button btnOk = new Button("ok");
					rWarning.getChildren().add(gpWarning);
					gpWarning.add(lbWarning, 0, 0);
					gpWarning.add(btnOk, 1, 1);
					
					gpWarning.setPadding(new Insets(30));
					
					gpWarning.setHgap(20);
					gpWarning.setVgap(5);
					
					 sWarning.setTitle("Erreur SQL");
				        //stage.getIcons().add(icon);
				        sWarning.initModality(Modality.WINDOW_MODAL);
				        sWarning.initOwner(wWarning);
				        sWarning.setResizable(false);
				        sWarning.show();
					
					e1.printStackTrace();
				} catch (MauvaiseDate e1) {
					Group rWarning = new Group();
					Window wWarning = btnValidate.getScene().getWindow();
					Stage sWarning = new Stage();
					Scene scWarning = new Scene(rWarning);
					sWarning.setScene(scWarning);
					GridPane gpWarning = new GridPane();
					Label lbWarning =new Label(e1.toString());
					Button btnOk = new Button("ok");
					rWarning.getChildren().add(gpWarning);
					gpWarning.add(lbWarning, 0, 0);
					gpWarning.add(btnOk, 1, 1);
					
					gpWarning.setPadding(new Insets(30));
					
					gpWarning.setHgap(20);
					gpWarning.setVgap(5);
					
					 sWarning.setTitle("Modificaton "+employe.getPrenom()+" "+employe.getNom()+" effectué !");
				        //stage.getIcons().add(icon);
				        sWarning.initModality(Modality.WINDOW_MODAL);
				        sWarning.initOwner(wWarning);
				        sWarning.setResizable(false);
				        sWarning.show();
				}
			});
			root.getChildren().add(gpModif);
			Scene scModif = new Scene(root);
			stage.setScene(scModif);
			

	        stage.setTitle("Modifier "+employe.getPrenom()+" "+employe.getNom());
	        //stage.getIcons().add(icon);
	        stage.initModality(Modality.WINDOW_MODAL);
	        stage.initOwner(window);
	        stage.setResizable(false);
	        stage.show();

		});

		fullCard.getChildren().add(lbUmail);
		fullCard.getChildren().add(lbTmail);
		fullCard.getChildren().add(lbUmdp);
		fullCard.getChildren().add(lbTmdp);
		
		fullCard.getChildren().add(lbUarrive);
		fullCard.getChildren().add(lbTarrive);
		fullCard.getChildren().add(lbUdepart);
		fullCard.getChildren().add(lbTdepart);
		
		fullCard.getChildren().add(lbnames);
		fullCard.getChildren().add(btnTmodifier);
		fullCard.setHgap(20);
		
		fullCard.setVgap(10);
		
		
		
	     ColumnConstraints col1 = new ColumnConstraints();
	     col1.setPercentWidth(50);
	     ColumnConstraints col2 = new ColumnConstraints();
	     col2.setPercentWidth(30);
	     ColumnConstraints col3 = new ColumnConstraints();
	     col3.setPercentWidth(20);
	     fullCard.getColumnConstraints().addAll(col1,col2,col3);
		
		// GRID PANE FULL CARD 
		GridPane.setColumnIndex(lbnames,0);
		
		GridPane.setRowIndex(btnTmodifier, 0);
		GridPane.setColumnIndex(btnTmodifier, 2);
		GridPane.setHalignment(btnTmodifier, HPos.RIGHT);

    	GridPane.setRowIndex(lbTmail,1);
    	GridPane.setColumnIndex(lbTmail,0);
		
		GridPane.setRowIndex(lbUmail, 2);
    	GridPane.setColumnIndex(lbUmail, 0);

    	
    	GridPane.setRowIndex(lbUmdp, 2);
    	GridPane.setColumnIndex(lbUmdp, 1);

    	
    	GridPane.setRowIndex(lbTmdp, 1);
    	GridPane.setColumnIndex(lbTmdp, 1);
    	
    	GridPane.setRowIndex(lbUarrive, 4);
    	GridPane.setColumnIndex(lbUarrive, 0);

    	
    	GridPane.setRowIndex(lbTarrive,3);
    	GridPane.setColumnIndex(lbTarrive,0);
    	
    	GridPane.setRowIndex(lbUdepart, 4);
    	GridPane.setColumnIndex(lbUdepart, 1);

    	
    	GridPane.setRowIndex(lbTdepart, 3);
    	GridPane.setColumnIndex(lbTdepart, 1);
    	


    	
    	
    	fullCard.prefWidthProperty().bind(vList.widthProperty());
    	
    	fullCard.setBorder(new Border(new BorderStroke(Color.valueOf("#ededed"),
    		    BorderStrokeStyle.SOLID,
    		    CornerRadii.EMPTY,
    		    BorderWidths.DEFAULT)));
    	fullCard.setStyle("-fx-border-style: solid none none none; -fx-border-width: 2; -fx-border-color: #ededed; -fx-background-color:white;");
    	
		return fullCard;
		

	}
    	
    	public void setEvent(VBox vbox,ScrollPane scp){
    		
    		chk.setOnAction(e ->{
        		if(chk.isSelected()) {
        			vbox.getChildren().add(getFullPane(scp));
        		}else {
        			vbox.getChildren().remove(fullCard);
        		}
        	});
    	}
    	public void checkChk(VBox vbox,ScrollPane scp,Boolean checked) {
    		if(checked) {
    			if(!chk.isSelected()) {
    				chk.setSelected(true);
    				vbox.getChildren().add(getFullPane(scp));
    			}
    		
    		}else {
    			chk.setSelected(false);
    			vbox.getChildren().remove(fullCard);
    		}
    	}
}
    		
    	

