jQuery(document).ready(function () {
    var storedFiles = [];
    $('body').on('change', '.user_picked_files', function () {
        var files = this.files;
        var i = 0;
        for (i = 0; i < files.length; i++) {
            var readImg = new FileReader();
            var file = files[i];
            if (file.type.match('image.*')) {
                storedFiles.push(file);
                readImg.onload = (function (file) {
                    return function (e) {
                        $('.cvf_uploaded_files').append(
                            "<li class='all-image' file = '" + file.name + "'>" +
                            "<img class = 'img-thumb' src = '" + e.target.result + "' />" +
                            "<a href = '#' class = 'cvf_delete_image' title = 'Cancel'><img class = 'delete-btn' src='images/close.png' /></a>" +
                            "</li>"
                        );
                    };
                })(file);
                readImg.readAsDataURL(file);
            } else {

            }
        }
        if (storedFiles.length === 0) {
            $(".write #width-scroll").css("display", "none");
            $(".write #content").css("height", "580px");
        } else {
            $(".write #width-scroll").css("display", "inline-block");
            $(".write #content").css("height", "374px");
        }
    });
    // 이미지 하나씩 제거하기
    $('body').on('click', 'a.cvf_delete_image', function (e) {
        $(this).parent().remove('');
        var file = $(this).parent().attr('file');
        console.log(file)
        for (var i = 0; i < storedFiles.length; i++) {
            if (storedFiles[i].name == file) {
                storedFiles.splice(i, 1);
                break;
            }
        }
        if (storedFiles.length === 0) {
            $(".write #width-scroll").css("display", "none");
            $(".write #content").css("height", "580px");
        } else {
            $(".write #width-scroll").css("display", "inline-block");
            $(".write #content").css("height", "374px");
        }
    });
    //저장하기
    $('body').on('click', '.btn-save', function (e) {
        var form = $('#write')[0];
        // Create an FormData object
        var data = new FormData();
        var title = $("input[name='title']").val();
        var content = $("#content").val();
        var writer = $("#username-check").val();
        var lat = rightClickLat;
        var lng = rightClickLng;
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
         for(var i = 0; i <storedFiles.length; i++){
         	console.log(storedFiles[i]);
         	data.append("files",storedFiles[i]);
         }
        data.append("title", title);
        data.append("content", content);
        data.append("writer", writer);
        data.append("lat", lat);
        data.append("lng", lng);

        $.ajax({
        	type: "POST",
        	url: contextpath+"register",
        	data: data,
        	processData: false,
        	contentType: false,
        	dataType: 'json',
        	success: function (data) {
        		$('#btnUpload').prop('disabled', false);
                $('.write.modal').modal("hide");
                $(".write .cvf_uploaded_files .all-image").remove('');
                $(".write #width-scroll").css("display", "none");
                $(".write #content").css("height", "580px");
                $(".write-section").find('form')[0].reset()
                start = true;
                getMyMapLogList();
                toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1000 };
                toastr.success('작성이 완료되었습니다.');
        	},
        	error: function (e) {
        		$('#btnUpload').prop('disabled', false);
                toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1000 };
        		toastr.error('오류 : 관리자에게 문의 바랍니다.')
            }
        });
    });
});
//이미지 좌우 스크롤
$(".write #width-scroll").on('mousewheel', function (e) {
    var wheelDelta = e.originalEvent.wheelDelta;
    if (wheelDelta > 0) {
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    } else {
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    }
});
//닫기
$("body").on('click',".btn-close",function(){
    $(".write .cvf_uploaded_files .all-image").remove('');
    $(".write #width-scroll").css("display", "none");
    $(".write #content").css("height", "580px");
    $(".write-section").find('form')[0].reset()
    $('.write.modal').modal("hide");
});

