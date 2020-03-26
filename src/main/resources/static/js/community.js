//提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment-content").val();
    comment2Target(questionId, 1, content);

    commentFlash("question-comment-1", 1, content);
}

//评论拼接
function commentFlash(id, type, content) {
    if (!content) {
        return;
    }
    let src = $("#user-src").prop("src");
    let name = $("#user-name").text();

    var mediaLeftElement = $("<div/>", {
        "class": "media-left"
    }).append($("<img/>", {
        "class": "media-object img-rounded",
        "src": src
    }));

    var mediaBodyElement = $("<div/>", {
        "class": "media-body"
    }).append($("<h5/>", {
        "class": "media-heading",
        "html": name
    })).append($("<div/>", {
        "html": content
    })).append($("<div/>", {
        "class": "menu"
    }).append($("<span/>", {
        "class": "pull-right",
        "html": moment(Date()).format('YYYY-MM-DD')
    })));

    var mediaElement = $("<div/>", {
        "class": 'media'
    }).append(mediaLeftElement)
        .append(mediaBodyElement);

    var commentElement = $("<div/>", {
        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
    }).append(mediaElement);

    if (type === 1) {
        $("#" + id).append(commentElement);
        $("#comment-content").val("");
    } else if (type === 2) {
        $("#comment-input-" + id).before(commentElement);
        $("#input-" + id).val("");
    }
}

function comment2Target(targetId, type, content) {
    if (!content) {
        alert("回复不能为空");
        return;
    }

    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code === 200) {
                // window.location.reload();
            } else {
                if (response.code === 2003) {
                    const isAccepted = confirm("response.message");
                    if (isAccepted) {
                        window.open("/signIn");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}

//二级评论
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2Target(commentId, 2, content);
    commentFlash(commentId, 2, content);
}

//展开二级评论
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    var attribute = e.getAttribute("data-collapse");
    if (attribute) {
        //折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);

        comments.addClass("in");
        e.setAttribute("data-collapse", "in");
        e.classList.add("active");


        //服务器获取
        if (subCommentContainer.children().length === 1) {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data, function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": 'media'
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                })
            });
        }
    }
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}


function showSelectTag() {
    $("#select-tag").show();
}

//登录
$(document).ready(function () {
    $("#login").click(function () {
        let userId = $("#login-user-name").val();
        let userPassword = $("#login-user-password").val();
        if (userPassword === "" || userPassword == null || userId === "" || userId == null) {
            alert("内容不能为空");
            return;
        }
        $.ajax({
            type: "post",
            url: "/signIn",
            contentType: "application/json",
            data: JSON.stringify({
                "userId": userId,
                "userPassword": userPassword
            }),
            success: function (response) {
                if (response.code === 200) {
                    window.location.reload();
                } else {
                    alert(response.message);
                }
                console.log(response);
            },
            dataType: "json"
        })
    })
});