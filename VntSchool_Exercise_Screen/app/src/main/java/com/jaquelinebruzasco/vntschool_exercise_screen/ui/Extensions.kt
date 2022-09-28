package com.jaquelinebruzasco.vntschool_exercise_screen.ui

fun String.toBrl(): String {

    return if (this.contains(".")) {
        if (this.substringAfter(".").length < 2) {
            "R$${this}0"
        } else if (this.substringAfter(".").length == 2) {
            "R$$this"
        } else {
            "R$${this.substringBefore(".")}.${this.substringAfter(".")[0]}${this.substringAfter(".")[1]}"
        }
    } else{
        "R$${this}.00"
    }
}