<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Presentations</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <!-- CSS here -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/owl.carousel.min.css" rel="stylesheet">
    <link href="css/magnific-popup.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/themify-icons.css" rel="stylesheet">
    <link href="css/nice-select.css" rel="stylesheet">
    <link href="css/flaticon.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/slicknav.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <!-- <link rel="stylesheet" href="css/responsive.css"> -->
</head>

<body>
<!-- Header -->
<%@include file='header.jsp' %>


<div class="most_recent_blog">
    <div class="container">
        <div class="row">
            <c:forEach items="${presentations}" var="presentation"></c:forEach>
            <div class="col-xl-8 col-md-8">
                <div class="section_title mb-33">
                    <h3>Most Recent</h3>
                </div>
                <div class="row">
                    <div class="single_blog">
                        <div class="blog_thumb">
                            <a href="#">
                                <img src="img/most_recent/2.jpg" alt="">
                            </a>
                        </div>
                        <div class="blog_meta">
                            <p><a href="#">${presentation.name}</a></p>
                            <h3><a href="#">${presentation.name}</a></h3>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-4 col-md-4">
                <div class="section_title mb-33">
                    <h3>Tags</h3>
                </div>
                <div class="tags">
                    <ul>
                        <c:forEach items="${tags}" var="tag">
                            <li><a href="/presentations/${tag}">tag</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<%@include file='footer.jsp' %>

<!-- JS here -->
<script src="js/vendor/modernizr-3.5.0.min.js"></script>
<script src="js/vendor/jquery-1.12.4.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/isotope.pkgd.min.js"></script>
<script src="js/ajax-form.js"></script>
<script src="js/waypoints.min.js"></script>
<script src="js/jquery.counterup.min.js"></script>
<script src="js/imagesloaded.pkgd.min.js"></script>
<script src="js/scrollIt.js"></script>
<script src="js/jquery.scrollUp.min.js"></script>
<script src="js/wow.min.js"></script>
<script src="js/nice-select.min.js"></script>
<script src="js/jquery.slicknav.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/plugins.js"></script>

<script src="js/main.js"></script>

</body>

</html>