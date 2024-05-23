package me.nexryai.transit.utils


class Logger {
    private class ConsoleColors (){
        val reset = "\u001B[0m"
        val bold = "\u001B[1m"

        val red = "\u001B[31m"
        val green = "\u001B[32m"
        val yellow = "\u001B[33m"
        val blue = "\u001B[34m"
        val gray = "\u001B[90m"

        val redBold = "\u001B[1m\u001B[31m"
        val greenBold = "\u001B[1m\u001B[32m"
        val yellowBold = "\u001B[1m\u001B[33m"
        val blueBold = "\u001B[1m\u001B[「34m"

        //アンダーライン付き（callerPackageName表示用）
        val whiteWithUnderline = "\u001B[4m\u001B[37m"
    }

    private val color = ConsoleColors()
    private val callerPackageName = Throwable().stackTrace[1].className.substringBeforeLast('.')

    fun debug(msg: String) {
        println("${color.gray}[DEBUG] ${color.whiteWithUnderline}${callerPackageName}${color.reset} $msg")
    }

    fun info(msg: String) {
        println("${color.greenBold}[INFO] ${color.whiteWithUnderline}${callerPackageName}${color.reset} $msg")
    }

    fun warn(msg: String) {
        println("${color.yellowBold}[WARNING] ${color.whiteWithUnderline}${callerPackageName}${color.reset} $msg")
    }

    fun error(msg: String) {
        System.err.println("${color.redBold}[ERROR] ${color.whiteWithUnderline}${callerPackageName}${color.red} ${color.red}${msg}${color.reset}")
    }

}
