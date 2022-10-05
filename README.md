# file-traverse

This is a Clojure library for traverse a file path recursively, and apply a function.

## Installation

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.kay1759/file-traverse.svg)](https://clojars.org/org.clojars.kay1759/file-traverse)

## Usage
### Usage:
```
(require '[file-traverse.core :as trv])

(trv/traverse <base directory> <function> <filter for file: optional> <filter for direcotry: optional>)
```

### Example:
This example is updating all html files under a directory.

This is a typical example, though it has side effects causes it uses 'spit'.

```
(trv/traverse "/src/templates"
    #(spit %
		(str/replace (slurp %)
			"http://mydomain.com"
			"https://mydomain.com")
    #(re-find #"\.html$" (.getName %))))
```

## Tests ##
```
lein test
```
## Licence:

[MIT]

## Author

[Katsuyoshi Yabe](https://github.com/kay1759)
