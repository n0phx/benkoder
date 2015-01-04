# benkoder [![Build Status](https://travis-ci.org/integricho/benkoder.svg)](https://travis-ci.org/integricho/benkoder)
[![Clojars Project](http://clojars.org/benkoder/latest-version.svg)](http://clojars.org/benkoder)

A Clojure library designed to perform Bencode encoding and decoding.

## Installation

Add the following dependency to your `project.clj` file:

    [benkoder "0.1.0"]

## Usage

    (use '[benkoder.encode])
    (bencode {:a 3})

or

    (use '[benkoder.decode])
    (bdecode "d1:ai3ee")

## License

Copyright © 2015 Andrean Franc

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
