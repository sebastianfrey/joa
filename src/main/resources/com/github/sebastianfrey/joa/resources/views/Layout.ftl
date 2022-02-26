<#macro header>
  <div class="sticky top-0 z-10 bg-slate-50 border-b-2 border-[#caae53] px-6 py-3 min-h-4 w-full lg:px-0">
    <header class="m-auto w-full px-6 lg:w-3/4 flex">
      Header TODO<#nested />
    </header>
  </div>
</#macro>

<#macro footer>
  <div class="fixed bottom-0 z-10 bg-slate-50 px-6 py-3 w-full shadow-inner lg:px-0 ">
    <footer class="m-auto w-full px-6 lg:w-3/4 flex">
      Footer TODO<#nested />
    </footer>
  </div>
</#macro>

<#macro layout>
  <!doctype html>

  <html lang="en">
    <head>
      <meta charset="utf-8">
      <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

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
      <script>
        tailwind.config = {
          plugins: [
            tailwind.plugin(function ({ addVariant }) {
              // Add a `third` variant, ie. `third:pb-0`
              addVariant('third', '&:nth-child(3)')
            }),
          ],
        };
      </script>
    </head>

    <body class="min-h-screen bg-[#f5f5f6] overflow-y-scroll">
      <@header />
      <#nested />
      <@footer />
    </body>
  </html>
</#macro>
