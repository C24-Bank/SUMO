package c24.sumox

class Scan(
    var builder : Builder
) {

    class Builder {
        private var borderView = BorderView()


        fun setBorderView(borderView: BorderView) = apply {this.borderView = borderView}

        fun getBorderView() = borderView
    }

}
