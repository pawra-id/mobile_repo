package id.pawra.data.remote.response

import com.google.gson.annotations.SerializedName

data class VetResponse(

	@field:SerializedName("items")
	val items: List<VetResponseItem>? = null,

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

data class VetResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("education")
	val education: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("clinic_name")
	val clinicName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("experience")
	val experience: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("end_work_hour")
	val endWorkHour: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("start_work_hour")
	val startWorkHour: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("id")
	val id: Int,

	val error: String? = null
)
