package com.partridgetech.model.observer;

import java.util.List;


/**
 * Serves same function as Observable but does not require extension and provides T type.
 * 
 * @author Zxzx
 *
 * @param <T> data to share
 */
public interface IObservable<T>{
    
    /**
     * Notify observers that something worthwhile has happened.
     */
    void notifyAllObservers();
    
    /**
     * Add observer.
     * 
     * @param observer
     */
    void addObserver(final IObserver<T> observer);
    
    /**
     * Add observers.
     * 
     * @param observers
     */
    void addObservers(final List<IObserver<T>> observers);
    
    /**
     * Remove all observers.
     */
    void removeAllObservers();
}
