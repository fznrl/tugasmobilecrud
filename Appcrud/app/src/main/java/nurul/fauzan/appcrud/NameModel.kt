package nurul.fauzan.appcrud

import java.util.*

data class NameModel(
    val id: Int = getAutoId(),
    var name: String = "",
    var email: String = ""

){
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}

