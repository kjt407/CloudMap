var storedFilesModify = [];
var areadyStoredFiles = [];
var deleteStoredFiles = [];
$('body').on('change', '.user_picked_files_modify', function () {
    var files = this.files;
    var i = 0;
    for (i = 0; i < files.length; i++) {
        var readImg = new FileReader();
        var file = files[i];
        if (file.type.match('image.*')) {
            storedFilesModify.push(file);
            readImg.onload = (function (file) {
                return function (e) {
                    $('.cvf_uploaded_files').append(
                        "<li class='all-image' file = '" + file.name + "'>" +
                        "<img class = 'img-thumb' src = '" + e.target.result + "' />" +
                        "<a href = '#' class = 'cvf_delete_image' title = 'Cancel'><img class = 'delete-btn' src='../images/close.png' /></a>" +
                        "</li>"
                    );
                };
            })(file);
            readImg.readAsDataURL(file);
        } else {
            alert('the file ' + file.name + ' is not an image<br/>');
        }
    }
    if (storedFilesModify.length+areadyStoredFiles.length === 0) {
        $(".modify #width-scroll").css("display", "none");
        $(".modify #content").css("height", "580px");
    } else {
        $(".modify #width-scroll").css("display", "inline-block");
        $(".modify #content").css("height", "374px");
    }
});
// 하나씩 제거하기
$('body').on('click', 'a.cvf_delete_image', function (e) {
    $(this).parent().remove('');
    var file = $(this).parent().attr('file');
    for (var i = 0; i < storedFilesModify.length; i++) {
        if (storedFilesModify[i].name == file) {
            storedFilesModify.splice(i, 1);
            break;
        }
    }
    for (var i = 0; i < areadyStoredFiles.length; i++) {
        if (areadyStoredFiles[i].imgName == file) {
            deleteStoredFiles.push(areadyStoredFiles[i].uuid)
            areadyStoredFiles.splice(i, 1);
            break;
        }
    }
    if (storedFilesModify.length+areadyStoredFiles.length === 0) {
        $(".modify #width-scroll").css("display", "none");
        $(".modify #content").css("height", "580px");
    } else {
        $(".modify #width-scroll").css("display", "inline-block");
        $(".modify #content").css("height", "374px");
    }
});
$('body').on('click', '.modify-btn', function (e) {
    var lno = document.getElementById("my-lno").innerHTML;
    var data = new FormData();

    var title = $(".modify input[name='title']").val();
    var content = $(".modify #content").val();

    if(title===null||title===""||title===undefined||title==="undefined"){
        toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1000 };
        toastr.warning('제목을 입력해주세요.');
        return;
    }
    if(content===null||content===""||content===undefined||content==="undefined"){
        toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1000 };
        toastr.warning('내용을 입력해주세요.');
        return;
    }
    data.append("lno", lno);
    for(var i = 0; i <storedFilesModify.length; i++){
        data.append("files",storedFilesModify[i]);
    }

    for(var i = 0; i <deleteStoredFiles.length; i++){
        data.append("uuids",deleteStoredFiles[i]);
    }
    data.append("title", title);
    data.append("content", content);


    $.ajax({
        type: "PUT",
        url: "/editMapLog",
        data: data,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function () {
            $('.modify.modal').modal("hide");
            storedFilesModify = [];
            deleteStoredFiles = [];
            areadyStoredFiles = [];
            getMyMapLogList();
            toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1500 };
            toastr.success('수정이 완료되었습니다.');
        },
        error: function (e) {
            toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1500 };
            toastr.error('오류 : 관리자에게 문의 바랍니다.');
        }
    });
});
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

