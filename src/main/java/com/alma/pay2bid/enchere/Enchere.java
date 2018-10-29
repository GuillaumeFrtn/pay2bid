package com.alma.pay2bid.enchere;

import java.util.ArrayList;
import java.util.Collection;

import com.alma.pay2bid.client.observable.IBidSoldObservable;
import com.alma.pay2bid.client.observable.INewAuctionObservable;
import com.alma.pay2bid.client.observable.INewPriceObservable;
import com.alma.pay2bid.client.observable.ITimerObservable;
import com.alma.pay2bid.client.observer.IBidSoldObserver;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;
import com.alma.pay2bid.client.observer.ITimerObserver;

public class Enchere implements IBidSoldObservable, INewAuctionObservable, INewPriceObservable , ITimerObservable{

    private transient Collection<ITimerObserver> newTimerObservers = new ArrayList<ITimerObserver>();
    private transient Collection<IBidSoldObserver> bidSoldObservers = new ArrayList<IBidSoldObserver>();
    private transient Collection<INewAuctionObserver> newAuctionObservers = new ArrayList<INewAuctionObserver>();
    private transient Collection<INewPriceObserver> newPriceObservers = new ArrayList<INewPriceObserver>();
	
	
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
	
}
