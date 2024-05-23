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

            link {
                rel = "stylesheet"
                href = "/style.css"
            }

            link {
                rel = "preconnect"
                href = "https://fonts.bunny.net"
            }

            link {
                rel = "stylesheet"
                href = "https://fonts.bunny.net/css2??family=Josefin+Sans:ital,wght@0,100..700;1,100..700&family=Noto+Sans+JP:wght@100..900&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
            }

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
            div {
                id = "app"
                div {
                    id = "header"
                    h1 {
                        insert(header)
                    }
                }

                div {
                    id = "content"
                    insert(ContentTemplate(), content)
                }
            }
        }
    }
}