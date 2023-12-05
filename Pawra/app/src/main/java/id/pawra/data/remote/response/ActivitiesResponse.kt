package id.pawra.data.remote.response

import com.google.gson.annotations.SerializedName

data class Dog(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("neutered")
	val neutered: Boolean? = null,

	@field:SerializedName("breed")
	val breed: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class TagsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class ActivitiesResponseItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("dog")
	val dog: Dog? = null,

	@field:SerializedName("tags")
	val tags: List<TagsItem?>? = null
)


