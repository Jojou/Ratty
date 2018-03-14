package com.partridgetech.model.observer;

/**
 * Custom observer interface with greater type safety than java.lang version.  Interacts with IObservable that does
 * not force extension of Observable.
 * 
 * @author Zxzx
 *
 * @param <T> The data there is interest in sharing.
 */
public interface IObserver<T> {

    /**
     * Method to allow for data to be pushed to interested party.
     * 
     * @param data to share.
     */
    void update(T data);   

}
