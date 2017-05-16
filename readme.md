# Boot Embongo

This is Clojure's boot plugin that can start and stop embedded MongoDB.
It can be useful for tests and development.

## Usage

Add the dependency:

```clojure
[io.github.marad/boot-embongo "0.0.2-SNAPSHOT"]
```

And import the `start-embongo` and `stop-embongo` tasks:

```clojure
(require '[boot-embongo.core :refer :all])
```

Then you can simply use the tasks:

```clojure
(deftask run-tests []
    (comp (start-embongo) (watch) (midje)))
```

## License

Copyright © 2017 Marcin Radoszewski

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
