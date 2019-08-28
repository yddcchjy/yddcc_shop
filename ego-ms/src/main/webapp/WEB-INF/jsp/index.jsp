<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Cache-Control" content="max-age=300" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>秒杀专场 - 易购</title>
<meta name="Keywords" content="java,易购java" />
<meta name="description" content="在易购中找到了29910件java的类似商品，其中包含了“图书”，“电子书”，“教育音像”，“骑行运动”等类型的java的商品。" />
<link rel="stylesheet" type="text/css" href="/css/base.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/psearch20131008.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/psearch.onebox.css" media="all" />
<link rel="stylesheet" type="text/css" href="/css/pop_compare.css" media="all" />
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<style>
	.divcss5{ text-decoration:line-through} 
</style>
</head>
<body>
<script type="text/javascript" src="/js/base-2011.js" charset="utf-8"></script>
<!-- header start -->
<jsp:include page="commons/header.jsp" />
<!-- header end -->

<!-- kill1 -->
<%-- <div class="w main">
	<div class="crumb">全部结果&nbsp;&gt;&nbsp;<strong>"${query}"</strong></div>
<div class="clr"></div> --%>

<!-- 商品展示详情 -->
<div class="m psearch " id="plist">
<ul class="list-h clearfix" tpl="2">
<c:forEach items="${itemList}" var="item">
<li class="item-book" bookid="11078102">
	<!-- image -->
	<div class="p-img">
		<a target="_blank" href="http://localhost:8087/item/${item.id }.html">
			<img width="160" height="160" data-img="1" data-lazyload="${item.images[0]}" />
		</a>
	</div>
	
	<!-- title -->
	<div class="p-name">
		<a target="_blank" href="http://localhost:8087/item/${item.id }.html">
			${item.title}<span style="font-size:9px;color:#FFAA71;">${item.sellPoint }</span>
		</a>
	</div>
	
	<!-- price -->
	<div class="p-price">
		<i>秒杀价：</i>
		<strong>￥<fmt:formatNumber groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" value="${item.msPrice / 100 }"/></strong>
		<b class='ever-price'><span class="divcss5">￥${item.oldPrice}</span></b>
	</div>
	
	<div class='num'>
		<div>剩余<b class='owned'>${item.leftnum}</b>件</div>
	</div>
	
	<a class='sui-btn btn-block btn-buy' href="http://localhost:8087/item/${item.id }.html" >立即抢购</a>
	
	<div class="service">由 易购 发货</div>
	
	<div class="extra">
		<span class="star"><span class="star-white"><span class="star-yellow h5">&nbsp;</span></span></span>
	</div>
	
</li>
</c:forEach>
</ul></div>
</div>

<!-- 页数 -->
   
   	<div align="center">
	   	<span class="prev-disabled"> 
	   		<a href="search2.html?page=${page - 1}" class="next">
	   			 <b>上一页</b>
	   		</a>
	   	</span>
   		<a href="javascript:void(0)" class="current">1</a>
		<a href="/search2.html?page=2">2</a>
		<a href="/search2.html?page=3">3</a>
		<a href="/search2.html?page=4">4</a>
		<a href="/search2.html?page=5">5</a>
		<a href="/search2.html?page=6">6</a>
		<span class="text">…</span>
		<a href="/search2.html?page=${page + 1}" class="next">下一页<b></b></a>
		<a href="/search2.html?page=${totalPages}">${totalPages}</a>
		<%-- <span class="page-skip"><em>&nbsp;&nbsp;共${totalPages}页&nbsp;&nbsp;&nbsp;&nbsp;到第</em></span> --%>
   	</div>
	



<!-- footer start -->
<jsp:include page="commons/footer.jsp" />
<!-- footer end -->
<script type="text/javascript" src="/js/jquery.hashchange.js"></script>
<script type="text/javascript" src="/js/search_main.js"></script>
<script type="text/javascript">
//${paginator.totalPages}
//SEARCH.query = "${query}";
SEARCH.bottom_page_html(${page},${totalPages},'');
</script>
</body>
</html>