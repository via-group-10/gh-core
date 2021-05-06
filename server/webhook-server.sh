#!/bin/sh
cd "$(dirname "$0")"
webhook -hooks hooks.json -hotreload -port 8555 --verbose
cd -
