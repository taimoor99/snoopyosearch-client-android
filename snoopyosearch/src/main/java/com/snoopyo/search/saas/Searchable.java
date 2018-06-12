package com.snoopyo.search.saas;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Map;

/***
 * A searchable source of data
 */
public abstract class Searchable {
	/**
	 * Search inside this index (asynchronously).
	 *
	 * @param query             Search parameters. May be null to use an empty query.
	 * @param requestOptions    Request-specific options.
	 * @param completionHandler The listener that will be notified of the request's outcome.
	 * @return A cancellable request.
	 */
	public abstract Request searchAsync(@Nullable Query query, @Nullable final RequestOptions requestOptions, @Nullable CompletionHandler completionHandler);

	/**
	 * Search inside this index (asynchronously).
	 *
	 * @param query             Search parameters. May be null to use an empty query.
	 * @param completionHandler The listener that will be notified of the request's outcome.
	 * @return A cancellable request.
	 */
	public Request searchAsync(@Nullable Query query, @Nullable CompletionHandler completionHandler) {
		return searchAsync(query, /* requestOptions: */ null, completionHandler);
	}

	/**
	 * Perform a search with disjunctive facets, generating as many queries as number of disjunctive facets (helper).
	 *
	 * @param query             The query.
	 * @param disjunctiveFacets List of disjunctive facets.
	 * @param refinements       The current refinements, mapping facet names to a list of values.
	 * @param requestOptions    Request-specific options.
	 * @param completionHandler The listener that will be notified of the request's outcome.
	 * @return A cancellable request.
	 */
	public Request searchDisjunctiveFacetingAsync(@NonNull Query query, @NonNull final Collection<String> disjunctiveFacets, @NonNull final Map<String, ? extends Collection<String>> refinements, @Nullable final RequestOptions requestOptions, @NonNull final CompletionHandler completionHandler) {
		throw new UnsupportedOperationException("make sure to override searchDisjunctiveFacetingAsync for custom backend");
	}

	/**
	 * Search for some text in a facet values, optionally restricting the returned values to those contained in objects matching other (regular) search criteria.
	 *
	 * @param facetName      The name of the facet to search. It must have been declared in the index's `attributesForFaceting` setting with the `searchable()` modifier.
	 * @param facetText      The text to search for in the facet's values.
	 * @param query          An optional query to take extra search parameters into account. There parameters apply to index objects like in a regular search query. Only facet values contained in the matched objects will be returned
	 * @param requestOptions Request-specific options.
	 * @param handler        A Completion handler that will be notified of the request's outcome.
	 * @return A cancellable request.
	 */
	public Request searchForFacetValuesAsync(@NonNull String facetName, @NonNull String facetText, @Nullable Query query, @Nullable final RequestOptions requestOptions, @NonNull final CompletionHandler handler) {
		throw new UnsupportedOperationException("make sure to override searchForFacetValuesAsync for custom backend");
	}

	/***
	 * If you override this please be sure it returns a unique string per instance
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return super.toString();
	}
}
