package com.erkindilekci.beerbook.data.mappers

import com.erkindilekci.beerbook.data.data_source.local.BeerEntity
import com.erkindilekci.beerbook.data.data_source.remote.dto.BeerDto
import com.erkindilekci.beerbook.domain.model.Beer

fun BeerDto.toBeerEntity(): BeerEntity {
    return BeerEntity(id, name, tagline, description, first_brewed, image_url)
}

fun BeerEntity.toBeer(): Beer {
    return Beer(id, name, tagline, description, firstBrewed, imageUrl)
}
