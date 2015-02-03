package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import java.util.List;

abstract class CommunicationLayer {
	
	// A list of all components expecting the results of a search query
	List<? extends SearchListener> searchListeners;
	
	// Adds a listener to the list of searchListeners to allow them to
	// receive results of a query
	abstract void registerListener(SearchListener s);
	
	// Creates a Search from the current values of fields in the GUI
	abstract Search createSearch();
	
	// Sends a Search to the database
	abstract void sendSearch();
	
	// Polls for a SearchResult from the database, then sends it on to
	// the members of searchListeners by calling getSearchResult on each
	// member
	abstract void getResult();
	
}
