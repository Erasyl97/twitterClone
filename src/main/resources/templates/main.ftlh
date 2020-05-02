<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>
        <@l.logout />
    </div>
    <br>
    <strong>Добавить запись:</strong>
    <div>
        <form method="post" action="main">
            <input type="text" name="text" placeholder="Введите сообщение">
            <input type="text" name="tag" placeholder="Тэг">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Добавить</button>
        </form>
    </div>
    <br>
    <strong>Поиск по тэгу</strong>
    <div>
        <form method="get" action="main">
            <input type="text" name="filter" placeholder="Тэг" value="${filter}">
            <button type="submit">Поиск</button>
        </form>
    </div>
    <br>
    <strong>Поиск по автору</strong>
    <div>
        <form method="get" action="main">
            <input type="text" name="author" placeholder="Автор" value="${author}">
            <button type="submit">Поиск</button>
        </form>
    </div>
    <br>
    <div>Список сообщений</div>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
    <#else>
        No message!!!
    </#list>
</@c.page>