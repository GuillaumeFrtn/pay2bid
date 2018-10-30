package com.alma.pay2bid.client;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.gui.ClientGui;
import com.alma.pay2bid.server.IServer;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * The Interface of a client in our model
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
 */
public interface IClient extends Remote, Serializable {
    /**
     * @param auction
     * @throws RemoteException
     */
    void newAuction(AuctionBean auction) throws RemoteException;

    /**
     * @param auction
     * @throws RemoteException
     */
    void submit(AuctionBean auction, IServer server) throws RemoteException;

    /**
     * @param buyer
     * @throws RemoteException
     */
    void bidSold(IClient buyer, IServer server) throws RemoteException;

    /**
     * @param auctionID
     * @param price
     * @throws RemoteException
     */
    void newPrice(UUID auctionID, int price) throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    String getName() throws RemoteException;
    
    void setName(String name) throws RemoteException;

    ClientState getState() throws RemoteException;

    void setState(ClientState newState) throws RemoteException;

    void setEstVendeur(boolean v) throws RemoteException;

	boolean getEstVendeur();
}
