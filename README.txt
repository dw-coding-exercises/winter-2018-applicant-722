Hello, and thank you for your time!

Below you will find:
  1. How to run the project
  2. An outline of the project
  3. Ways to improve the project

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Run this project:

lein clean; lein cljsbuild once prod; lein ring server

Use "dev" over "prod" for faster build times and source maps.

Use "server-headless" over "server" if you want to navigate
to localhost:3000 all by yourself.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Documentation appears at the top of source files.

Please note that my style of commenting has been
ajusted for this exercise.

Starting with src/clj/my_exercise/core.clj would be logical.

The general outline is that the core routes "/" to home/page
and "/search" to elections/search.

The home/page renders a Reagent Cljs frontend which is just
a form that calls "/search" and lists the results underneath.

The elections/search calls a TurboVote API and returns
the results to the client. Currently only the city and
state are actually considered.

One goal I had was to keep code simple and straightforward
while not looking too hasty or dismissive.

I chose Reagent because it is a straightforward framework
that makes building UIs fun. Clojure functions and lisp
syntax play wellwith a reactive data flow and tree-like
HTML, respectively.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

With more time, there are lots of ways to improve this
project, some of which are noted in the source files.

These ways include:

Testing, both manually (like on mobile) and automated,
potentally with something cool and/or generative
like with clojure.spec.

Style, the CSS should be tested across browsers and
maybe made responsive, and maybe a tad prettier.

Forms, the front end form could be a lot fancier,
with autocomplete, validation and errors, requesting
user location via the browser, picking an address on
a map, remembering addresses a user has filled out,
and so forth.

Results, right now they are just listed, but they
are not orginized or sortable in any fancy way.
They could also be prettier and hold more info.

Develoment cycle, using tools like Figwheel and
having other creature comforts set up could be nice.

File/dependancy size, right now the app does not do a
lot and there might be ways to trim it down, or at least,
if it were to grow bigger, break some things into helper
namespaces.
