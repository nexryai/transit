package me.nexryai.transit.templates

import io.ktor.server.html.*
import kotlinx.html.*

class RouteNotFoundPageTemplate(): Template<HTML> {
    private val head = TemplatePlaceholder<HeadTemplate>()
    override fun HTML.apply() {
        insert(HeadTemplate(), head)
        body {
            div {
                id = "app"
                div {
                    id = "error-page"
                    h2(classes = "error-page-title") {
                        i(classes = "ti ti-mood-puzzled")
                        +" Route not found"
                    }
                    p {
                        +"経路が見つかりませんでした。"
                    }
                    ul {
                        li {
                            +"出発地または到着地が正しいか確認してください。"
                        }
                        li {
                            +"出発地と到着地が離れすぎている可能性があります。"
                        }
                        li {
                            +"一時的なシステムエラーによりこの表示が出る場合もあります。"
                        }
                    }
                }
            }
        }
    }
}

class ServerErrorPageTemplate(private val message: String): Template<HTML> {
    private val head = TemplatePlaceholder<HeadTemplate>()
    override fun HTML.apply() {
        insert(HeadTemplate(), head)
        body {
            div {
                id = "app"
                div {
                    id = "error-page"
                    h2(classes = "error-page-title") {
                        i(classes = "ti ti-zoom-exclamation")
                        +" Something went wrong"
                    }
                    p {
                        +"An unrecoverable error occurred. Unexpected exception was thrown."
                    }
                    p {
                        +"Technical details: "
                        span(classes = "error-page-detail") {
                            +message
                        }
                    }
                }
            }
        }
    }
}