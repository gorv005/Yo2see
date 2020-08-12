package com.appiness.yo2see.ui.categories


import com.appiness.yo2see.managers.PreferenceManager
import com.appiness.yo2see.model.categories.CategoriesResponse
import com.appiness.yo2see.model.categories.FeatureListResponse
import com.appiness.yo2see.network.AppRestApiFast


import io.reactivex.Single

class CategoryRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CategoryRepository {
    override fun getCategories(service: String): Single<CategoriesResponse> {
        return restApi.getCategories(service)
    }

    override fun getFeatureProducts(service: String): Single<FeatureListResponse> {
        return restApi.getFeatureList(service)
    }


}