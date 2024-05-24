package me.nexryai.transit.templates

import kotlinx.css.*
import kotlinx.css.Float

var styles = CssBuilder().apply {
    body {
        backgroundColor = Color("#f4f4f4")
    }

    button {
        border = Border.none
        backgroundColor = Color.black
        color = Color.white
        padding = Padding(10.px)
        borderRadius = 999.px
        minWidth = 70.px
        hover {
            backgroundColor = Color("#333")
            cursor = Cursor.pointer
        }
    }

    input {
        padding = Padding(10.px)
        borderRadius = 5.px
        border = Border(1.px, BorderStyle.solid, Color("#ccc"))
        width = 87.pct
       marginBottom = 10.px
    }

    h1 {
        fontFamily = "'Ubuntu', sans-serif"
        fontWeight = FontWeight.w400
        fontStyle = FontStyle.normal
    }

    rule("#app") {
        maxWidth = 600.px
        margin = Margin(LinearDimension.auto)
        fontFamily = "'Poppins', 'Noto Sans JP', sans-serif"
        fontWeight = FontWeight.w300
        fontStyle = FontStyle.normal
    }

    rule(".form") {
        display = Display.flex
        flexDirection = FlexDirection.column
        maxWidth = 500.px
        margin = Margin(50.px, LinearDimension.auto, 0.px, LinearDimension.auto)
    }

    rule(".form-element") {
        margin = Margin(0.px, 0.px, 32.px, 0.px)
    }

    rule(".form-button") {
        textAlign = TextAlign.right
    }

    rule(".form-icon") {
        marginRight = 10.px
    }

    rule(".button-icon") {
        marginRight = 4.px
    }

    rule("#errorLabel") {
        color = Color("#bd0000")
    }

    rule(".transfer-station") {
        border = Border(1.px, BorderStyle.solid, Color("#ccc"))
        borderRadius = 5.px
        paddingLeft = 10.px
        height = 50.px
        overflow = Overflow.hidden
    }

    rule(".transfer-train") {
        borderLeft = Border(3.px, BorderStyle.solid, Color("#ccc"))
        marginLeft = 32.px
        paddingLeft = 10.px
    }

    rule(".transfer-station-name") {
        float = Float.left
        marginTop = 4.px
    }

    rule(".transfer-platform-info") {
        textAlign = TextAlign.right
        marginRight = 10.px
    }

    rule(".transfer-depart-time") {
        marginRight = 10.px
        color = Color("#666")
        fontFamily = "'Ubuntu', sans-serif"
    }

    rule(".transfer-station-name-subtext") {
        color = Color("#666")
        marginRight = 10.px
        fontWeight = FontWeight.w300
    }

    rule(".train-info") {
        color = Color("#2d2d2d")
        fontWeight = FontWeight.w700
    }
}