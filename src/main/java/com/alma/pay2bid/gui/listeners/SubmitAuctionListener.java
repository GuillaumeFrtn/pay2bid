package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.gui.AuctionInput;
import com.alma.pay2bid.gui.AuctionView;
import com.alma.pay2bid.gui.ClientGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An ActionListener that submit a new auction to the server
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
 */
public class SubmitAuctionListener implements ActionListener{
    private AuctionView auction;
    private ClientGui gui;
    private AuctionInput input;

    public SubmitAuctionListener(ClientGui gui, AuctionInput input) {
        this.gui = gui;
        this.input = input;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            // send the new auction to the server through the client
            AuctionBean a = new AuctionBean(Integer.parseInt(input.getAuctionPrice()), input.getAuctionName(), input.getDescription(), gui.getClient().getName(), null);
            // la nouvelle enchère récupère la liste de tous les clients via le serveur, puis les notifie de sa présence

            gui.submit(a);


            // close the menu & refresh the status label
            input.hideFrame();
            input.getStatusLabel().setText("New auction sent...");

        } catch(Exception e) {
            input.getStatusLabel().setText("Price must be an Integer");
        }
    }
}
