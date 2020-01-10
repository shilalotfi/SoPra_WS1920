package de.hdm.SoPra_WS1920.client.gui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

import de.hdm.SoPra_WS1920.shared.bo.Group;
import de.hdm.SoPra_WS1920.shared.bo.Movie;
import de.hdm.SoPra_WS1920.shared.bo.Screening;
import de.hdm.SoPra_WS1920.shared.bo.Survey;

public class SurveyCardResultView extends FlowPanel {
	
	Label group;
	Label movie;
	Label status;
	Label participations;
	Label voted;
	Button results;
	
	Survey surveyToShow;
	Movie movieOfSurvey;
	Group groupOfSurvey;
	Screening screeningOfSurvey;
	
	
	
	SurveyCard parentCard;
	
	
	public SurveyCardResultView(SurveyCard parentCard, Survey surveyToShow) {
		this.parentCard = parentCard;
		this.surveyToShow = surveyToShow;
	}
	
	public void onLoad() {
		super.onLoad();
		//surveyEntrysOfSurvey = SurveyManagementImpl.getSurveyEntryBySurveyFK(survey.getId)
		//screeningOfSurvey = CinemaAdminImpl.getScreeningById(surveyentryOfSurvey.getScreeningFK)
		//movieOfSurvey = cinemaAdminImpl.getMovieById(screeningOfSurvey.getMovieFK)
		movie = new Label("Joker");
		movie.setStyleName("CardViewTitle");
		
		//groupOfSurvey = SurveyManagementImpl.getGroupbyId(surveyToShow.getGroupFK)
		group = new Label("Friends");
		group.setStyleName("CardViewSubtitle");
		
		status = new Label("Status: Closed");
		status.setStyleName("CardViewParagraph");
		
		voted = new Label("Voted:");
		voted.setStyleName("CardViewParagraph");
		
		//  / groupmembers = SurveyManagementImpl.countGroupMembers(groupOfSurvey.getId)
		participations = new Label("4/6 participations");
		participations.setStyleName("CardViewParagraph");
		
		results = new Button("Results");
		results.setStyleName("SaveButton");
		results.addClickHandler(new ResultClickHandler());
	
		this.add(movie);
		this.add(group);
		this.add(status);
		this.add(voted);
		this.add(participations);
		this.add(results);
	
	}
	class ResultClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			parentCard.showSurveyCardResult(surveyToShow);
		}
	}
	
	

}
