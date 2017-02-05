package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.Spine;
import com.github.i49.pulp.api.SpineBuilder;

/**
 * An implementation of {@link SpineBuilder}.
 */
public class SpineBuilderImpl implements SpineBuilder {
	
	private final Map<String, Rendition.Item> items;
	private final List<Spine.Page> pages = new ArrayList<>();
	
	public SpineBuilderImpl(Map<String, Rendition.Item> items) {
		this.items = items;
	}

	@Override
	public SpineBuilder next(String name) {
		return next(name, true);
	}

	@Override
	public SpineBuilder next(String name, boolean linear) {
		Rendition.Item item = items.get(name);
		if (item == null) {
			throw new IllegalArgumentException("Item \"" + name + "\" is not found in the rendition.");
		}
		pages.add(new PageImpl(item, linear));
		return this;
	}
	
	public Spine build() {
		return new SpineImpl(pages);
	}
	
	private static class SpineImpl implements Spine {
	
		private final List<Page> pages;
		
		public SpineImpl(List<? extends Page> pages) {
			this.pages = Collections.unmodifiableList(pages);
		}

		@Override
		public Iterator<Page> iterator() {
			return pages.iterator();
		}

		@Override
		public int size() {
			return pages.size();
		}

		@Override
		public boolean isEmpty() {
			return pages.isEmpty();
		}

		@Override
		public String toString() {
			return pages.toString();
		}
	}

	private static class PageImpl implements Spine.Page {
		
		private final Rendition.Item item;
		private final boolean linear;
		
		public PageImpl(Rendition.Item item, boolean linear) {
			this.item = item;
			this.linear = linear;
		}
		
		@Override
		public String getName() {
			return item.getName();
		}

		@Override
		public PublicationResource getResource() {
			return item.getResource();
		}

		@Override
		public boolean isLinear() {
			return linear;
		}

		@Override
		public String toString() {
			return getName();
		}
	}
}
