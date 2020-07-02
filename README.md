# file-traverse

This is a Clojure library for traverse a file path recursively, and apply a function.

## Installation

[![Clojars Project](https://img.shields.io/clojars/v/kay1759/file-traverse.svg)](https://clojars.org/kay1759/file-traverse)

## Usage
### Usage:
```
(require '[kay1759.file-traverse/core :as trv])

(trv/traverse <base directory> <function> <filter for file: optional> <filter for direcotry: optional>)
```

### Example:
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
