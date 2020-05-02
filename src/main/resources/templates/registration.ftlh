<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    Add new User <br/>
    ${message}
    <@l.login "/registration" />
</@c.page>
