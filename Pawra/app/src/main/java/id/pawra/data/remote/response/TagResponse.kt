package id.pawra.data.remote.response

import com.google.gson.annotations.SerializedName

data class TagResponse(

	@field:SerializedName("TagResponse")
	val tagResponse: List<TagsItem>? = null,

	val error: String? = null
)

data class TagsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int,
)
