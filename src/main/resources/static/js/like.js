
jQuery(document).ready(function () {

    console.log("여기 실행?")

    $("#like").change(function(){
        if($("#like").is(":checked")){
            console.log("체크완료")
            var lno = document.getElementById("lno").innerHTML;
            var data = new FormData();
            data.append("lno", lno);
            $.ajax({
                type: "POST",
                url: "/addLikes",
                data: data,
                processData: false,
                contentType: false,
                dataType: 'json',
                success: function (data) {
                    $("input:checkbox[id='like']").prop("checked", true);
                    $.ajax({
                        type: "GET",
                        url: "/getLikes/?lno="+lno,
                        processData: false,
                        contentType: false,
                        dataType: 'json',
                        success: function (data) {
                            $("label[for='like']").html("&nbsp; x"+data.likesCount);
                        },
                        error: function (e) {
                            $('#btnUpload').prop('disabled', false);
                            alert('fail');
                        }
                    });

                },
                error: function (e) {
                    $('#btnUpload').prop('disabled', false);
                    alert('fail');
                }
            });

        }else{
                console.log("체크완료")
                var lno = document.getElementById("lno").innerHTML;
                var data = new FormData();
                data.append("lno", lno);
                $.ajax({
                    type: "DELETE",
                    url: "/deleteLikes",
                    data: data,
                    processData: false,
                    contentType: false,
                    dataType: 'json',
                    success: function (data) {
                        $("input:checkbox[id='like']").prop("checked", false);
                        $.ajax({
                            type: "GET",
                            url: "/getLikes/?lno="+lno,
                            processData: false,
                            contentType: false,
                            dataType: 'json',
                            success: function (data) {
                                $("label[for='like']").html("&nbsp; x"+data.likesCount);
                            },
                            error: function (e) {
                                $('#btnUpload').prop('disabled', false);
                                alert('fail');
                            }
                        });
                    },
                    error: function (e) {
                        $('#btnUpload').prop('disabled', false);
                        alert('fail');
                    }
                });
            }

    });

});