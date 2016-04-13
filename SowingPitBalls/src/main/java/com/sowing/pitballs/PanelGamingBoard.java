package com.sowing.pitballs;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;

import com.sowing.pitballs.model.GameBoard;
import com.sowing.pitballs.model.Pit;

public class PanelGamingBoard extends Panel {

	private GameBoard gameBoard;
	private static final long serialVersionUID = -49325349325336135L;

	private Label label1;
	private Label label2;
	private Label winnerPly1;
	private Label winnerPly2;
	private int currentPlayer = 1;
	// method loadPersons is defined elsewhere
	private List<Pit> pits;
	private ListDataProvider<Pit> listDataProvider;
	private DataView<Pit> dataView;
	private WebMarkupContainer theContainer;
	
	private Form<?> form2;

	public PanelGamingBoard(String id) {
		super(id);
		gameBoard = new GameBoard();
		Label label = new Label("label", "Capture Pits And Balls  ");
		add(label);


		pits = gameBoard.getBoardPits();
		listDataProvider = new ListDataProvider<Pit>(pits);
		theContainer = new WebMarkupContainer("theContainer");
		
		label1 = new Label("label1", "Player One Active   Enter A VALID NUMBER BETWEEN  0-5 ");
		label1.setVisible(false);
		label2 = new Label("label2", "Player Two Active   Enter A VALID NUMBER BETWEEN 7-12");
		label2.setVisible(false);
		winnerPly1 = new Label("winnerPly1", "Player one  You are the Winner");
		winnerPly1.setVisible(false);
		winnerPly2 = new Label("winnerPly2", "Player Two  You are the Winner");
		winnerPly2.setVisible(false);
		
		// TODO-its disabled for now
		stopGame();

		final TextField<String> inputPlayer = new TextField<String>("inputPlayer",new PropertyModel<String>(gameBoard, "pitNo"));
		inputPlayer.setOutputMarkupId(true);
		Form<?> form = startGame(inputPlayer);
		add(form);
		updateBoard();
		this.setOutputMarkupId(true);
	}

	private Form<?> startGame(final TextField<String> inputPlayer) {
		Form<?> form = new Form("form");
		form.add(inputPlayer);
		form.add(new AjaxButton("startGame") {

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onAfterSubmit(target, form);
				if (target != null) {
					String textPosition = form.getRequest().getRequestParameters().getParameterValue("inputPlayer").toString();
					
					gameBoard.moveAndPlay(getCurrentPlayer(), Integer.parseInt(textPosition));
					checkCurrentPlayer();
					target.add(theContainer);

				}

			}

		
		});
		return form;
	}

	
	private void checkCurrentPlayer() {
		if (gameBoard.getNextPlayer() == 1) {
			setCurrentPlayer(1);
			label1.setVisible(true);
			label2.setVisible(false);

		} else if (gameBoard.getNextPlayer() == 2) {
			setCurrentPlayer(2);
			label1.setVisible(false);
			label2.setVisible(true);

		}
		
		displayWinner();

	}
	
	

	private void displayWinner() {
		gameBoard.calculateScore();
		if (gameBoard.getWinner() == 1 || gameBoard.getWinner() == 2) {
			int player = gameBoard.getWinner();
			if (player == 1) {
				winnerPly1.setVisible(true);
				winnerPly2.setVisible(false);
				label1.setVisible(false);
				label2.setVisible(false);
			} else {
				winnerPly1.setVisible(false);
				winnerPly2.setVisible(true);
				label1.setVisible(false);
				label2.setVisible(false);
			}

		}
	}

	@SuppressWarnings("unchecked")
	public void updateBoard() {

		dataView = new DataView<Pit>("rows", listDataProvider) {

			@Override
			protected void populateItem(Item<Pit> item) {
				Pit pit = item.getModelObject();
				RepeatingView repeatingView = new RepeatingView("dataRow");

				repeatingView.add(new Label(repeatingView.newChildId(), pit.getPitNumber()));
				repeatingView.add(new Label(repeatingView.newChildId(), pit.getStones()));
				item.add(repeatingView);
			}
		};

		// encapsulate the ListView in a WebMarkupContainer in order for it to
		// update
		theContainer = new WebMarkupContainer("theContainer");
		// generate a markup-id so the contents can be updated through an AJAX
		// call
		theContainer.setOutputMarkupId(true);
		theContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)));
		// add all the components that need to be updated via ajax call.
		theContainer.add(label1);
		theContainer.add(label2);
		theContainer.add(winnerPly1);
		theContainer.add(winnerPly2);
		theContainer.add(dataView);
		theContainer.add(form2);
		// finally add the container to the page
		add(theContainer);

	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	// TODO stop button not working now...need to fix it
	private void stopGame() {

		form2 = new Form("form2");
		AjaxButton stopGame = new AjaxButton("stopGame") {

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form2) {
				super.onAfterSubmit(target, form2);
				if (target != null) {
					// gameBoard.setTerminate(true);
					target.add(form2);

				}

			}
		};
		form2.add(stopGame);
		stopGame.setEnabled(false);

	}

}
