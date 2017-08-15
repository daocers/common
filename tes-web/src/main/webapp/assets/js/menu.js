

$(function () {
    console.log("navi....")
    var Accordion = function (e, multiple) {
        this.e = e || {};
        this.multiple = multiple || false;
        var links = this.e.find(".link");
        links.on("click", {e: this.e, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function (e) {
        console.log("点击menu事件")
//            $(".accordion li.open").removeClass("open");
        var $e = e.data.e;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $e.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
        ;
    }
    var accordion = new Accordion($('#accordion'), false);

    $(".menu-left .submenu li a").on("click", function () {
        $(".menu-left a").removeClass("active");
        $(this).addClass("active");
        var url = $(this).attr("href");
        if (!url || url == "#" || url == '') {
            return false;
        }
        window.location.href = url;
        return false;
    })

})