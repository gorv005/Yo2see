package com.dartmic.yo2see.ui.categories



import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryDataResponsePayload
import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.FeatureListResponse
import io.reactivex.Single

interface CategoryRepository {
    fun getCategories(service :String, type: String) : Single<CategoryDataResponsePayload>
    fun getCategoriesData(service :String, type: String) : Single<CategoryDataResponsePayload>

    fun getFeatureProducts(service :String) : Single<FeatureListResponse>

}