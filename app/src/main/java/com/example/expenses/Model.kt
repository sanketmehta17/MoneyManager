package com.example.expenses

data class Model (var Name: String, var Amount: String, var Date: String, var Repeat: String, var Type: String) {
    override fun toString(): String {
        return "Recurring Expenses Name: $Name \n" +
                "Repeat from: $Date " +
                "for $Repeat times $Type\n" +
                "Amount: $Amount"
    }
}

object Output {
    val out = ArrayList<Model>()

    init {
        printOut();
    }

    private fun printOut() {
        println(out.size)
    }
}