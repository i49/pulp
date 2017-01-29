package com.github.i49.pulp.api;

public interface ContentDocument extends PublicationResource {
	
	boolean isLinear();
	
	void setLinear(boolean linear);
}
