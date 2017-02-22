package com.github.i49.pulp.api;

import java.nio.file.Path;
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
	 * Creates an instance of {@link PublicationReader} that can read a publication from the given input stream.
	 * @param path the location where the file to be read is located.
	 * @return created publication reader.
	 * @exception NullPointerException if given {@code path} is {@code null}.
	 * @exception EpubException if API implementation is not found.
	 */
	public static PublicationReader createReader(Path path) {
		return createReaderFactory().createReader(path);
	}
	
	/**
	 * Creates an instance of {@link PublicationReaderFactory} that can produce one or more {@link PublicationReader}s.
	 * @return created publication reader factory.
	 * @exception EpubException if API implementation is not found.
	 */
	public static PublicationReaderFactory createReaderFactory() {
		return getProvider().createReaderFactory();
	}

	/**
	 * Creates an instance of {@link PublicationWriter} that can write a publication to the given output stream.
	 * @param path the location where a publication will be stored by the writer.
	 * @return created publication writer.
	 * @exception NullPointerException if given {@code path} is {@code null}.
	 * @exception EpubException if API implementation is not found.
	 */
	public static PublicationWriter createWriter(Path path) {
		return createWriterFactory().createWriter(path);
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
