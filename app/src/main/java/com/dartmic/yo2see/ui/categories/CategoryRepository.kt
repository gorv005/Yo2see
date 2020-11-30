package com.dartmic.yo2see.ui.categories



import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.FeatureListResponse
import io.reactivex.Single

interface CategoryRepository {
    fun getCategories(service :String) : Single<CategoriesResponse>

    fun getFeatureProducts(service :String) : Single<FeatureListResponse>

}