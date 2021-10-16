$(".modify #width-scroll").on('mousewheel', function (e) {
    var wheelDelta = e.originalEvent.wheelDelta;
    if (wheelDelta > 0) {
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    } else {
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    }
});

$("body").on("click", ".btn-cancle", function(){
    $('.modify.modal').modal("hide");
    $('.detail.modal').modal("hide");
})