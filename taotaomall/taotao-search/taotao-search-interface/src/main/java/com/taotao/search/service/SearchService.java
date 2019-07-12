package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TAotaoresult;

public interface SearchService {
 public TAotaoresult importAllSearchContent() throws Exception;

SearchResult search(String queryString, Integer page, Integer rows) throws Exception;
}
