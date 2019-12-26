package de.hdm.SoPra_WS1920.client.gui.Admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import de.hdm.SoPra_WS1920.shared.bo.Cinema;

public class CinemaCardView extends FlowPanel{

	Label name;
	Label cinemaChain;
	Label street;
	Label streetNr;
	Label zipCode;
	Label city;
	Button edit;
	Cinema cinemaToShow;
	CinemaCard parentCard;
	Image editIcon;
	FlowPanel streetRow;
	FlowPanel cityRow;
	
	public CinemaCardView(CinemaCard parentCard, Cinema cinemaToShow) {
		this.parentCard=parentCard;
		this.cinemaToShow=cinemaToShow;
	}

	public void onLoad() {
		super.onLoad();
		this.setStyleName("view");
		streetRow = new FlowPanel();
		streetRow.setStyleName("streetrow-inline");
		cityRow = new FlowPanel();
		cityRow.setStyleName("cityrow-inline");
		
		name = new Label(cinemaToShow.getName());
		name.setStyleName("title");
		cinemaChain = new Label("Cinema Chain");
		cinemaChain.setStyleName("subtitle");
		street = new Label(cinemaToShow.getStreet());
		street.setStyleName("street");
		streetNr = new Label(cinemaToShow.getStreetNo());
		streetNr.setStyleName("street");
		zipCode = new Label(cinemaToShow.getzipCode());
		zipCode.setStyleName("city");
		city = new Label(cinemaToShow.getCity());
		city.setStyleName("city");
		
		edit=new Button("");
		edit.setStyleName("invisibleButton");
		editIcon = new Image("/Images/png/006-pen.png");
		editIcon.setStyleName("icon");
		editIcon.addClickHandler(new EditClickHandler());
		
		streetRow.add(street);
		streetRow.add(streetNr);
		cityRow.add(zipCode);
		cityRow.add(city);
		this.add(name);
		this.add(cinemaChain);
		this.add(streetRow);		
		this.add(cityRow);
		this.add(edit); //Without this, the clickable icon doesn't work
		this.add(editIcon);
		
	}
	class EditClickHandler implements ClickHandler{
		

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			parentCard.showCinemaCardEdit(cinemaToShow);
		}
		
	}
}
