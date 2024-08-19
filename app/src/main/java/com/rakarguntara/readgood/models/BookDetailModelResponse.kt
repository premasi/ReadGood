package com.rakarguntara.readgood.models

import com.google.gson.annotations.SerializedName

data class BookDetailModelResponse(

	@field:SerializedName("saleInfo")
	val saleInfo: SaleInfoDetail? = null,

	@field:SerializedName("kind")
	val kind: String? = null,

	@field:SerializedName("volumeInfo")
	val volumeInfo: VolumeInfoDetail? = null,

	@field:SerializedName("etag")
	val etag: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("accessInfo")
	val accessInfo: AccessInfoDetail? = null,

	@field:SerializedName("selfLink")
	val selfLink: String? = null
)

data class AccessInfoDetail(

	@field:SerializedName("accessViewStatus")
	val accessViewStatus: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("viewability")
	val viewability: String? = null,

	@field:SerializedName("pdf")
	val pdf: PdfDetail? = null,

	@field:SerializedName("webReaderLink")
	val webReaderLink: String? = null,

	@field:SerializedName("epub")
	val epub: EpubDetail? = null,

	@field:SerializedName("publicDomain")
	val publicDomain: Boolean? = null,

	@field:SerializedName("quoteSharingAllowed")
	val quoteSharingAllowed: Boolean? = null,

	@field:SerializedName("embeddable")
	val embeddable: Boolean? = null,

	@field:SerializedName("textToSpeechPermission")
	val textToSpeechPermission: String? = null
)

data class SaleInfoDetail(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("isEbook")
	val isEbook: Boolean? = null,

	@field:SerializedName("saleability")
	val saleability: String? = null,

	@field:SerializedName("buyLink")
	val buyLink: String? = null
)

data class EpubDetail(

	@field:SerializedName("isAvailable")
	val isAvailable: Boolean? = null,

	@field:SerializedName("downloadLink")
	val downloadLink: String? = null
)

data class ReadingModesDetail(

	@field:SerializedName("image")
	val image: Boolean? = null,

	@field:SerializedName("text")
	val text: Boolean? = null
)

data class VolumeInfoDetail(

	@field:SerializedName("pageCount")
	val pageCount: Int? = null,

	@field:SerializedName("printType")
	val printType: String? = null,

	@field:SerializedName("readingModes")
	val readingModes: ReadingModesDetail? = null,

	@field:SerializedName("previewLink")
	val previewLink: String? = null,

	@field:SerializedName("canonicalVolumeLink")
	val canonicalVolumeLink: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("imageLinks")
	val imageLinks: ImageLinksDetail? = null,

	@field:SerializedName("subtitle")
	val subtitle: String? = null,

	@field:SerializedName("panelizationSummary")
	val panelizationSummary: PanelizationSummaryDetail? = null,

	@field:SerializedName("publisher")
	val publisher: String? = null,

	@field:SerializedName("publishedDate")
	val publishedDate: String? = null,

	@field:SerializedName("printedPageCount")
	val printedPageCount: Int? = null,

	@field:SerializedName("maturityRating")
	val maturityRating: String? = null,

	@field:SerializedName("allowAnonLogging")
	val allowAnonLogging: Boolean? = null,

	@field:SerializedName("contentVersion")
	val contentVersion: String? = null,

	@field:SerializedName("authors")
	val authors: List<String?>? = null,

	@field:SerializedName("dimensions")
	val dimensions: Dimensions? = null,

	@field:SerializedName("infoLink")
	val infoLink: String? = null
)

data class Dimensions(

	@field:SerializedName("height")
	val height: String? = null
)

data class PanelizationSummaryDetail(

	@field:SerializedName("containsImageBubbles")
	val containsImageBubbles: Boolean? = null,

	@field:SerializedName("containsEpubBubbles")
	val containsEpubBubbles: Boolean? = null
)

data class PdfDetail(

	@field:SerializedName("isAvailable")
	val isAvailable: Boolean? = null,

	@field:SerializedName("downloadLink")
	val downloadLink: String? = null
)

data class ImageLinksDetail(

	@field:SerializedName("small")
	val small: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("large")
	val large: String? = null,

	@field:SerializedName("extraLarge")
	val extraLarge: String? = null,

	@field:SerializedName("smallThumbnail")
	val smallThumbnail: String? = null,

	@field:SerializedName("medium")
	val medium: String? = null
)
