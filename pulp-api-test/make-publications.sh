#!/bin/bash

set -e

function pack() {
  rm -f $1.epub
  echo "Generating $1.epub..."
  cd $1
  if [ -e mimetype ]; then
    zip -0 ../$1.epub mimetype
  fi
  if [ "$(find . -not -name '.' -not -name 'mimetype')" ]; then
    zip -r ../$1.epub . -x mimetype
  fi
  cd ..
}

pushd src/test/epub

rm -f empty.epub
touch empty.epub

pack container-empty
pack container-missing
pack container-unsupported
pack container-wrong-root
pack mimetype-directory
pack mimetype-missing
pack mimetype-wrong

popd
