<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>SLiPP Java Web Programming</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<link href="../css/styles.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="./top.jsp" />
	
	<div class="container" id="main">
		<div class="col-md-10 col-md-offset-1">
			<div class="panel panel-default">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>사용자 아이디</th>
							<th>이름</th>
							<th>이메일</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">0</th>
							<td>${sessionScope.user.userId}</td>
							<td>${sessionScope.user.name}</td>
							<td>${sessionScope.user.email}</td>
							<td><a href="/user/update" class="btn btn-success" role="button">수정</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- script references -->
	<script src="../js/jquery-2.2.0.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/scripts.js"></script>
</body>
</html>
