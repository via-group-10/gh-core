#!/bin/bash
docker-compose logs -f app sql
sh watch-logs.sh
