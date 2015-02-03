package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

public interface SearchListener {
	
	/*
	* This is an interface implemented by any components that wish to receive search
	* results from the communication layer.
	*/	
	/*
	* Called by the communication layer when a search result is available. This function
	* should dictate how this result is handled.
	*/	
	abstract SearchResult getSearchResult(SearchResult s);

}
