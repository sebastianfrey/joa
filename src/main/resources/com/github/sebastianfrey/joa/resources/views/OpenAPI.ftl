<#-- @ftlvariable name="openAPI" type="com.github.sebastianfrey.joa.resources.views.OpenAPIView" -->
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <meta
    name="description"
    content="${serviceId} - SwaggerUI"
  />
  <title>${serviceId} - SwaggerUI</title>
  <link rel="stylesheet" href="https://unpkg.com/swagger-ui-dist@4.5.0/swagger-ui.css" />
</head>
<body>
<div id="swagger-ui"></div>
<script src="https://unpkg.com/swagger-ui-dist@4.5.0/swagger-ui-bundle.js" crossorigin></script>
<#assign url = context.toAbsoluteUrl("/${serviceId}/api") />
<script>
  window.onload = () => {
    window.ui = SwaggerUIBundle({
      url: "${url}" + "?f=json",
      dom_id: '#swagger-ui',
    });
  };
</script>
</body>
</html>
