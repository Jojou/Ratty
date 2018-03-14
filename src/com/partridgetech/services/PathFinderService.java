package com.partridgetech.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.eztech.util.JavaClassFinder;

/**
 * Class provides service that allows for any extensions of IPathFinder provider to be found at runtime.
 * 
 * @author Zxzx
 *
 */
public class PathFinderService {

    /** Singleton instance of class. */
    private static PathFinderService service;

    /** Loader for finding instances of IPathFinderProvider. */

    /** Path finder service proviers. */
    private final List<IPathFinderProvider> pathFinderServiceProviders = new ArrayList<IPathFinderProvider>();

    /**
     * Constructor.
     */
    private PathFinderService()
    {
        this.loadServiceProviders();
    }
    
    /**
     * Contains logic for loading service providers.
     */
    private void loadServiceProviders()
    {
        final JavaClassFinder classFinder = new JavaClassFinder();
        List<Class<? extends IPathFinderProvider>> matchingClasses = classFinder
                .findAllMatchingTypes(IPathFinderProvider.class);
        
        // Remove actual interface from collection.
        matchingClasses = matchingClasses.stream().filter(clazz -> !clazz.getName().equals(
                IPathFinderProvider.class.getName())).collect(Collectors.toList());
        
        //Add each provider to list for access by interested parties.
        matchingClasses.forEach(clazz -> {
            try
            {
                pathFinderServiceProviders.add(clazz.newInstance());
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        });
    }

    /**
     * Getter.
     * 
     * @return PathFinderService service
     */
    public static synchronized PathFinderService getInstance()
    {
        if (service == null)
        {
            service = new PathFinderService();
        }
        return service;
    }

    /**
     * Get all path finder services.
     * 
     * @return List<IPathFinderProvider>
     */
    public List<IPathFinderProvider> getPathFinderServiceProviders()
    {
        return this.pathFinderServiceProviders;
    }
}
