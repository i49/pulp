package com.github.i49.pulp.api;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.github.i49.pulp.api.spi.EpubProvider;

/**
 * Factory class for creating EPUB processing objects.
 * This class is the central interface of the API. 
 * 
 * <p>The following example shows how to create a new publication:</p>
 * <pre><code>
 * // Creates a new publication.
 * Publication publication = Epub.createPublication();
 * </code></pre>
 * 
 * <p>All the methods of this class are safe for use by multiple concurrent threads.</p>
 */
public final class Epub {

	/**
	 * Creates an empty publication.
	 * @return an empty publication.
	 * @exception EpubException if API implementation is not found.
	 */
	public static Publication createPublication() {
		return getProvider().createPublication();
	}
	
	/**
	 * Creates an instance of {@link PublicationWriter} that can write a publication to the given output stream.
	 * @param stream the stream to which the writer will writes a publication.
	 * @return created publication writer.
	 * @exception NullPointerException if given {@code stream} is {@code null}.
	 * @exception EpubException if API implementation is not found.
	 */
	public static PublicationWriter createWriter(OutputStream stream) {
		return createWriterFactory().createWriter(stream);
	}
	
	/**
	 * Creates an instance of {@link PublicationWriterFactory} that can produce one or more {@link PublicationWriter}s.
	 * @return created publication writer factory.
	 * @exception EpubException if API implementation is not found.
	 */
	public static PublicationWriterFactory createWriterFactory() {
		return getProvider().createWriterFactory();
	}

	/**
	 * Thread-local service loader.
	 */
	private static class ThreadLocalLoader extends ThreadLocal<ServiceLoader<EpubProvider>> {

		@Override
		protected ServiceLoader<EpubProvider> initialValue() {
			return ServiceLoader.load(EpubProvider.class);
		}
	}
	
	private static final ThreadLocal<ServiceLoader<EpubProvider>> threadLoader = new ThreadLocalLoader();
	
	/**
	 * Returns the service provider of {@link EpubProvider}. 
	 * @return found service provider.
	 */
	private static EpubProvider getProvider() {
		Iterator<EpubProvider> it = threadLoader.get().iterator();
		if (it.hasNext()) {
			return it.next();
		}
		throw new EpubException("Implementation of EpubProvider is not found.");
	}
	
	private Epub() {
	}
}
