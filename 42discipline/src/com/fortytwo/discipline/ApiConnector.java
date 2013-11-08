package com.fortytwo.discipline;

public class ApiConnector {
	
    private static class SingletonHolder { 
        public static final ApiConnector SINGLETON = new ApiConnector();
    }
	
    public static ApiConnector getSingleton(){
    	return SingletonHolder.SINGLETON;
    } 
    
    
    
}
