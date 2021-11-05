<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Панель навигации изменяется в зависимости от ширины экрана .navbar-expand{-sm|-md|-lg|-xl}, если как у нас--%>
<%---lg - это означает что на всех экранах данного размера или больше панель навигации отображается в развёрнутом виде--%>
<%--navbar-light bg-light - цветовая схема--%>
<%--collapse - сворачиваемое меню--%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Estr</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user">User list</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/profile">Profile</a>
            </li>
        </ul>
    </div>
</nav>

