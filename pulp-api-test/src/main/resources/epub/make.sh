#!/bin/bash

set -e

SCRIPT_PATH=$(realpath $0)
EPUB_DIR=$(dirname $SCRIPT_PATH)

function pack() {
  rm -f ${EPUB_DIR}/$1.epub
  echo "Generating $1.epub..."
  cd $1
  if [ -e mimetype ]; then
    zip -0 ${EPUB_DIR}/$1.epub mimetype
  fi
  if [ "$(find . -not -name '.' -not -name 'mimetype')" ]; then
    zip -r ${EPUB_DIR}/$1.epub . -x mimetype
  fi
  cd ..
}

rm -f ${EPUB_DIR}/empty.epub
touch ${EPUB_DIR}/empty.epub

pack container-empty
pack container-missing
pack container-unrecognized
pack container-unsupported
pack mimetype-directory
pack mimetype-missing
pack mimetype-wrong
pack package-empty
pack package-missing
pack package-unrecognized
pack package-unsupported
pack valid-multiple-renditions
pack valid-single-rendition
