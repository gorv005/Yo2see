package com.dartmic.yo2see.ui.categories


import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryDataResponsePayload
import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.FeatureListResponse
import com.dartmic.yo2see.network.AppRestApiFast


import io.reactivex.Single

class CategoryRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CategoryRepository {
    override fun getCategories(service: String,type: String): Single<CategoriesResponse> {
        return restApi.getCategories(service,type)
    }

    override fun getCategoriesData(
        service: String,
        type: String
    ): Single<CategoryDataResponsePayload> {
        return restApi.getCategoriesData(service,type)
    }

    override fun getFeatureProducts(service: String): Single<FeatureListResponse> {
        return restApi.getFeatureList(service)
    }


}