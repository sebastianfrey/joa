<#-- @ftlvariable name="" type="com.github.sebastianfrey.joa.resources.views.ServicesView" -->
<html>
    <body>
        <h1>Services</h1>
        <ul>
          <#list services.getServices() as service>
            <li>${service.getTitle()}</li>
            <ul>
              <#list service.getLinks() as link>
                <li>${link.getUri()}</li>
              </#list>
            </ul>
          </#list>
        </ul>

        <h1>Links</h1>
        <ul>
          <#list services.getLinks() as link>
            <li>${link.getUri()}</li>
          </#list>
        </ul>
    </body>
</html>