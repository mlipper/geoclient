#!/usr/bin/env bash

echo "set -o vi" >> $HOME/.bashrc \
    && echo "alias ll=\"ls -l\"" >> $HOME/.bashrc \
    && echo "alias lla=\"ls -lsa\"" >> $HOME/.bashrc \
    && echo "alias c=clear" >> $HOME/.bashrc

/opt/geosupport/current/bin/geosupport install

