package com.alma.pay2bid.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.client.observable.IBidSoldObservable;
import com.alma.pay2bid.client.observable.INewAuctionObservable;
import com.alma.pay2bid.client.observable.INewPriceObservable;
import com.alma.pay2bid.client.observable.ITimerObservable;
import com.alma.pay2bid.client.observer.IBidSoldObserver;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;
import com.alma.pay2bid.client.observer.ITimerObserver;

import java.util.*;

/**
 * AuctionBean represent an auction, ie an item sold by a client
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
 */
public class AuctionBean implements IBean, IBidSoldObservable, INewAuctionObservable, INewPriceObservable , ITimerObservable {
    private UUID uuid;
    private int price;
    private String name;
    private String description;
    private String vendeur;
    List<IClient> clients;
    
    private transient Collection<ITimerObserver> timerObservers = new ArrayList<ITimerObserver>();
    private transient Collection<IBidSoldObserver> bidSoldObservers = new ArrayList<IBidSoldObserver>();
    private transient Collection<INewAuctionObserver> newAuctionObservers = new ArrayList<INewAuctionObserver>();
    private transient Collection<INewPriceObserver> newPriceObservers = new ArrayList<INewPriceObserver>();
    
    
    public AuctionBean(int price, String name, String description, String vendeur, List<IClient> clients) {
    	if (price > 0) this.price = price;
    	else this.price = 0;
        this.name = name;
        this.description = description;
        this.vendeur = vendeur;
        this.clients = clients;
        notifyNewAuctionObserver();
    }

    
    
		
    @Override
    public boolean addNewPriceObserver(INewPriceObserver observer) {
        return newPriceObservers.add(observer);
    }

    @Override
    public boolean removeNewPriceObserver(INewPriceObserver observer) {
        return newPriceObservers.remove(observer);
    }

    @Override
    public boolean addBidSoldObserver(IBidSoldObserver observer) {
        return bidSoldObservers.add(observer);
    }

    @Override
    public boolean removeBidSoldObserver(IBidSoldObserver observer) {
        return bidSoldObservers.remove(observer);
    }

    @Override
    public boolean addNewAuctionObserver(INewAuctionObserver observer) {
        return newAuctionObservers.add(observer);
    }

    @Override
    public boolean removeNewAuctionObserver(INewAuctionObserver observer) {
        return newAuctionObservers.remove(observer);
    }
    

    @Override
    public boolean addTimerObserver(ITimerObserver observer) {
        return newTimerObservers.add(observer);
    }

    @Override
    public boolean removeTimerObserver(ITimerObserver observer) {
        return newTimerObservers.remove(observer);
    }
    
    @Override
    public UUID getUUID() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getVendeur() {
		return vendeur;
	}

	public void setVendeur(String vendeur) {
		this.vendeur = vendeur;
	}

	@Override
	public void notifyTimerObserver() {
		for(ITimerObserver o : timerObservers){
            o.updateTimer(timeString);
        }
	}

	@Override
	public void notifyNewPriceObserver() {
		for (INewPriceObserver observer : newPriceObservers) {
            observer.updateNewPrice(uuid, price);
        }
	}

	@Override
	public void notifyNewAuctionObserver() {
		for (INewAuctionObserver observer : newAuctionObservers) {
            observer.updateNewAuction(this);
        }
	}

	@Override
	public void notifyBidSoldObserver() {
		for (IBidSoldObserver observer : bidSoldObservers) {
        	if(winner != null)
        		observer.updateBidSold(winner);
        }
	}
}
