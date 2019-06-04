#!/bin/bash
sh_folder=$(dirname $(readlink -f $0))
pushd $sh_folder
target/$(basename $0) $*
popd
