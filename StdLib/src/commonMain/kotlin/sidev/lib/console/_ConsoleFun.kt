package sidev.lib.console

import sidev.lib.`val`.StringLiteral

object IoConfig{
    const val PRINT= true
    const val PRINT_DEBUG= PRINT && true
    const val PRINT_RESULT= PRINT && true
    const val PRINT_WARNING= PRINT && true
    const val PRINT_ERROR= PRINT && true
}

fun prind(any: Any?, endWithNewLine: Boolean = true){
    if(IoConfig.PRINT_DEBUG)
        prin(any, StringLiteral.ANSI_CYAN, endWithNewLine)
}
fun prinr(any: Any?, endWithNewLine: Boolean = true){
    if(IoConfig.PRINT_RESULT)
        prin(any, StringLiteral.ANSI_GREEN, endWithNewLine)
}
fun prinw(any: Any?, endWithNewLine: Boolean = true){
    if(IoConfig.PRINT_WARNING)
        prin(any, StringLiteral.ANSI_YELLOW, endWithNewLine)
}
fun prine(any: Any?, endWithNewLine: Boolean = true){
    if(IoConfig.PRINT_ERROR)
        prin(any, StringLiteral.ANSI_RED, endWithNewLine)
}
fun prinp(any: Any?, endWithNewLine: Boolean = true){
    if(IoConfig.PRINT_ERROR)
        prin(any, StringLiteral.ANSI_BLUE, endWithNewLine)
}
//@JvmOverloads
fun prin(any: Any?, color: String= StringLiteral.ANSI_RESET, endWithNewLine: Boolean = true){
    if(IoConfig.PRINT){
        if(endWithNewLine)
            println("$color $any ${StringLiteral.ANSI_RESET}")
        else
            print("$color $any ${StringLiteral.ANSI_RESET}")
    }
}
fun prin_(any: Any?) = prin(any)

expect fun log(any: Any?)
expect fun str(any: Any?): String
