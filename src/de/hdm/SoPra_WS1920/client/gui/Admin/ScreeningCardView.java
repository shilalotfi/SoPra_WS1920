package de.hdm.SoPra_WS1920.client.gui.Admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.hdm.SoPra_WS1920.shared.bo.Cinema;
import de.hdm.SoPra_WS1920.shared.bo.Movie;
import de.hdm.SoPra_WS1920.shared.bo.Screening;

public class ScreeningCardView extends FlowPanel{
		
	Label movie;
	Label cinema;
	Label date;
	Label time;
	Button edit;
	Image editIcon;
	
	Screening screeningToShow;
	Movie movieOfScreening;
	Cinema cinemaOfScreening;
	
	ScreeningCard parentCard;

	public ScreeningCardView(ScreeningCard screeningCard, Screening screening) {
		// TODO Auto-generated constructor stub
		this.parentCard= screeningCard;
		this.screeningToShow = screening;
	}

	public void onLoad() {
		super.onLoad();
		
		//movieOfScreening = cinemaAdminImpl.getMovieById(screening.getMovieFK)
		movie = new Label("Joker");//parameter would be movieOfScreening.getName();
		movie.setStyleName("Title");
		//cinemaOfScreening= cinemaAdminImpl.getCinemaById(screening.getCinemaFK)
		cinema = new Label();//parameter would be cinemaOfScreening.getName();
		cinema.setStyleName("Subtitle");
		date = new Label(screeningToShow.getScreeningDate().toString());
		date.setStyleName("Paragraph");
		time = new Label(screeningToShow.getScreeningTime().toString());
		time.setStyleName("Paragraph");
		
		edit=new Button("");
		edit.setStyleName("InvisibleButton");
		editIcon = new Image("/Images/png/006-pen.png");
		editIcon.setStyleName("Icon");
		editIcon.addClickHandler(new EditClickHandler());
		
		this.add(movie);
		this.add(cinema);
		this.add(date);
		this.add(time);
		this.add(edit);
		this.add(editIcon);
	}
	class EditClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			parentCard.showScreeningCardEdit(screeningToShow);
		}
		
	}
}