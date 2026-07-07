#!/bin/bash
# Start Next.js dev server detached from any controlling terminal
cd /home/z/my-project
exec bun run dev > /home/z/my-project/dev.out.log 2>&1
