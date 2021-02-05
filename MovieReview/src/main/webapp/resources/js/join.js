	$(function(){
		$("#pwd2").keyup(function(){
			var pwd = $("#pwd1").val();
			var pwd2 = $("#pwd2").val();
			
			if(pwd == pwd2){
				$("#pwd_check").text("비밀번호가 일치합니다.");
				$("#pwd_check").css("color", "green");
			}else{
				$("#pwd_check").text("비밀번호가 일치하지않습니다.");
				$("#pwd_check").css("color", "red");
			}
		});
	});

	function id_check(){
		var mem_id = frm.id.value;
		
		if(mem_id == ""){
			alert("아이디를 입력해주세요");
		}else{
			$.ajax({
				url : "./check_id.do",
				dataType : "text",
				data : {"mem_id" : mem_id},
				success : function(data){
					console.log(data);
					if(data > 0){
						alert("중복된 아이디가 있습니다.");
						$("#id").val("");
					}else{
						alert("사용 가능한 아이디입니다.");
					}
				},
				errror: function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});	
		}	
	}
	
	function addr_search() {
		new daum.Postcode({
		    oncomplete: function(data) {
		    	var roadAddr = data.roadAddress;
		        var extraRoadAddr = ''; 
		        
		        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
		            extraRoadAddr += data.bname;
		        }
		        
		        if(data.buildingName !== '' && data.apartment === 'Y'){
		            extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
		         }
		        
		        if(extraRoadAddr !== ''){
		            extraRoadAddr = ' (' + extraRoadAddr + ')';
		        }
		        
		        $('#zip').val(data.zonecode);
		        $("#addr1").val(roadAddr);
		        
		        if(roadAddr !== ''){
		            $("#addr2").val(extraRoadAddr);
		        } else {
		        	$("#addr2").val('');
		        }
	
		    }
		}).open();	
	}
	
	function email_check() {
		var mem_email = $("#email").val();
		
		if(mem_email == ""){
			alert("이메일을 입력해주세요");
		}else{
			$.ajax({
				url : "./email_check.do",
				dataType : "text",
				data : {"mem_email" : mem_email},
				success : function(data){
					console.log(data);
					if(data > 0){
						alert("중복된 이메일이 있습니다.");
						$("#email").val("");
					}else{
						alert("사용 가능한 이메일입니다..");
					}
				},
				errror: function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}
	}
	
	function join_check(){
		if(frm.name.value == ""){
			alert("이름을 입력해주세요");
			frm.name.focus();
			return false;
		}
		if(frm.id.value == ""){
			alert("아이디를 입력해주세요");
			frm.id.focus();
			return false;
		}
		if(frm.pwd1.value == ""){
			alert("비밀번호를 입력해주세요");
			frm.pwd1.focus();
			return false;
		}
		if(frm.pwd2.value == ""){
			alert("비밀번호를 확인해주세요");
			frm.pwd2.focus();
			return false;
		}
		if(frm.nickname.value == ""){
			alert("닉네임을 입력해주세요");
			frm.nickname.focus();
			return false;
		}
		if(frm.addr1.value == ""){
			alert("주소를 입력해주세요");
			frm.addr1.focus();
			return false;
		}
		if(frm.addr2.value == ""){
			alert("주소를 입력해주세요");
			frm.addr2.focus();
			return false;
		}
		if(frm.phone2.value == ""){
			alert("핸드폰번호를 입력해주세요");
			frm.phone2.focus();
			return false;
		}
		if(frm.phone3.value == ""){
			alert("핸드폰번호를 입력해주세요");
			frm.phone3.focus();
			return false;
		}
		if(frm.email.value == ""){
			alert("이메일을 입력해주세요");
			frm.email.focus();
			return false;
		}
	}
	
	
	
	
	
	
	