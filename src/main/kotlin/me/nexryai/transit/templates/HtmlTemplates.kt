package me.nexryai.transit.templates

import io.ktor.server.html.*
import kotlinx.html.*

class ContentTemplate: Template<FlowContent> {
    override fun FlowContent.apply() {
        article {
            h2 {
                +"乗換案内へようこそ"
            }
            p {
                +"フォームに入力して検索しましょう。"
            }
            div(classes = "form"){
                div(classes = "form-element") {
                    p(classes = "form-label") {
                        i("ti ti-plane-departure form-icon")
                        +"出発地となるバス停・駅"
                    }
                    input {
                        id = "from"
                        type = InputType.text
                        placeholder = "出発地"
                    }
                }

                div(classes = "form-element") {
                    p(classes = "form-label") {
                        i("ti ti-plane-arrival form-icon")
                        +"到着地となるバス停・駅"
                    }
                    input {
                        id = "to"
                        type = InputType.text
                        placeholder = "到着地"
                    }
                }

                div(classes = "form-element") {
                    p(classes = "form-label") {
                        i("ti ti-clock-hour-10 form-icon")
                        +"目標到着日時"
                    }
                    input {
                        id = "date"
                        type = InputType.date
                        placeholder = "日付"
                    }
                    input {
                        id = "time"
                        type = InputType.time
                        placeholder = "時刻"
                    }
                }

                div {
                    id = "error"
                    p {
                        id = "errorLabel"
                    }
                }

                div(classes = "form-button") {
                    button {
                        id = "search"
                        onClick = "jumpToResult()"
                        i("ti ti-search button-icon")
                        +"検索"
                    }
                }
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

            link {
                rel = "preconnect"
                href = "https://cdn.jsdelivr.net"
            }

            link {
                rel = "stylesheet"
                href = "https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css"
            }

            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1.0"
            }
        }
    }
}

class LayoutTemplate: Template<HTML> {
    private val head = TemplatePlaceholder<HeadTemplate>()
    private val content = TemplatePlaceholder<ContentTemplate>()
    override fun HTML.apply() {
        insert(HeadTemplate(), head)
        body {
            div {
                id = "app"
                div {
                    id = "header"
                    h1 {
                        +"TransitKt"
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