package id.pawra.data.remote.response

import com.google.gson.annotations.SerializedName

data class AnalysisResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("items")
	val items: List<AnalysisResponseItem>? = null,

	val error: String? = null
)

data class AnalysisResponseItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("activities")
	val activities: List<ActivitiesResponseItem>? = null,

	@field:SerializedName("is_shared")
	val isShared: Boolean? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("dog_id")
	val dogId: Int? = null,

	@field:SerializedName("actions")
	val actions: List<ActionsItem>? = null,

	val error: String? = null
)

data class ShareAnalysisResponse(
	@field:SerializedName("message")
	val message: String,

	val error: Boolean
)

data class ActionsItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("action")
	val action: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)