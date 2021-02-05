<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="${path }/resources/css/board.css">
<script type="text/javascript" src="${path }/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	$(function(){
		comm_list($("#bnum").val(),1);
		$("#menu_bar").click(function(){
			if($("#cont_menu").is(":visible")){
				$("#cont_menu").hide();	
			}else{
				$("#cont_menu").show();
			}
		})
		
	});
	
	// 댓글 가져오기
	function comm_list(bnum, page){
		
		$.ajax({
			url : "${pageContext.request.contextPath }/comm_list.do",
			dataType : "json",
			data : {
				"bnum" : bnum,
				"page" : page
			},
			success : function(data){
				if(data){
					comm(data);
				}else{
					alert("댓글 가져오기 실패");
				}
			},
			error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	// 댓글 리스트
	function comm(data){
		var paging = data.paging;
		var tb = "";
		
		$("#comm").empty();
		for(i in data.comm){
			
			tb += "<table id = 'tb_"+ data.comm[i].comm_num+"'>";
			tb += "<tr>";
			for(var j = 0; j <  data.comm[i].comm_indent; j++){
				tb += "<td rowspan = '4' class = 'nbsp'></td>";
			}
			tb += "<td rowspan = '2' class = 'img_td'><img src='${pageContext.request.contextPath }/resources/upload/"+data.comm[i].mem_img+"'></td>";
			tb += "<td class = 'info_td'>"+ data.comm[i].comm_writer+"<span>"+ data.comm[i].comm_regdate.substring(0,16)+"</span></td>";
			tb += "<td class = 'comm_option'>";
			if('${session_mem_id}' != ''){
				if( data.comm[i].comm_indent == 0){
					tb += "<a href='javascript:frm(\""+ data.comm[i].comm_num+"\", \"reply\")'>답글</a>";
				}
				if( data.comm[i].comm_writer == '${session_mem_id}'){
					tb += "<a href='javascript:frm(\""+ data.comm[i].comm_num+"\", \"update\")'>수정</a>";
					tb += "<a href='javascript:comm_del(\""+ data.comm[i].comm_num+"\")'>삭제</a>";
				}
			}
			tb += "</tr><tr><td colspan = '4'>"+ data.comm[i].comm_cont+"</td></tr><tr style = 'display : none'><td></td><td>";
			tb += "<textarea>"+ data.comm[i].comm_cont+"</textarea>";
			tb += "</td><td>"
			tb += "<button class = 'btn_red' onclick = 'comm_update(\""+ data.comm[i].comm_num+"\")'>수정</button><br>";
			tb += "<button class = 'btn_white' onclick = 'frm_cancle()'>취소</button>";
			tb += "</td></tr><tr style = 'display : none'><td></td><td><textarea></textarea></td><td>";
			tb += "<button class = 'btn_red' onclick = 'comm_reply(\""+ data.comm[i].comm_num+"\")'>등록</button><br>";
			tb += "<button class = 'btn_white' onclick = 'frm_cancle()'>취소</button>";
			tb += "</td></tr>";
			tb += "</table>";
		}
		
		var page = "";
		var bnum = $("#bnum").val()
		
		page += "<div id = 'paging_box'>";
		
		if(paging.prev){
			page += "<a href = 'javascript:comm_list(\""+bnum+"\",\""+(paging.startPage-1)+"\");'> < </a>";
		}
		for(var i = paging.startPage; i <= paging.endPage; i++){
			if(paging.page == i){
				page += "<b><a href = 'javascript:comm_list(\""+bnum+"\",\""+i+"\");'>["+i+"]</a></b>";	
			}else{
				page += "<a href = 'javascript:comm_list(\""+bnum+"\",\""+i+"\");'>["+i+"]</a>";
			}			
		}
		if(paging.next){
			page += "<a href = 'javascript:comm_list(\""+bnum+"\",\""+(paging.endPage-1)+"\");'> > </a>";
		}
		
		page += "</div>"
		
		$("#comm").append(tb);
		$("#comm").append(page);
	}
	
	// 폼열기
	function frm(comm_num, option){
		$("#comm table tr:nth-child(3)").hide();
		$("#comm table tr:nth-child(4)").hide();
		if(option == "update"){
			$("#tb_"+comm_num+" tr:nth-child(3)").show();
		}else if(option == "reply"){
			$("#tb_"+comm_num+" tr:nth-child(4)").show();
		}
	}
	
	// 폼닫기
	function frm_cancle(comm_num) {
		$("#comm table tr:nth-child(3)").hide();
		$("#comm table tr:nth-child(4)").hide();
	}
	
	// 댓글 쓰기
	function comm_insert(){
		var board_num = $("#comm_board").val();
		var board_cont = $("#comm_cont").val();
		
		if('${session_mem_id}' == ''){
			alert("로그인 후 이용 가능합니다.");
			return false;
		}else{
			if($("#comm_cont").val() == ""){
				alert("댓글을 입력해주세요");
				$("#comm_cont").focus;
				return false;
			}else{
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath }/comm_insert.do",
					dataType : "text",
					data : {
						"comm_board": board_num,
						"comm_cont": board_cont
					},
					success : function(data){
						if(data > 0){
							comm_list($("#bnum").val(),1);
							$("#comm_cont").val("")
						}else{
							alert("댓글 달기 실패");
						}
					},
					error : function(request, status, error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				});
			}	
		}
	}
	
	// 삭제
	function comm_del(comm_num){
		
		if(confirm('댓글을 삭제하시겠습니까?')){
			$.ajax({
				url : "${pageContext.request.contextPath }/comm_delete.do",
				dataType : "text",
				data : {
					"comm_num" : comm_num
				},
				success : function(data){
					if(data > 0){
						comm_list($("#bnum").val(),1);
					}else{
						alert("댓글 삭제 실패");
					}
				},
				error : function(request, status, error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}else{
			return; 
		}
	}
	
	// 수정
	function comm_update(comm_num) {
		var board_num = $("#bnum").val();
		
		var comm_cont = $("#tb_"+comm_num+" tr:nth-child(3) textarea").val();
		
		if(comm_cont == ""){
			alert("댓글을 입력해주세요");
			$("#tb_"+comm_num+" tr:nth-child(3) textarea").focus;
		}else{
			$.ajax({
				url : "${pageContext.request.contextPath }/comm_update.do",
				dataType : "text",
				data : {
					"comm_num" : comm_num,
					"comm_cont" : comm_cont
				},
				success : function(data){
					if(data > 0){
						comm_list($("#bnum").val(),1);
					}else{
						alert("댓글 수정 실패");
					}
				},
				error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}
	}
	
	// 답글
	function comm_reply(comm_num){
		var board_num = $("#bnum").val();
		var comm_cont = $("#tb_"+comm_num+" tr:nth-child(4) textarea").val();

		if(comm_cont == ""){
			alert("댓글을 입력해주세요");
			$("#tb_"+comm_num+" tr:nth-child(4) textarea").focus;
		}else{
			$.ajax({
				url : "${pageContext.request.contextPath }/comm_reply.do",
				dataType : "text",
				data : {
					"comm_num" : comm_num,
					"comm_cont" : comm_cont
				},
				success : function(data){
					if(data > 0){
						comm_list($("#bnum").val(),1);
					}else{
						alert("답글 달기 실패");
					}
				},
				error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}
	}
	
	// 전체 목록
	function all_list(){
		var page = '${page}';
		var label = '${label}';
		var keyword = '${keyword}';
		
		url = "${pageContext.request.contextPath }/";
		
		if(label != '' && keyword != ''){
			url += "board_search.do?page="+page+"&label="+label+"&keyword="+keyword;
		}else{
			url += "board_list.do?page="+page;
		}
		
		location = url;
	}
	
	// 수정
	function post_update(bnum){
		var page = '${page}';
		var label = '${label}';
		var keyword = '${keyword}';
		
		url = "${pageContext.request.contextPath }/";
		
		if(label != '' && keyword != ''){
			url += "board_update_form.do?bnum="+bnum+"&page="+page+"&label="+label+"&keyword="+keyword;
		}else{
			url += "board_update_form.do?bnum="+bnum+"&page="+page;
		}
		
		location = url;
	}
</script>
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id = "board_post">
			<div id = "b_cont">
				<c:set var = "board" value = "${board_dto }" />
				<input type = "hidden" id = "bnum" value = "${board.getBoard_num() }">
				<p id = "tit"><span id = "cate">[${board.getBoard_category() }] </span>${board.getBoard_title() }</p>
				<p id = "mem_info"><img id = "info_img" src="${path }/resources/upload/${board.getMem_img()}"> ${board.getBoard_writer() }<span class="mem">${board.getBoard_regdate() }</span><span class="mem">조회수 : ${board.getBoard_hit() }</span></p>
				<textarea readonly>${board.getBoard_cont() }</textarea>
			</div>
			<div id = "comment_box">
				<p>댓글</p>
				<div id = "comm"></div>
				
				<input type = "hidden" value = "${board.getBoard_num() }" id = "comm_board">
				<div id = "comm_form">
					<table>
						<tr>
							<td><textarea name="comm_cont" id = "comm_cont" placeholder="댓글을 입력해주세요."></textarea></td>
							<td><input type = "button" class = "btn_white" onclick = "comm_insert()" value = "등록"></td>
						</tr>
					</table>
				</div>
			
				<div id = "b_btn_box">
					<c:if test="${session_mem_id == board.getBoard_writer() }">
					<button class="btn_white" onclick = "post_update('${board.getBoard_num() }')">수정</button>
					<button class="btn_white" onclick = "if(confirm('게시물을 삭제하겠습니까?')){
						location = '${pageContext.request.contextPath }/board_delete.do?bnum=${board.getBoard_num() }'  
					}else{return;}">삭제</button>
					</c:if>
					<button class="btn_white" onclick = "location = '${pageContext.request.contextPath }/board_reply_form.do?bnum=${board.getBoard_num() }'">답글</button>
					<button class="btn_red" onclick = "all_list()">목록</button>
				</div>
			</div>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>