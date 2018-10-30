package com.alma.pay2bid.bean;

import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.client.observable.IBidSoldObservable;
import com.alma.pay2bid.client.observable.INewAuctionObservable;
import com.alma.pay2bid.client.observable.INewPriceObservable;
import com.alma.pay2bid.client.observable.ITimerObservable;
import com.alma.pay2bid.client.observer.IBidSoldObserver;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;
import com.alma.pay2bid.client.observer.ITimerObserver;

import java.rmi.RemoteException;
import java.util.*;

/**
 * AuctionBean represent an auction, ie an item sold by a client
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
 */
public class AuctionBean implements IBean, IBidSoldObservable, INewAuctionObservable, INewPriceObservable , ITimerObservable {
	
	/**
     * A timer used to measure time between rounds
     */
	private class TimerManager extends TimerTask {
        private String timeString;
        private long time = TIME_TO_RAISE_BID;

        public TimerManager(String timeMessage){
            this.timeString = timeMessage;
        }

        @Override
        public void run() {
            try {
                time -=TIME_TO_REFRESH;
                timeString = Long.toString(time/1000);
                if(time == 0) {
                		if (!Client.this.estVendeur) { // utilité ?
                			server.timeElapsed(Client.this); // timeElapsed à deplacer ici
                		}
                } else {
                	// à remplacer par notify
                    for(ITimerObserver o : server.getCurrentAuction().getTimerObserver()){
                        o.updateTimer(timeString);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getTimeString() {
            return timeString;
        }

        public void setTimeString(String timeString) {
            this.timeString = timeString;
        }
    }
	
    private UUID uuid;
    private int price;
    private String name;
    private String description;
    private String vendeur;
    List<IClient> clients;
    private transient Timer timer;
    private String timeElapsed;
    
    private static final long TIME_TO_RAISE_BID = 30000;
    private static final long TIME_TO_REFRESH = 1000;
    
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
