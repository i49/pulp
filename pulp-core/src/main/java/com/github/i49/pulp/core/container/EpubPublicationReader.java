package com.github.i49.pulp.core.container;

import java.io.IOException;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;

public class EpubPublicationReader implements PublicationReader {

	private final ContainerLoader loader;
	
	public EpubPublicationReader(ContainerLoader loader) {
		this.loader = loader;
	}

	@Override
	public Publication read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		try {
			this.loader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
