//评论
function postComment() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    commentDiffTarget(questionId, 1, content);
}

//提交
function commentDiffTarget(targetId, type, content) {
    if (!content) {
        alert("评论内容不能为空~~");
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
        dataType: "json",
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 3001) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=7ffcf9222a376d295f34&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        //localStorage,类似cookie但有其优点
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        }
    });
}

/**
 * 子评论
 */
function subComment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    commentDiffTarget(commentId, 2, content);
}

/**
 * 展开/折叠二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    //获取状态
    var collapse = e.getAttribute("comment-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in")
        e.removeAttribute("comment-collapse");
        e.classList.remove("active");
    } else {
        var subCommentCollapse = $("#comment-" + id);
        if (subCommentCollapse.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            //设置展开状态
            e.setAttribute("comment-collapse", "in");
            //颜色填充样式
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {

                $.each(data.data.reverse(), function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-circle",
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
                        "class": "comment-menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var subCommentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 sub-comment-list"
                    }).append(mediaElement);

                    subCommentCollapse.prepend(subCommentElement);
                });

                //展开二级评论
                comments.addClass("in");
                //设置展开状态
                e.setAttribute("comment-collapse", "in");
                //颜色填充样式
                e.classList.add("active");
            });
        }
    }
}