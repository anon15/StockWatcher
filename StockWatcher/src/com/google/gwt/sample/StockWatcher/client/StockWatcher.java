package com.google.gwt.sample.StockWatcher.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StockWatcher implements EntryPoint {
		
	private VerticalPanel pnlMain;
	private HorizontalPanel pnlAddStockWatch;
	private Button btnAddStock;
	private TextBox txtAddStock;
	private Label lblTimeStamp;
	private FlexTable tblStockWatch;
	
	private ArrayList<String> stocks;
	
	
	
	public void onModuleLoad() {
		init();
		//get the body tag and add the main panel to it
		RootPanel.get().add(pnlMain);
	}
	
	/**
	 * initialize the widgets
	 */
	private void init(){
		lblTimeStamp = new Label();
		btnAddStock = new Button("Add");
		btnAddStock.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addStock();
			}
		});
		txtAddStock = new TextBox();
		txtAddStock.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					addStock();
				}
				
			}
		});
		
		pnlAddStockWatch = new HorizontalPanel();
		pnlAddStockWatch.add(txtAddStock);
		pnlAddStockWatch.add(btnAddStock);
		
		//create table, add columns
		tblStockWatch = new FlexTable();
		tblStockWatch.setText(0, 0, "Symbol");
		tblStockWatch.setText(0, 1, "Price");
		tblStockWatch.setText(0, 2, "Change");
		tblStockWatch.setText(0, 3, "Remove");
		
		//add widgets to panel
		pnlMain = new VerticalPanel();
		pnlMain.add(tblStockWatch);
		pnlMain.add(pnlAddStockWatch);
		pnlMain.add(lblTimeStamp);
		
		stocks = new ArrayList<>();
		
		txtAddStock.setFocus(true);
		
	}
	
	/**
	 * add users input (stock) to stock table
	 */
	private void addStock(){
		//get user's input from TextBox
		final String usersInput = txtAddStock.getText().toUpperCase().trim();
		btnAddStock.setFocus(true);
		
		//check validation
		if(!this.validateUsersStockInput(usersInput)){
			Window.alert("Your input is not valid");
		}
		else{
			stocks.add(usersInput);
			int rowNumber = this.tblStockWatch.getRowCount()+1;
			this.tblStockWatch.setText(rowNumber, 0, usersInput);
			Button btnRemove = new Button("X");
			btnRemove.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					int removeIndex = stocks.indexOf(usersInput);
					//remove stock from list
					stocks.remove(removeIndex);
					//remove stock from table
					tblStockWatch.removeRow(removeIndex+1);
					
				}
			});
			
			//add Remove button to row for deleting the stock
			this.tblStockWatch.setWidget(rowNumber, 3, btnRemove);
			
			/*
			//check if there is stock like users input in the list
			if(!stocks.contains(usersInput)){
				addStock(usersInput);
			}
			else{
				Window.alert("Stock already available in the list");
			}*/
		}
		
		//TODO add stock to table
		//TODO add remove button to row
		//TODO get the stock price
	}

	/**
	 * Checks if user's new stock input value is valid
	 * @param user's stock value input
	 * @return true if input is valid, otherwise false
	 */
	private boolean validateUsersStockInput(String input){
		if(!input.matches("^[0-9A-Z\\.]{1,10}$")){
			return false;
		}
		else{
			return true;
		}
		
		//ToDo check if input already exists
	}
}
