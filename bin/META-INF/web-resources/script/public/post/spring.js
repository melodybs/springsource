$(function () {
	
	//English Post Toggle
	var btn_contents_en = $(".btn_contents_en")
		contents_en = $(".contents_en");
	
	btn_contents_en.click(function (e) {
		
		var state = contents_en.css("display");
		
		if (state === "none") {
			
			contents_en.css("display", "block");
		} else {
			
			contents_en.css("display", "none");
		}
		
		e.preventDefault();
	});
	
	//Prev Title
	$.ajax({
		type: "GET",
		cache: false,
		url: "/ajax/prevNextPost/spring",
		async: true,
		data: "postId=" + prevId, //prevId: internal script define
		dataType: "json",
		success: function (data) {
			
			var desc_prev = $(".desc_prev");
			
			desc_prev.text(data.title);
		}
	});
	//Next Title
	$.ajax({
		type: "GET",
		cache: false,
		url: "/ajax/prevNextPost/spring",
		async: true,
		data: "postId=" + nextId, //nextId: internal script define
		dataType: "json",
		success: function (data) {
			
			var desc_next = $(".desc_next");
			
			desc_next.text(data.title);
		}
	});
	//Main Title
	if (mainId !== "0") {
		$.ajax({
			type: "GET",
			cache: false,
			url: "/ajax/prevNextPost/spring",
			async: true,
			data: "postId=" + mainId, //mainId: internal script define
			dataType: "json",
			success: function (data) {
				
				var title_main = $(".title_main");
				
				title_main.text("Part " + data.title);
			}
		});
	}
}());
