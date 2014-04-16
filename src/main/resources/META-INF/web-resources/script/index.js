$(function () {
	//Next Title
	$.ajax({
		type: "GET",
		cache: false,
		url: "/ajax/prevNextPost/spring",
		async: true,
		data: "postId=1", //nextId: internal script define
		dataType: "json",
		success: function (data) {
			
			var desc_next = $(".desc_next");
			
			desc_next.text(data.title);
		}
	});
}());