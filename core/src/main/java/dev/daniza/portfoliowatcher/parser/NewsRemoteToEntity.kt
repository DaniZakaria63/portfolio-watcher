package dev.daniza.portfoliowatcher.parser

import dev.daniza.portfoliowatcher.local.entity.NewsEntity
import dev.daniza.portfoliowatcher.model.news.NewsHeadline

fun NewsHeadline.toEntity(): NewsEntity {
    return NewsEntity(
        sourceId = this.source?.id,
        sourceName = this.source?.name,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        author = this.author,
        content = this.content
    )
}

fun NewsEntity.toModel(): NewsHeadline {
    return NewsHeadline(
        source = NewsHeadline.Source(
            id = this.sourceId,
            name = this.sourceName
        ),
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        author = this.author,
        content = this.content
    )
}