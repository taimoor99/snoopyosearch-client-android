package com.snoopyo.search.saas;

import android.support.annotation.NonNull;

/**
 * A search query targeting a specific index.
 */
public class IndexQuery {
    private final String indexName;
    private final Query query;

    public IndexQuery(@NonNull String indexName, @NonNull Query query)  {
        this.indexName = indexName;
        this.query = query;
    }

    public IndexQuery(@NonNull Index index, @NonNull Query query)  {
        this.indexName = index.getRawIndexName();
        this.query = query;
    }

    public @NonNull String getIndexName() {
        return indexName;
    }

    public @NonNull
    Query getQuery() {
        return query;
    }
}