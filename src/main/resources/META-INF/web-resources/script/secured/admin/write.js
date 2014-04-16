$(function() {
	CKEDITOR.replace( 'editor' );
	CKEDITOR.replace( 'editor_e' );
	
	var main_title = $("#main_title"),
		prev_title = $("#prev_title"),
		sub_title = $("#sub_title"),
		editor_ko = $("#editor");
		//내부 제목
		target_input = $(".inside_input"),
		target_text = $("#inside_text"),
		btn_input_add = $(".btn_input_add"),
		btn_input_reset = $(".btn_input_reset"),
		btn_text_reset = $(".btn_text_reset"),
		btn_text_one =  $(".btn_text_one"),
		target_input02 = $(".inside_input02"),
		btn_input_add02 = $(".btn_input_add02"),
		btn_input_reset02 = $(".btn_input_reset02"),

/* 메인 글 */
	$.ajax({
		type: "GET",
		cache: false,
		url: "/ajax/mainPost/spring",
		async: true,
		dataType: "json",
		success: function (data) {
			
			for (var i in data) {
				
				main_title.append('<option value="' + data[i].id + '">' 
						+ data[i].title + '</option>');
			}
		}
	});

/* 이전 글 */
	main_title.change(function (e) {
		
		var id_main = main_title.val();

		if (id_main !== "") {
				
			$.ajax({
				type: "GET",
				cache: false,
				url: "/ajax/possiblePrevPost/spring",
				async: true,
				data: "id_main=" + id_main,
				dataType: "json",
				success: function (data) {
					
					prev_title.empty();
					
					for (var i in data) {
						
						var whitespace = data[i].id_main == 0 ? "(M) " 
								: "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(S) ";
						
						prev_title.append('<option value="' + data[i].id + '">' 
								+ whitespace + data[i].title + '</option>');
					}
				}
			});
		} else {
			
			prev_title.empty();
			prev_title.append(
					'<option value="">(필수) 이전 글을 선택 해주세요.</option>');
			
			alert("필수 항목입니다. 선택후 저장 해주세요.");
		}
	});
		
/* 선택 순서
	sub_title.attr("disabled", "disabled");
	
	main_title.change(function (e) {
		
		if (main_title.val() === "0") {
			
			sub_title.attr("disabled", "disabled");
			
			return false;
		}
		
		sub_title.removeAttr("disabled");
	});
//선택 순서 */
	
/* 내부 제목 */
	//내부 제목 추가 버튼: 해당 내용이 텍스트 에리어에 추가 된다.
	btn_input_add.click(function (e) {
		
		var data = target_input.val(),
			old_data = target_text.val(),
			new_data = old_data === "" ? data : old_data + ";;" + data;
		
		var form = $("#form_spring"),
			form_data = form.serialize();
		
		console.log(form_data);
		
		target_text.val(new_data);
		
		e.preventDefault();
	});
	
	//내부 제목 리셋 버튼: 내용을 비워 준다.
	btn_input_reset.click(function (e) {
		
		target_input.val("");

		e.preventDefault();
	});
	
	//내부 제목 텍스트에리어 리셋 버튼: 내용을 비운다.
	btn_text_reset.click(function (e) {
		
		target_text.val("");

		e.preventDefault();
	});
	
	//내부 제목 텍스트에리어 1개 지우는 버튼 
	btn_text_one.click(function (e) {
		
		var data = target_text.val(),
			data_arr = data.split(";;");
			
		data_arr.pop();
		
		target_text.val(data_arr.join(";;"));
		
		e.preventDefault();
	});
	
	//내부 제목 02 추가 버튼: 해당 내용이 텍스트 에리어에 추가 된다.
	btn_input_add02.click(function (e) {
		
		var data = target_input02.val(),
			old_data = target_text.val(),
			new_data = old_data === "" ? data : old_data + ";;$$" + data;
		
		target_text.val(new_data);
		
		e.preventDefault();
	});
	
	//내부 제목 02 리셋 버튼: 내용을 비워 준다.
	btn_input_reset02.click(function (e) {
		
		target_input02.val("");

		e.preventDefault();
	});
/* 내부 제목 */
}());