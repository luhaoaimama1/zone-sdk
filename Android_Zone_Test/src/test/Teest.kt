package test

class Entity {
    var obj: Entity2? = null
}

class Entity2 {
    fun add(){
    }
}

class Teest {
    fun nullUse(var1: String?) {
        // val s = var1 ?: return
        val s = var1 ?: return let {
            println(1)
        }
        println("2:$s")
    }

    fun nullUse2(var1: Entity) {
        val obj = var1.obj
        if (obj == null) {
            return
        }
        obj.add()
    }

}

fun main(args: Array<String>) {
    Teest().nullUse(null)

    println("<=====华丽的分割线======>")

    Teest().nullUse("second")
}