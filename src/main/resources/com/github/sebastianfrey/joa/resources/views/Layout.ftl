<#macro layout>
  <!doctype html>

  <html lang="en">
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">

      <title>JOA</title>

      <meta name="description" content="JOA - OGC API made simple">
      <meta name="author" content="Sebastian Frey">

      <meta property="og:title" content="JOA - OGC API made simple">
      <meta property="og:type" content="website">
      <meta property="og:url" content="https://github.com/sebastianfrey/joa">
      <meta property="og:description" content="A simple HTML5 Template for new projects.">
      <meta property="og:image" content="image.png">

      <link rel="icon" href="/favicon.ico">
      <link rel="icon" href="/favicon.svg" type="image/svg+xml">
      <link rel="apple-touch-icon" href="/apple-touch-icon.png">

      <script src="https://cdn.tailwindcss.com"></script>
    </head>

    <body class="min-h-screen bg-[#e1e2e1]">
      <#nested />
    </body>
  </html>
</#macro>
