/**
 * Created by daocers on 2016/7/19.
 */


$(function () {
    /**
     * 全选
     */
    $(".selectAll").on("click", function () {
        var checked = $(this).prop('checked');
        console.log(checked);
        $("table").find("input[type='checkbox']").prop("checked", checked);
    });
})

/**
 * 如果查看详情，禁用掉所有的 按钮和输入框
 */
$(function () {
    /**
     * 初始化时间日期插件，默认是不显示时间，如果有需要可以进行个性化设置，data-date-format进行设置
     */
    $(".date").flatpickr({
        dateFormat: 'yyyy-mm-dd',
        onChange: change
    });

    $(".time").flatpickr({
        // dataFormat: 'yyyy-mm-dd HH:ii:SS',
        dataFormat: 'yyyy-mm-dd HH:ii',
        enableTime: true,
        enableSeconds: true,
        minuteIncrement: 1,
        time_24hr: true,
        onChange: change,
        onClose: change
    });

    function change() {
        $(".date, .time").trigger("change");
    }

    // console.log("detail， 开始禁用")

});


$(function () {

    /**
     * 页面加载完成查看是否有异常信息，进行提示
     * @type {*}
     */
    var err = $("#err").text().trim();
    if (err && err != '') {
        swal("", err, "error");
        return false;
    }

    var msg = $("#msg").text().trim();
    if (msg && msg != '') {
        // zeroModal.alert(msg);
        swal("", msg, "info");
    }




})


/**
 * 扩展jqeury，添加设置时间和设置日期的功能
 * 该扩展依赖于flatpickr日期选择插件
 */
jQuery.fn.extend({
    setDate: function (date) {
        $(this).flatpickr({
            defaultDate: date
        })
    },
    setTime: function (time) {
        $(this).flatpickr({
            // dataFormat: 'yyyy-mm-dd HH:ii:SS',
            dataFormat: 'yyyy-mm-dd HH:ii',
            enableTime: true,
            enableSeconds: true,
            defaultDate: time
        })
    }
})



/**
 * 当前有问题，需要排查，页面中引用以下modal方法无法弹出，提示方法undefined
 * X x x x x x x  x x x x x x x x x x x x x x  x x x xx x  xx x x  x xx x x
 *
 * 以下是为了使用弹出层
 *
 * @param title
 * @param content
 * @param confirmTest
 * @param cancelText
 * @param type
 * @param confirmFunc
 * @param cancelFunc
 */
function swalBase(title, content, confirmTest, cancelText, type, confirmFunc, cancelFunc) {
    if(!title){
        title = "";
    }
    if(!confirmText){
        confirmText = "确定";
    }
    if(!type){
        type = "info";
    }
    if(!confirmFunc){
        confirmFunc = function () {
            return false;
        }
    }
    if(!cancelFunc){
        cancelFunc = function () {
            return false;
        }
    }
    if(type == 'confirm'){
        swal({
            title: title,
            text: content,
            confirmButtonText: confirmText,
            cancelButtonText: cancelText,
            type: type
        }).then(function (isConfirm) {
            if(isConfirm){
                confirmFunc();
            }else{
                cancelFunc();
            }
        });
    }else{
        swal({
            title: title,
            text: content,
            confirmButtonText: confirmText,
            cancelButtonText: cancelText,
            type: type
        });
    }

}

function tesAlert(title, content, confirmText) {
    swalBase(title, content, confirmText, "info");
}

function tesWarning(title, content, confirmText) {
    swalBase(title, content, confirmText, "warning");
}

function tesError(title, content, confirmText) {
    swalBase(title, content, confirmText, "error");
}

function tesConfirm(title, content, confirmText, cancelText, type, confirmFunc, cancelFunc) {
    if(!type){
        type = "question";
    }
    swalBase(title, content, confirmText, cancelText, type, confirmFunc, cancelFunc);

}