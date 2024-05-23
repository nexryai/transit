package me.nexryai.transit.templates

import io.ktor.server.html.*
import kotlinx.html.*

class ContentTemplate: Template<FlowContent> {
    val articleTitle = Placeholder<FlowContent>()
    val articleText = Placeholder<FlowContent>()
    override fun FlowContent.apply() {
        article {
            h2 {
                insert(articleTitle)
            }
            p {
                insert(articleText)
            }
        }
    }
}

class HeadTemplate: Template<HTML> {
    override fun HTML.apply() {
        head {
            title {
                +"Ktor"
            }
            meta {
                charset = "utf-8"
            }
            script(src = "/static/main.js") {}
        }
    }
}

class LayoutTemplate: Template<HTML> {
    private val head = TemplatePlaceholder<HeadTemplate>()
    val header = Placeholder<FlowContent>()
    val content = TemplatePlaceholder<ContentTemplate>()
    override fun HTML.apply() {
        insert(HeadTemplate(), head)
        body {
            h1 {
                insert(header)
            }
            insert(ContentTemplate(), content)
        }
    }
}