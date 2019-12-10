package com.fengying.ad.search;

import com.fengying.ad.search.vo.SearchRequest;
import com.fengying.ad.search.vo.SearchResponse;

public interface ISearch {
    SearchResponse fetchAds(SearchRequest request);
}
