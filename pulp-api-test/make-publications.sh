#!/bin/bash

function epub() {
  cd $1
  zip -0 ../$1.epub mimetype
  zip -r ../$1.epub . -x mimetype
  cd ..
}

pushd src/test/epub

rm *.epub

epub container-empty
epub container-missing
epub container-unsupported
epub container-wrong-root
epub mimetype-wrong

(cd mimetype-directory; zip -r ../mimetype-directory.epub .)
(cd mimetype-missing; zip -r ../mimetype-missing.epub . -i .)

popd
