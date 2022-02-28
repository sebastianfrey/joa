<#macro header>
  <header class="z-10 bg-slate-50 border-b-2 border-[#caae53] px-6 min-h-4 w-full lg:px-0">
    <div class="m-auto w-full lg:w-3/4 flex">
      <a href="/" alt="Home">
        <img class="cursor-pointer h-16 hover:bg-slate-100 p-3" src="/assets/img/logo.svg" />
      </a>
      <#nested />
    </div>
  </header>
</#macro>

<#macro footer>
  <footer class="absolute h-28 bottom-0 z-10 bg-slate-50 px-6 py-3 w-full shadow-inner lg:px-0 ">
    <div class="m-auto h-full w-full px-6 lg:w-3/4 flex justify-center">
      <a href="https://github.com/sebastianfrey/joa" class="flex flex-row mr-auto items-center space-x-2">
        <p class="text-sm hover:underline">Powerd by</p><img class="cursor-pointer h-[30px] hover:bg-slate-100" src="/assets/img/logo.svg" />
      </a>
      <a href="https://github.com/sebastianfrey/joa" class="flex flex-row items-center space-x-2">
        <img class="h-[30px]" src="/assets/img/github.svg" alt="GitHub" /><p class="text-sm hover:underline">GitHub</p>
      </a>
      <#nested />
    </div>
  </footer>
</#macro>

<#macro layout title = "JOA">
  <!doctype html>

  <html lang="en">
    <head>
      <meta charset="utf-8">
      <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

      <meta name="viewport" content="width=device-width, initial-scale=1">

      <title>${title}</title>

      <meta name="description" content="JOA - OGC API made simple">
      <meta name="author" content="Sebastian Frey">

      <meta property="og:title" content="JOA - OGC API made simple">
      <meta property="og:type" content="website">
      <meta property="og:url" content="https://github.com/sebastianfrey/joa">
      <meta property="og:description" content="A simple HTML5 Template for new projects.">
      <meta property="og:image" content="image.png">

      <link rel="apple-touch-icon" sizes="180x180" href="/assets/apple-touch-icon.png">
      <link rel="icon" type="image/png" sizes="32x32" href="/assets/favicon-32x32.png">
      <link rel="icon" type="image/png" sizes="16x16" href="/assets/favicon-16x16.png">
      <link rel="manifest" href="/assets/site.webmanifest">
      <link rel="mask-icon" href="/assets/safari-pinned-tab.svg" color="#5bbad5">
      <meta name="msapplication-TileColor" content="#da532c">
      <meta name="theme-color" content="#ffffff">

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

    <body class="min-h-screen bg-[#f5f5f6] relative overflow-y-scroll">
      <@header />
      <#nested />
      <@footer />
    </body>
  </html>
</#macro>
