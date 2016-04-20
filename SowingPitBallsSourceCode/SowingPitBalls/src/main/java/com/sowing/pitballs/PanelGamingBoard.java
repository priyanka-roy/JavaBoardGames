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
import com.sowing.pitballs.model.Player;

public class PanelGamingBoard extends Panel {

	private GameBoard gameBoard;
	private static final long serialVersionUID = -49325349325336135L;

	private Label label1;
	private Label label2;
	private Label winnerPly1;
	private Label winnerPly2;
	private Player currentPlayer;
	private List<Pit> pits;
	private ListDataProvider<Pit> listDataProvider;
	private DataView<Pit> dataView;
	private WebMarkupContainer theContainer;
	
	private Form<?> form2;

	public PanelGamingBoard(String id) {
		super(id);
		gameBoard = new GameBoard();
		currentPlayer = gameBoard.getPlayers()[0];
		Label label = new Label("label", "Capture Pits and the Stones Inside it  ");
		add(label);


		pits = gameBoard.getBoardPits();
		listDataProvider = new ListDataProvider<Pit>(pits);
		theContainer = new WebMarkupContainer("theContainer");
		//the follwings bunch of labels are needed to display which player comes next 
		//and what number he should input,since I have not added validation
		//I expect the player would follow the instructions on the web page,saying that
		//player 1 should input values from 0 to 5 and Player 2 from 7-12
		label1 = new Label("label1", "Enter A VALID Pit NUMBER to select a pit position BETWEEN  0-5 Player 1  :" + gameBoard.getPlayers()[0].getName());
		label1.setVisible(false);
		label2 = new Label("label2", "Enter A VALID Pit NUMBER to select a pit position  BETWEEN  7-12 Player 2  :" + gameBoard.getPlayers()[1].getName());
		label2.setVisible(false);
		winnerPly1 = new Label("winnerPly1", "Player one  You are the Winner Congrats : "+ gameBoard.getPlayers()[0].getName());
		winnerPly1.setVisible(false);
		winnerPly2 = new Label("winnerPly2", "Player Two  You are the Winner Congrats : "+ gameBoard.getPlayers()[1].getName());
		winnerPly2.setVisible(false);
		
		// TODO-its disabled for now
		stopGame();
		//receives the player's input
		final TextField<String> inputPlayer = new TextField<String>("inputPlayer",new PropertyModel<String>(gameBoard, "pitNo"));
		inputPlayer.setOutputMarkupId(true);
		Form<?> form = startGame(inputPlayer);
		add(form);
		repaintGameBoardWithNewValues();
		this.setOutputMarkupId(true);
	}

	private Form<?> startGame(final TextField<String> inputPlayer) {
		Form<?> form = new Form("form");
		form.add(inputPlayer);
		form.add(new AjaxButton("startGame") {

		private static final long serialVersionUID = 1L;

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onAfterSubmit(target, form);
				if (target != null) {
					String textPosition = form.getRequest().getRequestParameters().getParameterValue("inputPlayer").toString();
					//the heart beat of the application is this method.
					gameBoard.moveAndPlay(getCurrentPlayer(), Integer.parseInt(textPosition));
					checkCurrentPlayer();
					target.add(theContainer);

				}

			}

		
		});
		return form;
	}

	/**
	 * Checks who is the next player by a call to the backend 
	 */
	private void checkCurrentPlayer() {
		if (gameBoard.getNextPlayer().getPlayerNo() == 1) {
			setCurrentPlayer(gameBoard.getNextPlayer());
			label1.setVisible(true);
			label2.setVisible(false);

		} else if (gameBoard.getNextPlayer().getPlayerNo() == 2) {
			setCurrentPlayer(gameBoard.getNextPlayer());
			label1.setVisible(false);
			label2.setVisible(true);

		}
		
		displayWinner();

	}
	
	
	/**
	 * Simply Displays the winner by making a call to the backend
	 * which is reponsible to determine the winner and set it to 1 or 2
	 * depending on the player who wins.
	 */
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

	public void repaintGameBoardWithNewValues() {

		dataView = new DataView<Pit>("rows", listDataProvider) {

			private static final long serialVersionUID = 1L;

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
		// generate a markup-id so the contents can be updated through an AJAX call
		theContainer.setOutputMarkupId(true);
		theContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)));
		// add all the components that needs to be updated via ajax call.
		theContainer.add(label1);
		theContainer.add(label2);
		theContainer.add(winnerPly1);
		theContainer.add(winnerPly2);
		theContainer.add(dataView);
		theContainer.add(form2);
		// finally add the container to the page
		add(theContainer);

	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	// TODO stop button not working now...need to fix it
	private void stopGame() {

		form2 = new Form("form2");
		AjaxButton stopGame = new AjaxButton("stopGame") {

		private static final long serialVersionUID = 8168612876483526376L;

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
