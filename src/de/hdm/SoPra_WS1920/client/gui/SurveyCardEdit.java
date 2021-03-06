package de.hdm.SoPra_WS1920.client.gui;

import java.util.Vector;
import java.sql.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.SoPra_WS1920.client.ClientsideSettings;
import de.hdm.SoPra_WS1920.client.gui.Admin.ScreeningCardEdit;
import de.hdm.SoPra_WS1920.server.SurveyManagementImpl;
import de.hdm.SoPra_WS1920.shared.CinemaAdministrationAsync;
import de.hdm.SoPra_WS1920.shared.SurveyManagementAsync;
import de.hdm.SoPra_WS1920.shared.bo.Cinema;
import de.hdm.SoPra_WS1920.shared.bo.Group;
import de.hdm.SoPra_WS1920.shared.bo.Movie;
import de.hdm.SoPra_WS1920.shared.bo.Person;
import de.hdm.SoPra_WS1920.shared.bo.Screening;
import de.hdm.SoPra_WS1920.shared.bo.Survey;
import de.hdm.SoPra_WS1920.shared.bo.SurveyEntry;

public class SurveyCardEdit extends DialogBox {
	
	FlowPanel formWrapper;
	SurveyCard parentCard;
	Survey surveyToShow;
	
	//CreateSurvey 1/2
	
	Label cardDescription;
	Label movieLabel;
	Label groupLabel;
	Label cityLabel;
	Label startDateLabel;
	Label endDateLabel;
	
	MultiWordSuggestOracle allMovies;
	SuggestBox movieSuggestBox;
	
	ListBox allGroups;
	
	TextBox cityTextBox;
	
	DateBox startDateBox;
	DateBox endDateBox;
	
	Button invisibleButton;
	Button addScreenings; 
	Image cancelIcon;
	
	Group group;
	Movie movie;
	Person person;
	String city;
	java.sql.Date startDate;
	java.sql.Date endDate;
	
	
	//CreateSurvey 2/2 
	
	Label cardDescription2;
	Label selectedMovie;
	Label selectedGroup;
	Label selectedCity;
	Label selectedPeriod;
	
	FlowPanel screeningSelection;
	Label deleteLabel;
	
	Vector <ScreeningRow> screeningRowVector;
	Vector <Screening> sv;
	Vector <Screening> screeningVector;
	Vector <SurveyEntry> surveyEntryVector;
	
	Label showSelected;
	Button saveSurvey;
	Survey survey;
	
	Cinema cinema;
	
	SurveyManagementHeader header;
	SurveyContent content;
	SurveyManagementAsync surveyManagement;
	CinemaAdministrationAsync cinemaAdministration;
	
	
	
	public SurveyCardEdit(SurveyCard surveyCard, Survey survey) {
		this.parentCard = surveyCard;
		this.surveyToShow = survey;
	}
	
	
	public SurveyCardEdit(SurveyContent content, SurveyManagementHeader header) {
		this.content = content;
		this.header = header;

		
	}
	
	public void onLoad() {
		super.onLoad();
		
		this.setStyleName("EditCard");
		person = new Person();
		person.setId(1);
		surveyManagement = ClientsideSettings.getSurveyManagement();
		cinemaAdministration = ClientsideSettings.getCinemaAdministration();
		formWrapper = new FlowPanel();
		
		
		
	}
	
	public void showCreateCard() {
		cardDescription = new Label("Create Survey 1/2");
		cardDescription.setStyleName("CardDescription");
		cancelIcon = new Image("/Images/png/007-close.png");
		cancelIcon.setStyleName("CancelIcon");
		cancelIcon.addClickHandler(new CancelClickHandler(this));
		invisibleButton = new Button();
		invisibleButton.setStyleName("InvisibleButton");
		
		movieLabel = new Label("Movie");
		movieLabel.setStyleName("TextBoxLabel");
		allMovies = new MultiWordSuggestOracle();
		cinemaAdministration.getAllMovies(new AllMoviesCallback());
		movieSuggestBox = new SuggestBox(allMovies);
		movieSuggestBox.setStyleName("CardSuggestBox");
		
		groupLabel =  new Label("Group");
		groupLabel.setStyleName("TextBoxLabel");
		allGroups = new ListBox();
		surveyManagement.getGroupByPersonFK(person.getId(), new GetGroupsByFKCallback());
		allGroups.setStyleName("CardSuggestBox");
		
		cityLabel = new Label("City");
		cityLabel.setStyleName("TextBoxLabel");
		cityTextBox = new TextBox();
		cityTextBox.setStyleName("CardSuggestBox");
		
		startDateLabel = new Label("Start Date");
		startDateLabel.setStyleName("TextBoxLabel");
		startDateBox = new DateBox();
		startDateBox.setStyleName("CardDatePicker");
		startDateBox.setFormat(
				new DateBox.DefaultFormat(
						DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
		
		
		endDateLabel = new Label("End Date");
		endDateLabel.setStyleName("TextBoxLabel");
		endDateBox = new DateBox();
		endDateBox.setStyleName("CardDatePicker");
		endDateBox.setFormat(
				new DateBox.DefaultFormat(
						DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
				
		
		//setValue, setText notwendig?
		
		formWrapper.add(cardDescription);
		formWrapper.add(cancelIcon);
		formWrapper.add(movieLabel);
		formWrapper.add(movieSuggestBox);
		formWrapper.add(groupLabel);
		formWrapper.add(allGroups);
		formWrapper.add(cityLabel);
		formWrapper.add(cityTextBox);
		formWrapper.add(startDateLabel);
		formWrapper.add(startDateBox);
		formWrapper.add(endDateLabel);
		formWrapper.add(endDateBox);
		

		addScreenings = new Button("Add Screenings");
		addScreenings.setStyleName("SaveButton");
		addScreenings.addClickHandler(new AddScreeningsClickHandler(this));

		formWrapper.add(addScreenings);
		this.add(formWrapper);
	}
	
	//Clickhandler muss CreateScreening2/2 aufrufen und eingetragene Werte �bernehmen
		class AddScreeningsClickHandler implements ClickHandler{
			
			SurveyCardEdit surveyCardEdit;
			public AddScreeningsClickHandler(SurveyCardEdit surveyCardEdit) {
				this.surveyCardEdit = surveyCardEdit;
			}
			@Override
			
			public void onClick(ClickEvent event) {
			
				cinemaAdministration.getMoviesByName(movieSuggestBox.getText(), new GetMovieCallback(surveyCardEdit));

			}
		}
		
		class GetMovieCallback implements AsyncCallback<Vector<Movie>>{
			SurveyCardEdit surveyCardEdit;
			public GetMovieCallback(SurveyCardEdit surveyCardEdit) {
				// TODO Auto-generated constructor stub
				this.surveyCardEdit = surveyCardEdit;
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Fehler beim Laden des Movies");
			}

			@Override
			public void onSuccess(Vector<Movie> result) {
				// TODO Auto-generated method stub
				movie = result.firstElement();
				//getGroupByNameAndPersonFk
				surveyManagement.getGroupOfPersonByGroupName(1, allGroups.getSelectedItemText(), new GetGroupCallback(surveyCardEdit));

			}
			
		}
		
		class GetGroupCallback implements AsyncCallback<Group>{
			SurveyCardEdit surveyCardEdit;
			public GetGroupCallback(SurveyCardEdit surveyCardEdit) {
				// TODO Auto-generated constructor stub
				this.surveyCardEdit=surveyCardEdit;
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSuccess(Group result) {
				// TODO Auto-generated method stub
				group = result;
				city = cityTextBox.getText();
				
				startDate = new java.sql.Date(startDateBox.getValue().getTime());
				endDate = new java.sql.Date(endDateBox.getValue().getTime());
				
//				surveyCardEdit.showAddScreenings(movie, group, city, startDate, endDate);
				surveyCardEdit.showSurveyCardEdit();
				
			}

		}
//		public void showAddScreenings(Movie movie, Group group, String city, java.sql.Date startDate, java.sql.Date endDate) {
		/*
		 * End of Create Survey Card 1
		 * ------------------------------------------------------------------------------------------------------------------------
		 * Start of Create Survey Card 2 & Edit Survey Card
		 */
		public void showSurveyCardEdit() {
			this.clear();
			this.setStyleName("EditCard");
			screeningRowVector = new Vector<ScreeningRow>();
			formWrapper = new FlowPanel();
			formWrapper.setStyleName("DialogBoxWrapper");
			cardDescription = new Label();
			cardDescription.setStyleName("CardDescription");
			cancelIcon = new Image("/Images/png/007-close.png");
			cancelIcon.setStyleName("CancelIcon");
			cancelIcon.addClickHandler(new CancelClickHandler(this));
			selectedMovie = new Label();
			selectedMovie.setStyleName("TextBoxLabel");
			selectedGroup = new Label ();
			selectedGroup.setStyleName("TextBoxLabel");
			selectedCity = new Label ();
			selectedCity.setStyleName("TextBoxLabel");
			selectedPeriod = new Label ();// + startDate.toString() + " - " + endDate.toString());
			selectedPeriod.setStyleName("TextBoxLabel");
			screeningSelection = new FlowPanel();
			screeningSelection.setStyleName("ScreeningSelection");

			showSelected = new Label("Screenings:");
			
			formWrapper.add(cardDescription);
			formWrapper.add(cancelIcon);
			formWrapper.add(selectedMovie);
			formWrapper.add(selectedGroup);
			formWrapper.add(selectedCity);
			formWrapper.add(selectedPeriod);
			formWrapper.add(screeningSelection);
			
			saveSurvey = new Button("Create Survey");
			saveSurvey.setStyleName("SaveButton");
			
			if(surveyToShow==null){
				cardDescription.setText("Create Survey 2/2");
				selectedMovie.setText("Movie: "+ movie.getName());
				selectedGroup.setText("Group: "+ group.getName());
				selectedPeriod.setText("Screening Period: "+ startDate.toString()+" - "+endDate.toString());
				
				cinemaAdministration.getScreeningsforSurveyCreation(movie, city, startDate, endDate, new GetScreeningsCallback());
				saveSurvey.addClickHandler(new CreateSurveyClickHandler(this));
			}else{
				Window.alert(surveyToShow.toString()+": in EditMode");
				cardDescription.setText("Edit Survey");
				selectedPeriod.setText("Screening Period: "+ surveyToShow.getStartDate().toString()+" - "+surveyToShow.getEndDate().toString());
				surveyManagement.getSurveyEntryBySurveyFK(surveyToShow.getId(), new GetSurveyEntriesCallback());
				surveyManagement.getMovieBySurveyFK(surveyToShow.getId(), new GetMovieBySurveyCallback());
				surveyManagement.getGroupById(surveyToShow.getGroupFK(), new GetGroupOfSurveyCallback());
				deleteLabel = new Label("Delete Survey");
//				deleteLabel.addClickHandler(new DeleteSurveyClickHandler());
				formWrapper.add(deleteLabel);
				saveSurvey.setText("Save");
				saveSurvey.addClickHandler(new UpdateSurveyClickHandler(this));
			}
					
			formWrapper.add(saveSurvey);
			this.add(formWrapper);
		}
		
		public void saveSurvey() {
			Vector<Screening> selectedScreenings = new Vector<Screening>();
			for(ScreeningRow sR: screeningRowVector) {
				if(sR.cb.getValue()==true) {
					selectedScreenings.add(sR.s);
				}
			}
			Window.alert("Selected Screenings: "+Integer.toString(selectedScreenings.size()));
			for(Screening s: selectedScreenings) {
				for(SurveyEntry sE: surveyEntryVector) {
					if(sE.getScreeningFK()==s.getId()) {
						surveyEntryVector.remove(sE);
					}else {
						surveyManagement.createSurveyEntry(s.getId(), surveyToShow.getId(), 1, new SaveSurveyCallback());
					}
				}
			}
			Window.alert("To be deleted: "+Integer.toString(surveyEntryVector.size()));
			for(SurveyEntry sE: surveyEntryVector) {
				surveyManagement.deleteSurveyEntry(sE, new DeleteSurveyEntryCallback());
			}
		}
		
		class DeleteSurveyEntryCallback implements AsyncCallback<Void>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				Window.alert("Deletion successfull");
			}
			
		}
		
		class SaveSurveyCallback implements AsyncCallback<SurveyEntry>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(SurveyEntry result) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		class UpdateSurveyClickHandler implements ClickHandler{
			SurveyCardEdit surveyCardEdit;
			public UpdateSurveyClickHandler(SurveyCardEdit surveyCardEdit) {
				// TODO Auto-generated constructor stub
				this.surveyCardEdit=surveyCardEdit;
			}

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Window.alert("Updated");
				surveyCardEdit.saveSurvey();
				
			}
			
		}
		
		class GetScreeningsCallback implements AsyncCallback<Vector<Screening>>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<Screening> result) {
				// TODO Auto-generated method stub
				
				Vector<Screening> screeningVector = new Vector<Screening>();
				screeningVector.addAll(result);

				Window.alert(Integer.toString(result.size()));
				for (Screening s : result) {	
					Window.alert("ScreeningRow");
					ScreeningRow sr = new ScreeningRow(s);
					screeningRowVector.add(sr);
					screeningSelection.add(sr);
				}
				
		
			}
		}

		
		class ScreeningRow extends FlowPanel{
			
			CheckBox cb;
			Label screeningDescription;
			Screening s;
			
			public ScreeningRow(Screening s) {
				this.s = s;
			}

			public void onLoad() {
				super.onLoad();
				cb = new CheckBox();
				screeningDescription = new Label();
				Window.alert("before cinemacall");
				if(surveyToShow!=null) {
					for(SurveyEntry sE: surveyEntryVector) {
						if(sE.getScreeningFK()==s.getId()) {
							cb.setValue(true);
						}
					}	
				}
				cinemaAdministration.getCinemaById(s.getCinemaFK(), new GetCinemaCallback(this));	
				
				this.add(cb);
				this.add(screeningDescription);
//				screeningSelection.add(this);
			}
	
		}

		class GetCinemaCallback implements AsyncCallback<Cinema>{
			ScreeningRow screeningRow;
			public GetCinemaCallback(ScreeningRow screeningRow) {
				// TODO Auto-generated constructor stub
				this.screeningRow = screeningRow;
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Cinema result) {
				// TODO Auto-generated method stub
				cinema = result;
				screeningRow.screeningDescription.setText(cinema.getName() + ", "+ screeningRow.s.getScreeningDate().toString() + ", "+ screeningRow.s.getScreeningTime());		
			}
		}
		
		class GetGroupOfSurveyCallback implements AsyncCallback<Group>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Group result) {
				// TODO Auto-generated method stub
				group = result;
				selectedGroup.setText("Group: " + group.getName());
			}
		}
		
		class GetSurveyEntriesCallback implements AsyncCallback<Vector<SurveyEntry>>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<SurveyEntry> result) {
				// TODO Auto-generated method stub
				surveyEntryVector = new Vector<SurveyEntry>();
				surveyEntryVector.addAll(result);
//				surveyEntryVector=result;
			}
			
		}
		
		class GetMovieBySurveyCallback implements AsyncCallback<Movie>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Movie result) {
				// TODO Auto-generated method stub
				movie = result;
				selectedMovie.setText("Movie: " + movie.getName());
				cinemaAdministration.getScreeningsforSurveyCreation(movie, surveyToShow.getSelectedCity(), surveyToShow.getStartDate(), surveyToShow.getEndDate(), new GetScreeningsCallback());
			}
		}
	
	
	class AllMoviesCallback implements AsyncCallback<Vector<Movie>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Movie> result) {
			// TODO Auto-generated method stub
			for(Movie m: result) {
				allMovies.add(m.getName());
			}
		}
	}
	
	class GetGroupsByFKCallback implements AsyncCallback<Vector<Group>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Group> result) {
			// TODO Auto-generated method stub
			for(Group g: result) {
				allGroups.addItem(g.getName());
			}
					
		}
		
	}
	

	class CancelClickHandler implements ClickHandler{
		SurveyCardEdit surveyCardEdit;
		public CancelClickHandler(SurveyCardEdit surveyCardEdit) {
			// TODO Auto-generated constructor stub
			this.surveyCardEdit = surveyCardEdit;
		}
		

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			if(parentCard==null) {
				surveyCardEdit.hide();
			}else {
				parentCard.showSurveyCardView(surveyToShow);
				surveyCardEdit.hide();
			}
		}
	}
	
	class CreateSurveyClickHandler implements ClickHandler{
		SurveyCardEdit surveyCardEdit;
		
		public CreateSurveyClickHandler(SurveyCardEdit surveyCardEdit) {
			
			this.surveyCardEdit = surveyCardEdit;
		}
		@Override
		
		public void onClick(ClickEvent event) {
			
			surveyManagement.createSurvey(group.getId(), person.getId(), city, startDate, endDate, new CreateSurveyCallback(surveyCardEdit));
			
			}	
		}

	
	class CreateSurveyCallback implements AsyncCallback<Survey>{
		
		SurveyCardEdit surveyCardEdit;
		public CreateSurveyCallback(SurveyCardEdit surveyCardEdit) {
			// TODO Auto-generated constructor stub
			this.surveyCardEdit=surveyCardEdit;
		}	
		@Override
		public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
			Window.alert("Callback: Survey could not be created.");
		
		}

		@Override
		public void onSuccess(Survey result) {
		
			surveyToShow = result;	
			Window.alert(Integer.toString(screeningRowVector.size()));
			for(ScreeningRow sr: screeningRowVector) {
				if(sr.cb.getValue() == true) {
					surveyManagement.createSurveyEntry(sr.s.getId(), result.getId(), person.getId(), new CreateSurveyEntryCallback());
				}
			}
			Window.alert(Integer.toString(result.getPersonFK()));
			//Irgendwas damit ich sicherstellen kann dass die survey nur beim owner angezeigt wird
			parentCard = new SurveyCard(content, result);
			parentCard.setMovie(movie);
//			parentCard.showSurveyCardView(result);
			content.add(parentCard);
			surveyCardEdit.hide();
		}
	}
	
	
	
	class CreateSurveyEntryCallback implements AsyncCallback<SurveyEntry>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void onSuccess(SurveyEntry result) {
			Window.alert("SurveyEntry Created!");
		}
		
	}


}