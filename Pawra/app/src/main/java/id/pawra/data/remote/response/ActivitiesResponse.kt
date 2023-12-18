package id.pawra.data.remote.response

import com.google.gson.annotations.SerializedName

data class ActivitiesResponse(

	@field:SerializedName("items")
	val items: List<ActivitiesResponseItem>? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	val error: String? = null
)

data class ActivitiesResponseItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("dog")
	val dog: PetResponseItem? = null,

	@field:SerializedName("tags")
	val tags: List<TagsItem>? = null,

	val error: String? = null
)