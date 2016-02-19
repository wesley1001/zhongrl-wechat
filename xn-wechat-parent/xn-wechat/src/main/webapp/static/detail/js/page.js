$(document).ready(function(){
	var show_per_page = parseInt($("#show_per_page").val()); 
	var number_of_items = parseInt($('#totalPage').val());
	var number_of_pages = Math.ceil(number_of_items/show_per_page);
	var navigation_html = '<ul><li><a href="javascript:previous(this);">‹</a></li>';
	var current_link = 0;
	var current_page = parseInt($('#current_page').val());
	while(number_of_pages > current_link){
		if(current_page == current_link){
			navigation_html += '<li><a class="active" href="javascript:go_to_page(' + current_link +',this)" longdesc="' + current_link +'">'+ (current_link + 1) +'</a></li>';
		}else{
			navigation_html += '<li><a href="javascript:go_to_page(' + current_link +',this)" longdesc="' + current_link +'">'+ (current_link + 1) +'</a></li>';
		}
		current_link++;
	}
	navigation_html += '<li><a href="javascript:next(this);">›</a></li>&nbsp;<li><a href="javascript:btn_hf();" id="hf" class="btn-flat gray" style="font-size:8px;">分页换肤</a></li></ul><input type="hidden" id="click_num" style="" value="0"/>';
	$('#pagination').html(navigation_html);
//	$('#pagination li a:first').addClass('active');
//	$('#pagination li a:first').addClass('active');
	$('#content').children().css('display', 'none');
	$('#content').children().slice(0, show_per_page).css('display', 'block');
	$("#hf").click();
});

function btn_hf(){
	
	var click_num = parseInt($("#click_num").val());
	( click_num % 2 == 0 )?$('#pagination').addClass('inverse') : $('#pagination').removeClass('inverse');
	$("#click_num").val(click_num+1);
}

function previous(obj){
	
	new_page = parseInt($('#current_page').val()) - 1;
	if($('.pagination li').prev('.active').length==true){
		go_to_page(new_page);
	}
	var totalPage = parseInt($('#totalPage').val());
	var show_page = parseInt($("#show_per_page").val()); 
	var number = Math.ceil(totalPage/show_page);
	var pageNumber = 0;
	if(new_page < 0){
		pageNumber = 0;
	}else if(number < new_page){
		pageNumber = 0;
	}else{
		pageNumber = new_page;
	}
	$('#current_page').val(pageNumber);
	$("#formPage").submit();
}
function next(obj){
	
	new_page = parseInt($('#current_page').val()) + 1;
	//if there is an item after the current active link run the function
	if($('.pagination li').next('.active').length==true){
		go_to_page(new_page);
	}
	var totalPage = parseInt($('#totalPage').val());
	var show_page = parseInt($("#show_per_page").val()); 
	var number = Math.ceil(totalPage/show_page);
	var pageNumber = 0;
	if(new_page < 0){
		pageNumber = 0;
	}else if(number <= new_page){
		pageNumber = 0;
	}else{
		pageNumber = new_page;
	}
	$('#current_page').val(pageNumber);
	$("#formPage").submit();
}
function go_to_page(page_num){
	var show_per_page = parseInt($('#show_per_page').val());
	start_from = page_num * show_per_page;
	end_on = start_from + show_per_page;
	$('#pagination').children().css('display', 'none').slice(start_from, end_on).css('display', 'block');
	$('#pagination li a[longdesc=' + page_num +']').addClass('active').siblings('.active').removeClass('active');
	$('#current_page').val(page_num);
	$("#formPage").submit();
}